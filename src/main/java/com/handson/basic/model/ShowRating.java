package com.handson.basic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.Date;

@Entity
@Table(name="student_grade")
public class ShowRating implements Serializable {
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

    @JsonIgnore
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "showId")
    private Member member;

    @NotEmpty
    @Length(max = 60)
    private String showName;

    @Min(1)
    @Max(10)
    private Integer memberRating;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public String getShowName() {
        return showName;
    }
    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getMemberRating() {
        return memberRating;
    }
    public void setMemberRating(Integer memberRating) {
        this.memberRating = memberRating;
    }

    public static final class ShowRatingBuilder {
        private Long id;
        private Date createdAt = Dates.nowUTC();
        private Member member;
        private String showName;
        private Integer memberRating;

        private ShowRatingBuilder() {
        }

        public static ShowRatingBuilder aStudentGrade() {
            return new ShowRatingBuilder();
        }

        public ShowRatingBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ShowRatingBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ShowRatingBuilder member(Member member) {
            this.member = member;
            return this;
        }

        public ShowRatingBuilder showName(String showName) {
            this.showName = showName;
            return this;
        }

        public ShowRatingBuilder memberRating(Integer memberRating) {
            this.memberRating = memberRating;
            return this;
        }

        public ShowRating build() {
            ShowRating studentGrade = new ShowRating();
            studentGrade.member = this.member;
            studentGrade.showName = this.showName;
            studentGrade.memberRating = this.memberRating;
            studentGrade.id = this.id;
            studentGrade.createdAt = this.createdAt;
            return studentGrade;
        }
    }
}
