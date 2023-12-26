
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyDomainResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("companyDomain")
    @Expose
    private String companyDomain;
    @SerializedName("primaryColor")
    @Expose
    private String primaryColor;
    @SerializedName("secondaryColor")
    @Expose
    private String secondaryColor;
    @SerializedName("companyFavIcon")
    @Expose
    private String companyFavIcon;
    @SerializedName("companyLogo")
    @Expose
    private String companyLogo;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;

    public String getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(String companyDomain) {
        this.companyDomain = companyDomain;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getCompanyFavIcon() {
        return companyFavIcon;
    }

    public void setCompanyFavIcon(String companyFavIcon) {
        this.companyFavIcon = companyFavIcon;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

}
