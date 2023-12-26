
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsAddJobList;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Datum {

    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
