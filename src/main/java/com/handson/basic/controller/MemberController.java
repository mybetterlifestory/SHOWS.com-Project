package com.handson.basic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.basic.model.*;
import com.handson.basic.repo.MemberService;
import com.handson.basic.repo.StudentService;
import com.handson.basic.util.AWSService;
import com.handson.basic.util.HandsonException;
import com.handson.basic.util.SmsService;
import org.apache.commons.collections4.IteratorUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.handson.basic.util.Dates.atUtc;
import static com.handson.basic.util.FPS.FPSBuilder.aFPS;
import static com.handson.basic.util.FPSCondition.FPSConditionBuilder.aFPSCondition;
import static com.handson.basic.util.FPSField.FPSFieldBuilder.aFPSField;
import static com.handson.basic.util.Strings.likeLowerOrNull;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @Autowired
    ObjectMapper om;

    @Autowired
    AWSService awsService;

//    @Autowired
//    SmsService smsService;

//    @RequestMapping(value = "/sms/all", method = RequestMethod.POST)
//    public ResponseEntity<?> smsAll(@RequestParam String text)
//    {
//        List<String> phones =
//                IteratorUtils.toList(studentService.all().iterator())
//                        .parallelStream()
//                        .map(student -> student.getPhone())
//                        .filter(phone -> !isEmpty(phone))
//                        .collect(Collectors.toList());
//        return new ResponseEntity<>(smsService.send(new MessageAndPhones(text, phones)), HttpStatus.OK);
//    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.PUT)
    public ResponseEntity<?> uploadMemberImage(@PathVariable Long id,  @RequestParam("image") MultipartFile image)
    {
        Optional<Member> dbMember = memberService.findById(id);
        if (dbMember.isEmpty()) throw new HandsonException("Member with id: " + id + " not found");
        String bucketPath = "apps/victoria/member-" +  id + ".png" ;
        awsService.putInBucket(image, bucketPath);
        dbMember.get().setProfilePicture(bucketPath);
        Member updatedMember = memberService.save(dbMember.get());
        return new ResponseEntity<>(MemberOut.of(updatedMember, awsService) , HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PaginationAndList> search(@RequestParam(required = false) String fullName,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromBirthDate,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toBirthDate,
//                                                    @RequestParam(required = false) Integer fromSatScore,
//                                                    @RequestParam(required = false) Integer toSatScore,
//                                                    @RequestParam(required = false) Integer fromAvgScore,
                                                    @RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "50") @Min(1) Integer count,
                                                    @RequestParam(defaultValue = "id") MembertSortField sort, @RequestParam(defaultValue = "asc") SortDirection sortDirection) throws JsonProcessingException {

        var res =aFPS().select(List.of(
                        aFPSField().field("s.id").alias("id").build(),
                        aFPSField().field("s.created_at").alias("createdat").build(),
                        aFPSField().field("s.fullname").alias("fullname").build(),
                        aFPSField().field("s.birth_date").alias("birthdate").build(),
//                        aFPSField().field("s.sat_score").alias("satscore").build(),
//                        aFPSField().field("s.graduation_score").alias("graduationscore").build(),
//                        aFPSField().field("s.phone").alias("phone").build(),
                        aFPSField().field("s.profile_picture").alias("profilepicture").build()
//                        aFPSField().field("(select avg(sg.course_score) from  student_grade sg where sg.student_id = s.id ) ").alias("avgscore").build()
                ))
                .from(List.of(" member s"))
                .conditions(List.of(
                        aFPSCondition().condition("( lower(fullname) like :fullName )").parameterName("fullName").value(likeLowerOrNull(fullName)).build(),
                        aFPSCondition().condition("( s.birth_Date >= :fromBirthDate )").parameterName("fromBirthDate").value(atUtc(fromBirthDate)).build(),
                        aFPSCondition().condition("( s.birth_Date <= :toBirthDate )").parameterName("toBirthDate").value(atUtc(toBirthDate)).build()
//                        aFPSCondition().condition("( sat_score >= :fromSatScore )").parameterName("fromSatScore").value(fromSatScore).build(),
//                        aFPSCondition().condition("( sat_score <= :toSatScore )").parameterName("toSatScore").value(toSatScore).build(),
//                        aFPSCondition().condition("( (select avg(sg.course_score) from  student_grade sg where sg.student_id = s.id ) >= :fromAvgScore )").parameterName("fromAvgScore").value(fromAvgScore).build()
                )).sortField(sort.fieldName).sortDirection(sortDirection).page(page).count(count)
                .itemClass(MemberOut.class)
                .build().exec(em, om);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOneMember(@PathVariable Long id)
    {
        return new ResponseEntity<>(memberService.findById(id), HttpStatus.OK);
    }

//    @RequestMapping(value = "/highSat", method = RequestMethod.GET)
//    public ResponseEntity<?> getHighSatStudents(@RequestParam Integer sat)
//    {
//        return new ResponseEntity<>(memberService.getStudentWithSatHigherThan(sat), HttpStatus.OK);
//    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> insertMember(@RequestBody MemberIn memberIn)
    {
        Member member = memberIn.toMember();
        member = memberService.save(member);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody MemberIn member)
    {
        Optional<Member> dbMember = memberService.findById(id);
        if (dbMember.isEmpty()) throw new HandsonException("Member with id: " + id + " not found");
        member.updateMember(dbMember.get());
        Member updatedMember = memberService.save(dbMember.get());
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMember(@PathVariable Long id)
    {
        Optional<Member> dbMember = memberService.findById(id);
        if (dbMember.isEmpty()) throw new HandsonException("Member with id: " + id + " not found");
        memberService.delete(dbMember.get());
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }

}
