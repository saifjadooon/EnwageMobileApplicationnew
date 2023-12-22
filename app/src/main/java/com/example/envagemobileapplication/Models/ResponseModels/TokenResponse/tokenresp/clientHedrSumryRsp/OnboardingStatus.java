
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class OnboardingStatus {

    @SerializedName("companyOnboardingStatusId")
    @Expose
    private Integer companyOnboardingStatusId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("onboardingStatusName")
    @Expose
    private String onboardingStatusName;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
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
    private Object modifiedDate;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;
    @SerializedName("tyleIndex")
    @Expose
    private Integer tyleIndex;
    @SerializedName("default")
    @Expose
    private Boolean _default;

    public Integer getCompanyOnboardingStatusId() {
        return companyOnboardingStatusId;
    }

    public void setCompanyOnboardingStatusId(Integer companyOnboardingStatusId) {
        this.companyOnboardingStatusId = companyOnboardingStatusId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getOnboardingStatusName() {
        return onboardingStatusName;
    }

    public void setOnboardingStatusName(String onboardingStatusName) {
        this.onboardingStatusName = onboardingStatusName;
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

    public Object getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Object modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public Integer getTyleIndex() {
        return tyleIndex;
    }

    public void setTyleIndex(Integer tyleIndex) {
        this.tyleIndex = tyleIndex;
    }

    public Boolean getDefault() {
        return _default;
    }

    public void setDefault(Boolean _default) {
        this._default = _default;
    }

}
