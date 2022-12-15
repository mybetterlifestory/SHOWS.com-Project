package com.handson.basic.model;

public enum ShowSortField {
    id("s.id") ,
    createdAt ("s.created_at"),
    name ("s.name"),
    premiered ("s.premiered"),
    ended ("s.ended"),
    image ("s.image");

    public final String fieldName;
    private ShowSortField(String fieldName) {
        this.fieldName = fieldName;
    }
}
