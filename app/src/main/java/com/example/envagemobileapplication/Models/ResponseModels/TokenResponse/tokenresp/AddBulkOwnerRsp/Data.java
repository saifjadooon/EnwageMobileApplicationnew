
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddBulkOwnerRsp;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Data {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("alreadyExist")
    @Expose
    private Boolean alreadyExist;
    @SerializedName("error")
    @Expose
    private Boolean error;

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

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

}
