
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSumryExpRes;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("candidateExperienceDetailsId")
    @Expose
    private Integer candidateExperienceDetailsId;
    @SerializedName("candidateId")
    @Expose
    private Integer candidateId;
    @SerializedName("candidateGUID")
    @Expose
    private String candidateGUID;
    @SerializedName("positionName")
    @Expose
    private String positionName;
    @SerializedName("employerName")
    @Expose
    private String employerName;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("isCurrentEmployeer")
    @Expose
    private Boolean isCurrentEmployeer;
    @SerializedName("employmentType")
    @Expose
    private String employmentType;
    @SerializedName("locationType")
    @Expose
    private String locationType;
    @SerializedName("employerLocation")
    @Expose
    private String employerLocation;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("modifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;

    public Integer getCandidateExperienceDetailsId() {
        return candidateExperienceDetailsId;
    }

    public void setCandidateExperienceDetailsId(Integer candidateExperienceDetailsId) {
        this.candidateExperienceDetailsId = candidateExperienceDetailsId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateGUID() {
        return candidateGUID;
    }

    public void setCandidateGUID(String candidateGUID) {
        this.candidateGUID = candidateGUID;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsCurrentEmployeer() {
        return isCurrentEmployeer;
    }

    public void setIsCurrentEmployeer(Boolean isCurrentEmployeer) {
        this.isCurrentEmployeer = isCurrentEmployeer;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getEmployerLocation() {
        return employerLocation;
    }

    public void setEmployerLocation(String employerLocation) {
        this.employerLocation = employerLocation;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
