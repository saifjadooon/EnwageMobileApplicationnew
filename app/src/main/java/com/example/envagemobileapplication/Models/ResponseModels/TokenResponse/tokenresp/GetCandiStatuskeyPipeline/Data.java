
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandiStatuskeyPipeline;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("keyPipelineId")
    @Expose
    private Integer keyPipelineId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
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
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("candidateVisibleToGuestJob")
    @Expose
    private Integer candidateVisibleToGuestJob;
    @SerializedName("candidateVisibleToGuestJobName")
    @Expose
    private String candidateVisibleToGuestJobName;
    @SerializedName("candidateSelectedInterview")
    @Expose
    private Integer candidateSelectedInterview;
    @SerializedName("candidateSelectedInterviewName")
    @Expose
    private String candidateSelectedInterviewName;
    @SerializedName("candidateReceiveOffer")
    @Expose
    private Integer candidateReceiveOffer;
    @SerializedName("candidateReceiveOfferName")
    @Expose
    private String candidateReceiveOfferName;
    @SerializedName("candidateHired")
    @Expose
    private Integer candidateHired;
    @SerializedName("candidateHiredName")
    @Expose
    private String candidateHiredName;
    @SerializedName("candidateDrop")
    @Expose
    private Integer candidateDrop;
    @SerializedName("candidateDropName")
    @Expose
    private String candidateDropName;
    @SerializedName("sequenceOfVisableTuGuest")
    @Expose
    private Integer sequenceOfVisableTuGuest;

    public Integer getKeyPipelineId() {
        return keyPipelineId;
    }

    public void setKeyPipelineId(Integer keyPipelineId) {
        this.keyPipelineId = keyPipelineId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getCandidateVisibleToGuestJob() {
        return candidateVisibleToGuestJob;
    }

    public void setCandidateVisibleToGuestJob(Integer candidateVisibleToGuestJob) {
        this.candidateVisibleToGuestJob = candidateVisibleToGuestJob;
    }

    public String getCandidateVisibleToGuestJobName() {
        return candidateVisibleToGuestJobName;
    }

    public void setCandidateVisibleToGuestJobName(String candidateVisibleToGuestJobName) {
        this.candidateVisibleToGuestJobName = candidateVisibleToGuestJobName;
    }

    public Integer getCandidateSelectedInterview() {
        return candidateSelectedInterview;
    }

    public void setCandidateSelectedInterview(Integer candidateSelectedInterview) {
        this.candidateSelectedInterview = candidateSelectedInterview;
    }

    public String getCandidateSelectedInterviewName() {
        return candidateSelectedInterviewName;
    }

    public void setCandidateSelectedInterviewName(String candidateSelectedInterviewName) {
        this.candidateSelectedInterviewName = candidateSelectedInterviewName;
    }

    public Integer getCandidateReceiveOffer() {
        return candidateReceiveOffer;
    }

    public void setCandidateReceiveOffer(Integer candidateReceiveOffer) {
        this.candidateReceiveOffer = candidateReceiveOffer;
    }

    public String getCandidateReceiveOfferName() {
        return candidateReceiveOfferName;
    }

    public void setCandidateReceiveOfferName(String candidateReceiveOfferName) {
        this.candidateReceiveOfferName = candidateReceiveOfferName;
    }

    public Integer getCandidateHired() {
        return candidateHired;
    }

    public void setCandidateHired(Integer candidateHired) {
        this.candidateHired = candidateHired;
    }

    public String getCandidateHiredName() {
        return candidateHiredName;
    }

    public void setCandidateHiredName(String candidateHiredName) {
        this.candidateHiredName = candidateHiredName;
    }

    public Integer getCandidateDrop() {
        return candidateDrop;
    }

    public void setCandidateDrop(Integer candidateDrop) {
        this.candidateDrop = candidateDrop;
    }

    public String getCandidateDropName() {
        return candidateDropName;
    }

    public void setCandidateDropName(String candidateDropName) {
        this.candidateDropName = candidateDropName;
    }

    public Integer getSequenceOfVisableTuGuest() {
        return sequenceOfVisableTuGuest;
    }

    public void setSequenceOfVisableTuGuest(Integer sequenceOfVisableTuGuest) {
        this.sequenceOfVisableTuGuest = sequenceOfVisableTuGuest;
    }

}
