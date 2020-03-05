package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class get_data_notification {
    @SerializedName("title")
    String title;

    @SerializedName("note")
    String note;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
