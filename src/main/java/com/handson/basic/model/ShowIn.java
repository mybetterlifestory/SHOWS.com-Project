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

    public static Show toShow() {
        return aShow().premiered(Dates.atUtc(premiered)).name(name)
//        ended(Dates.atUtc(ended)).
                .build();
    }

    public void updateShow(Show show) {
        show.setName(name);
        show.setPremieredDate(Dates.atUtc(premiered));
//        show.setEndedDate(ended);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPremieredDate() {
        return premiered;
    }

    public void setPremieredDate(LocalDate premiered) {
        this.premiered = premiered;
    }

    public LocalDate getEndedDate() {
        return ended;
    }

    public void setEndedDate(LocalDate ended) { this.ended = ended; }
}
