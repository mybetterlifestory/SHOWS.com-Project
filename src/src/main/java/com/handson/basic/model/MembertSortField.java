package com.handson.basic.model;

public enum MembertSortField {
    id("s.id") ,
    createdAt ("s.created_at"),
    fullName ("s.fullname"),
    birthDate ("s.birth_date"),
//    satScore ("s.at_score"),
//    graduationScore ("s.graduation_score"),
//    phone ("s.phone"),
    profilepicture ("s.profile_picture");
//    avgScore (" (select avg(sg.course_score) from  student_grade sg where sg.student_id = s.id ) ");

    public final String fieldName;
    private MembertSortField(String fieldName) {
        this.fieldName = fieldName;
    }
}
