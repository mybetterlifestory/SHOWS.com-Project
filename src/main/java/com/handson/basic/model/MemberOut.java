package com.handson.basic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.handson.basic.util.AWSService;
import com.handson.basic.util.Dates;
import org.joda.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import java.util.Date;

@Entity
@SqlResultSetMapping(name = "MemberOut")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberOut {

    @Id
    private Long id;

    private Date createdat;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("createdat")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdat);
    }

    private String fullname;
    private Date birthdate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("birthdate")
    public LocalDateTime calcBirthDate() {
        return Dates.atLocalTime(birthdate);
    }

//    private String username;

    private String profilepicture;
//    private Double avgscore;

    public static MemberOut of(Member member, AWSService awsService) {
        MemberOut res = new MemberOut();
        res.id = member.getId();
        res.createdat = member.getCreatedAt();
        res.fullname = member.getFullname();
        res.birthdate = member.getBirthDate();
//        res.username = member.getUsername();
        res.profilepicture = awsService.generateLink(member.getProfilePicture());
//        res.avgscore = null;
        return res;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

//    public Double getAvgscore() {
//        return avgscore;

    public Date getCreatedat() {
        return createdat;
    }

    public String getFullname() {
        return fullname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getUsername() {
//        return username;
//    }

    public String getProfilePicture() {
        return profilepicture;
    }
}
