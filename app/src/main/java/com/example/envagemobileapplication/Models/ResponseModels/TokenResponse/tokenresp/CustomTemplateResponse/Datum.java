
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CustomTemplateResponse;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Datum {

    @SerializedName("jobTemplateId")
    @Expose
    private Integer jobTemplateId;
    @SerializedName("templateName")
    @Expose
    private String templateName;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("payrollPayGroupId")
    @Expose
    private Integer payrollPayGroupId;
    @SerializedName("industryId")
    @Expose
    private Integer industryId;
    @SerializedName("jobNature")
    @Expose
    private String jobNature;
    @SerializedName("jobType")
    @Expose
    private String jobType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
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
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("industryName")
    @Expose
    private String industryName;
    @SerializedName("payrollPayGroupTitle")
    @Expose
    private String payrollPayGroupTitle;

    public Integer getJobTemplateId() {
        return jobTemplateId;
    }

    public void setJobTemplateId(Integer jobTemplateId) {
        this.jobTemplateId = jobTemplateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getPayrollPayGroupId() {
        return payrollPayGroupId;
    }

    public void setPayrollPayGroupId(Integer payrollPayGroupId) {
        this.payrollPayGroupId = payrollPayGroupId;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getJobNature() {
        return jobNature;
    }

    public void setJobNature(String jobNature) {
        this.jobNature = jobNature;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getPayrollPayGroupTitle() {
        return payrollPayGroupTitle;
    }

    public void setPayrollPayGroupTitle(String payrollPayGroupTitle) {
        this.payrollPayGroupTitle = payrollPayGroupTitle;
    }

}
