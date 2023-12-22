
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRecentJobsRes;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("candidateJobId")
    @Expose
    private Integer candidateJobId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("candidateId")
    @Expose
    private Integer candidateId;
    @SerializedName("jobId")
    @Expose
    private Integer jobId;
    @SerializedName("positionName")
    @Expose
    private String positionName;
    @SerializedName("jobStatusId")
    @Expose
    private Integer jobStatusId;
    @SerializedName("jobStatus")
    @Expose
    private String jobStatus;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("clientProfilePicture")
    @Expose
    private String clientProfilePicture;
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("jobType")
    @Expose
    private String jobType;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("jobNature")
    @Expose
    private String jobNature;
    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusColorCode")
    @Expose
    private String statusColorCode;
    @SerializedName("payRate")
    @Expose
    private Integer payRate;
    @SerializedName("billRate")
    @Expose
    private Integer billRate;
    @SerializedName("overtimePayRate")
    @Expose
    private Integer overtimePayRate;
    @SerializedName("overtimeBillRate")
    @Expose
    private Integer overtimeBillRate;
    @SerializedName("doublePayRate")
    @Expose
    private Integer doublePayRate;
    @SerializedName("doubleBillRate")
    @Expose
    private Integer doubleBillRate;
    @SerializedName("statusSequence")
    @Expose
    private Integer statusSequence;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("offerSent")
    @Expose
    private Boolean offerSent;
    @SerializedName("templateId")
    @Expose
    private Integer templateId;
    @SerializedName("offerLetterLink")
    @Expose
    private String offerLetterLink;
    @SerializedName("lastStatusId")
    @Expose
    private Integer lastStatusId;
    @SerializedName("lastStatus")
    @Expose
    private String lastStatus;
    @SerializedName("lastStatusColorCode")
    @Expose
    private String lastStatusColorCode;
    @SerializedName("joiningDate")
    @Expose
    private String joiningDate;
    @SerializedName("offeredSalary")
    @Expose
    private Integer offeredSalary;
    @SerializedName("jobGuid")
    @Expose
    private String jobGuid;

    public Integer getCandidateJobId() {
        return candidateJobId;
    }

    public void setCandidateJobId(Integer candidateJobId) {
        this.candidateJobId = candidateJobId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getJobStatusId() {
        return jobStatusId;
    }

    public void setJobStatusId(Integer jobStatusId) {
        this.jobStatusId = jobStatusId;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientProfilePicture() {
        return clientProfilePicture;
    }

    public void setClientProfilePicture(String clientProfilePicture) {
        this.clientProfilePicture = clientProfilePicture;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobNature() {
        return jobNature;
    }

    public void setJobNature(String jobNature) {
        this.jobNature = jobNature;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusColorCode() {
        return statusColorCode;
    }

    public void setStatusColorCode(String statusColorCode) {
        this.statusColorCode = statusColorCode;
    }

    public Integer getPayRate() {
        return payRate;
    }

    public void setPayRate(Integer payRate) {
        this.payRate = payRate;
    }

    public Integer getBillRate() {
        return billRate;
    }

    public void setBillRate(Integer billRate) {
        this.billRate = billRate;
    }

    public Integer getOvertimePayRate() {
        return overtimePayRate;
    }

    public void setOvertimePayRate(Integer overtimePayRate) {
        this.overtimePayRate = overtimePayRate;
    }

    public Integer getOvertimeBillRate() {
        return overtimeBillRate;
    }

    public void setOvertimeBillRate(Integer overtimeBillRate) {
        this.overtimeBillRate = overtimeBillRate;
    }

    public Integer getDoublePayRate() {
        return doublePayRate;
    }

    public void setDoublePayRate(Integer doublePayRate) {
        this.doublePayRate = doublePayRate;
    }

    public Integer getDoubleBillRate() {
        return doubleBillRate;
    }

    public void setDoubleBillRate(Integer doubleBillRate) {
        this.doubleBillRate = doubleBillRate;
    }

    public Integer getStatusSequence() {
        return statusSequence;
    }

    public void setStatusSequence(Integer statusSequence) {
        this.statusSequence = statusSequence;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getOfferSent() {
        return offerSent;
    }

    public void setOfferSent(Boolean offerSent) {
        this.offerSent = offerSent;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getOfferLetterLink() {
        return offerLetterLink;
    }

    public void setOfferLetterLink(String offerLetterLink) {
        this.offerLetterLink = offerLetterLink;
    }

    public Integer getLastStatusId() {
        return lastStatusId;
    }

    public void setLastStatusId(Integer lastStatusId) {
        this.lastStatusId = lastStatusId;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getLastStatusColorCode() {
        return lastStatusColorCode;
    }

    public void setLastStatusColorCode(String lastStatusColorCode) {
        this.lastStatusColorCode = lastStatusColorCode;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Integer getOfferedSalary() {
        return offeredSalary;
    }

    public void setOfferedSalary(Integer offeredSalary) {
        this.offeredSalary = offeredSalary;
    }

    public String getJobGuid() {
        return jobGuid;
    }

    public void setJobGuid(String jobGuid) {
        this.jobGuid = jobGuid;
    }

}
