
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetOfferLetterTemplates;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Record {

    @SerializedName("offerTemplateId")
    @Expose
    private Integer offerTemplateId;
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
    private String createByUserName;
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

    public Integer getOfferTemplateId() {
        return offerTemplateId;
    }

    public void setOfferTemplateId(Integer offerTemplateId) {
        this.offerTemplateId = offerTemplateId;
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

    public String getCreateByUserName() {
        return createByUserName;
    }

    public void setCreateByUserName(String createByUserName) {
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

}
