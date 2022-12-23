package com.handson.basic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handson.basic.util.Dates;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDate;

import java.io.Serializable;

import static com.handson.basic.model.Member.MemberBuilder.aMember;
import static com.handson.basic.model.Show.ShowBuilder.aShow;

public class ShowIn implements Serializable {

    @Length(max = 60)
    private static String name;

    @JsonFormat(pattern="yyyy-MM-dd")
    private static LocalDate premiered;

    @JsonFormat(pattern="yyyy-MM-dd")
    private static LocalDate ended;

    @Length(max = 2000)
    private static String summery;

    @Length(max = 100)
    private static String genres;

//    @Length(max = 10)
    private static String status;

    private static Integer rating;

    public static Show toShow() {
        return aShow().name(name)
                .premiered(Dates.atUtc(premiered))
                .ended(Dates.atUtc(ended))
                .summery(summery)
                .genres(genres)
                .status(status)
                .rating(rating)
                .build();
    }

    public void updateShow(Show show) {
        show.setName(name);
        show.setPremiered(Dates.atUtc(premiered));
        show.setEnded(Dates.atUtc(ended));
        show.setSummery(summery);
        show.setGenres(genres);
        show.setStatus(status);
        show.setRating(rating);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPremiered() {
        return premiered;
    }
    public void setPremiered(LocalDate premiered) { this.premiered = premiered; }

    public LocalDate getEnded() {
        return ended;
    }
    public void setEnded(LocalDate ended) { this.ended = ended; }

    public String getSummery() {
        return summery;
    }
    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getGenres() {
        return genres;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
