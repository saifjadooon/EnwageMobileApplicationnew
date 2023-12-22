
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getEmailTemplateResponse;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Datum {

    @SerializedName("emailTemplateId")
    @Expose
    private Integer emailTemplateId;
    @SerializedName("templateName")
    @Expose
    private String templateName;
    @SerializedName("templatePath")
    @Expose
    private String templatePath;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createByUserName")
    @Expose
    private Object createByUserName;
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
    @SerializedName("specifiedTo")
    @Expose
    private String specifiedTo;
    @SerializedName("forStatus")
    @Expose
    private Integer forStatus;
    @SerializedName("statusName")
    @Expose
    private String statusName;
    @SerializedName("alreadyExist")
    @Expose
    private Boolean alreadyExist;

    public Integer getEmailTemplateId() {
        return emailTemplateId;
    }

    public void setEmailTemplateId(Integer emailTemplateId) {
        this.emailTemplateId = emailTemplateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
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

    public Object getCreateByUserName() {
        return createByUserName;
    }

    public void setCreateByUserName(Object createByUserName) {
        this.createByUserName = createByUserName;
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

    public String getSpecifiedTo() {
        return specifiedTo;
    }

    public void setSpecifiedTo(String specifiedTo) {
        this.specifiedTo = specifiedTo;
    }

    public Integer getForStatus() {
        return forStatus;
    }

    public void setForStatus(Integer forStatus) {
        this.forStatus = forStatus;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Boolean getAlreadyExist() {
        return alreadyExist;
    }

    public void setAlreadyExist(Boolean alreadyExist) {
        this.alreadyExist = alreadyExist;
    }

}
