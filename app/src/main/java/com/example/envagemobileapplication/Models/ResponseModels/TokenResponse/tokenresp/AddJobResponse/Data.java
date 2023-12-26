
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddJobResponse;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Data {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("alreadyExist")
    @Expose
    private Boolean alreadyExist;
    @SerializedName("allRecordNotImportable")
    @Expose
    private Boolean allRecordNotImportable;
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

    public Boolean getAllRecordNotImportable() {
        return allRecordNotImportable;
    }

    public void setAllRecordNotImportable(Boolean allRecordNotImportable) {
        this.allRecordNotImportable = allRecordNotImportable;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

}
