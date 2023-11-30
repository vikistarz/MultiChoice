package com.company.mulitchoice.dashboardPage;

import android.widget.ImageView;

public class SliderData {
    private int pictures;
    private String course;
    private String tools;
    private String tools2;


    public SliderData(int pictures, String course, String tools, String tools2) {
        this.pictures = pictures;
        this.course = course;
        this.tools = tools;
        this.tools2 = tools2;
    }
        public SliderData(int pictures, String course, String tools) {
            this.pictures = pictures;
            this.course = course;
            this.tools = tools;
            this.tools2 = tools2;
    }

    public int getPictures() {
        return pictures;
    }

    public void setPictures(int pictures) {
        this.pictures = pictures;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getTools2() {
        return tools2;
    }

    public void setTools2(String tools2) {
        this.tools2 = tools2;
    }
}
