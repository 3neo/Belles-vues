package com.perls3.bellesvues.model.db;


import java.util.Date;

public class TypeConverter {

    @androidx.room.TypeConverter
    public Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @androidx.room.TypeConverter
    public  Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}