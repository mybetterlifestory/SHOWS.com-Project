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
@SqlResultSetMapping(name = "ShowOut")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowOut {

    @Id
    private Long id;

    private Date createdat;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdat")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdat);
    }

    private String name;
    private Date premiered;
    private Date ended;
    private String summery;
    private String genres;
    private String status;
    private Integer rating;
    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("premiered")
    public LocalDateTime calcPremieredDate() {
        return Dates.atLocalTime(premiered);
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("ended")
    public LocalDateTime calcEndedDate() {
        return Dates.atLocalTime(ended);
    }

    public static ShowOut of(Show show, AWSService awsService) {
        ShowOut res = new ShowOut();
        res.id = show.getId();
        res.createdat = show.getCreatedAt();
        res.name = show.getName();
        res.premiered = show.getPremiered();
        res.ended = show.getEnded();
        res.summery = show.getSummery();
        res.genres = show.getGenres();
        res.status = show.getStatus();
        res.rating = show.getRating();
        res.image =   show.getImage();
        res.adjustImageUrl();
        return res;
    }

    public void adjustImageUrl() {
        if (this.image != null && !(this.image.startsWith("http"))){
            this.image = "https://s3.amazonaws.com/shows.com" + this.image;
        }
    }

    public Date getCreatedat() { return createdat; }
    public String getName() { return name; }
    public Date getPremiered() { return premiered; }
    public Date getEnded() { return ended; }
    public String getSummery() { return summery; }
    public String getGenres() { return genres; }
    public String getStatus() { return status; }
    public Integer getRating() { return rating; }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }
}
