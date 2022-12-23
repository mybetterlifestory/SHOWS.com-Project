package com.handson.basic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.handson.basic.util.Dates;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="member")
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Date createdAt = Dates.nowUTC();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("createdAt")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdAt);
    }

    @NotEmpty
    @Length(max = 60)
    private String fullname;

    private Date birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("birthDate")
    public LocalDateTime calcBirthDate() {
        return Dates.atLocalTime(birthDate);
    }

    @NotEmpty
//    @Unique
    @Length(max = 20)
    private String username;

    @Length(max = 40)
    private String email;

    @Length(max = 20)
    private String password;

    @Length(max = 500)
    private String profilePicture;

    private String role;

//    public Collection<StudentGrade> getStudentGrades() {
//        return studentGrades;
//    }

//    @OneToMany(mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private Collection<StudentGrade> studentGrades = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
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

    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public static final class MemberBuilder {
        private Long id;
        private Date createdAt = Dates.nowUTC();
        private String fullname;
        private Date birthDate;
        private String username;
        private String email;
        private String password;
        private String profilePicture;
        private String role;
        private MemberBuilder() {
        }

        public static MemberBuilder aMember() {
            return new MemberBuilder();
        }

        public MemberBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MemberBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MemberBuilder fullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public MemberBuilder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public MemberBuilder username(String username) {
            this.username = username;
            return this;
        }

        public MemberBuilder email(String email) {
            this.email = email;
            return this;
        }

        public MemberBuilder password(String password) {
            this.password = password;
            return this;
        }

        public MemberBuilder profilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }

        public MemberBuilder role(String role) {
            this.role = role;
            return this;
        }

        public Member build() {
            Member member = new Member();
            member.setCreatedAt(createdAt);
            member.setFullname(fullname);
            member.setBirthDate(birthDate);
            member.setUsername(username);
            member.setEmail(email);
            member.setPassword(password);
            member.setProfilePicture(profilePicture);
            member.setRole(role);
            member.id = this.id;
            return member;
        }
    }
}