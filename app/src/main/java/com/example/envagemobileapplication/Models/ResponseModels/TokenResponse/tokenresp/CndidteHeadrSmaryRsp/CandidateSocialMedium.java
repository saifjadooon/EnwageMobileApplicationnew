
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteHeadrSmaryRsp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CandidateSocialMedium {

    @SerializedName("candidateSocialMediaId")
    @Expose
    private Integer candidateSocialMediaId;
    @SerializedName("candidateId")
    @Expose
    private Integer candidateId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logoPath")
    @Expose
    private Object logoPath;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    public Integer getCandidateSocialMediaId() {
        return candidateSocialMediaId;
    }

    public void setCandidateSocialMediaId(Integer candidateSocialMediaId) {
        this.candidateSocialMediaId = candidateSocialMediaId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(Object logoPath) {
        this.logoPath = logoPath;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
