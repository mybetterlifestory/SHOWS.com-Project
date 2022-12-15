package com.handson.basic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.basic.model.*;
import com.handson.basic.repo.MemberService;
import com.handson.basic.repo.ShowService;
import com.handson.basic.util.AWSService;
import com.handson.basic.util.HandsonException;
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

import static com.handson.basic.util.Dates.atUtc;
import static com.handson.basic.util.FPS.FPSBuilder.aFPS;
import static com.handson.basic.util.FPSCondition.FPSConditionBuilder.aFPSCondition;
import static com.handson.basic.util.FPSField.FPSFieldBuilder.aFPSField;
import static com.handson.basic.util.Strings.likeLowerOrNull;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    @Autowired
    ShowService showService;
    @Autowired
    EntityManager em;
    @Autowired
    ObjectMapper om;
    @Autowired
    AWSService awsService;

    @RequestMapping(value = "/{id}/image", method = RequestMethod.PUT)
    public ResponseEntity<?> uploadShowImage(@PathVariable Long id,  @RequestParam("image") MultipartFile image)
    {
        Optional<Show> dbShow = showService.findById(id);
        if (dbShow.isEmpty()) throw new HandsonException("Show with id: " + id + " not found");
        String bucketPath = "apps/victoria/show-" +  id + ".png" ;
        awsService.putInBucket(image, bucketPath);
        dbShow.get().setImage(bucketPath);
        Show updatedShow = showService.save(dbShow.get());
        return new ResponseEntity<>(ShowOut.of(updatedShow, awsService) , HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PaginationAndList> search(@RequestParam(required = false) String fullName,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromBirthDate,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toBirthDate,
                                                    @RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "50") @Min(1) Integer count,
                                                    @RequestParam(defaultValue = "id") ShowSortField sort, @RequestParam(defaultValue = "asc") SortDirection sortDirection) throws JsonProcessingException {

        var res =aFPS().select(List.of(
                        aFPSField().field("s.id").alias("id").build(),
                        aFPSField().field("s.created_at").alias("createdat").build(),
                        aFPSField().field("s.name").alias("name").build(),
                        aFPSField().field("s.premiered").alias("premiered").build(),
                        aFPSField().field("s.ended").alias("ended").build(),
                        aFPSField().field("s.image").alias("image").build()
                ))
                .from(List.of(" show s"))
                .conditions(List.of(
                        aFPSCondition().condition("( lower(fullname) like :fullName )").parameterName("fullName").value(likeLowerOrNull(fullName)).build(),
                        aFPSCondition().condition("( s.birth_Date >= :fromBirthDate )").parameterName("fromBirthDate").value(atUtc(fromBirthDate)).build(),
                        aFPSCondition().condition("( s.birth_Date <= :toBirthDate )").parameterName("toBirthDate").value(atUtc(toBirthDate)).build()
                )).sortField(sort.fieldName).sortDirection(sortDirection).page(page).count(count)
                .itemClass(MemberOut.class)
                .build().exec(em, om);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOneShow(@PathVariable Long id)
    {
        return new ResponseEntity<>(showService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> insertShow(@RequestBody ShowIn showIn)
    {
        Show show = ShowIn.toShow();
        show = showService.save(show);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateShow(@PathVariable Long id, @RequestBody ShowIn show)
    {
        Optional<Show> dbShow = showService.findById(id);
        if (dbShow.isEmpty()) throw new HandsonException("Show with id: " + id + " not found");
        show.updateShow(dbShow.get());
        Show updatedShow = showService.save(dbShow.get());
        return new ResponseEntity<>(updatedShow, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteShow(@PathVariable Long id)
    {
        Optional<Show> dbShow = showService.findById(id);
        if (dbShow.isEmpty()) throw new HandsonException("Show with id: " + id + " not found");
        showService.delete(dbShow.get());
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }

}
