
package com.example.envagemobileapplication.Models.SocialMediaResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Data {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("alreadyExist")
    @Expose
    private Boolean alreadyExist;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAlreadyExist() {
        return alreadyExist;
    }

    public void setAlreadyExist(Boolean alreadyExist) {
        this.alreadyExist = alreadyExist;
    }

}
