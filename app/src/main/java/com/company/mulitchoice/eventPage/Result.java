
package com.company.mulitchoice.eventPage;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Result {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("customerld")
    @Expose
    private String customerld;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("-id")
    @Expose
    private String id;
    @SerializedName("-v")
    @Expose
    private Integer v;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerld() {
        return customerld;
    }

    public void setCustomerld(String customerld) {
        this.customerld = customerld;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
