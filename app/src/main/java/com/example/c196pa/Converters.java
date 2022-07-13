package com.example.c196pa;

import androidx.room.TypeConverter;


import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static ArrayList<Assessment> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Assessment>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Assessment> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


    @TypeConverter
    public static ArrayList<Class> fromStringClassList(String value) {
        Type listType = new TypeToken<ArrayList<Class>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListClassList(ArrayList<Class> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
