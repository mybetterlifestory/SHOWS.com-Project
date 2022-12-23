package com.handson.basic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handson.basic.util.Dates;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDate;

import java.io.Serializable;

import static com.handson.basic.model.Member.MemberBuilder.aMember;

public class MemberIn implements Serializable {

    @Length(max = 60)
    private String fullname;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Length(max = 20)
    private String username;

    @Length(max = 40)
    private String email;

    @Length(max = 20)
    private String password;

    private String role;

    public Member toMember() {
        return aMember().fullname(fullname)
                .birthDate(Dates.atUtc(birthDate))
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    public void updateMember(Member member) {
        member.setFullname(fullname);
        member.setBirthDate(Dates.atUtc(birthDate));
        member.setUsername(username);
        member.setEmail(email);
        member.setPassword(password);
        member.setRole(role);
    }

    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
