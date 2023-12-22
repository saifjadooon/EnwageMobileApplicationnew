
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSkillsResponse;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Datum {

    @SerializedName("applicantSkillId")
    @Expose
    private Integer applicantSkillId;
    @SerializedName("applicantId")
    @Expose
    private Object applicantId;
    @SerializedName("skillName")
    @Expose
    private String skillName;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("modifiedBy")
    @Expose
    private Object modifiedBy;
    @SerializedName("modifiedDate")
    @Expose
    private Object modifiedDate;

    public Integer getApplicantSkillId() {
        return applicantSkillId;
    }

    public void setApplicantSkillId(Integer applicantSkillId) {
        this.applicantSkillId = applicantSkillId;
    }

    public Object getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Object applicantId) {
        this.applicantId = applicantId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Object modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Object getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Object modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
