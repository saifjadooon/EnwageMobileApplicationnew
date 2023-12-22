
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ZipCodeResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Datum {

    @SerializedName("zipCirtStateId")
    @Expose
    private Integer zipCirtStateId;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("stateCode")
    @Expose
    private String stateCode;

    public Integer getZipCirtStateId() {
        return zipCirtStateId;
    }

    public void setZipCirtStateId(Integer zipCirtStateId) {
        this.zipCirtStateId = zipCirtStateId;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

}
