
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyDomainResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCompanyDomainResponse {

    @SerializedName("Response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
