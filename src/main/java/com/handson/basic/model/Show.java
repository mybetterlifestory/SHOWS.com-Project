package com.handson.basic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.handson.basic.util.Dates;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="show")
public class Show implements Serializable {
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
    private String name;

    private Date premiered;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("premiered")
    public LocalDateTime calcPremiered() {
        return Dates.atLocalTime(premiered);
    }

    private Date ended;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("ended")
    public LocalDateTime calcEnded() { return Dates.atLocalTime(ended); }

    @Length(max = 500)
    private String image;

    public Date getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPremieredDate() {
        return premiered;
    }

    public void setPremieredDate(Date premiered) {
        this.premiered = premiered;
    }

    public Date getEndedDate() {
        return ended;
    }

    public void setEndedDate(Date ended) {
        this.ended = ended;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public static final class ShowBuilder {
        private Long id;
        private Date createdAt = Dates.nowUTC();
        private String name;
        private Date premiered;
        private Date ended;
        private String image;

        private ShowBuilder() {
        }

        public static ShowBuilder aShow() {
            return new ShowBuilder();
        }

        public ShowBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Show.ShowBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ShowBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ShowBuilder premiered(Date premiered) {
            this.premiered = premiered;
            return this;
        }

        public ShowBuilder ended(Date ended) {
            this.ended = ended;
            return this;
        }

        public ShowBuilder image(String image) {
            this.image = image;
            return this;
        }

        public Show build() {
            Show show = new Show();
            show.setCreatedAt(createdAt);
            show.setName(name);
            show.setPremieredDate(premiered);
            show.setEndedDate(ended);
            show.setImage(image);
            show.id = this.id;
            return show;
        }
    }
}