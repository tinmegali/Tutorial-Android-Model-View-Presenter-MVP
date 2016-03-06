package com.tinmegali.mvp_tutorial.modelos;

/**
 * ---------------------------------------------------
 * Created by Tin Megali on 26/02/16.
 * Project: MVP_Tutorial
 * ---------------------------------------------------
 * <a href="http://www.tinmegali.com">tinmegali.com</a>
 * <a href="http://www.github.com/tinmegali>github</a>
 * ---------------------------------------------------
 *
 */
public class Note {

    private String text;
    private String date;

    public Note() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}
