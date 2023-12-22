
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class CompanyBadge {

    @SerializedName("companyBadgesId")
    @Expose
    private Integer companyBadgesId;
    @SerializedName("companyId")
    @Expose
    private Object companyId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("modifiedBy")
    @Expose
    private Object modifiedBy;
    @SerializedName("modifiedDate")
    @Expose
    private Object modifiedDate;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("assignedIsActive")
    @Expose
    private Object assignedIsActive;

    public Integer getCompanyBadgesId() {
        return companyBadgesId;
    }

    public void setCompanyBadgesId(Integer companyBadgesId) {
        this.companyBadgesId = companyBadgesId;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
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

    public Object getAssignedIsActive() {
        return assignedIsActive;
    }

    public void setAssignedIsActive(Object assignedIsActive) {
        this.assignedIsActive = assignedIsActive;
    }

}
