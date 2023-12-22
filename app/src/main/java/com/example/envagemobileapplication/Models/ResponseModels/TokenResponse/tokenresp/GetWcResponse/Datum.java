
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetWcResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("clientApprovedWccodeId")
    @Expose
    private Integer clientApprovedWccodeId;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("payrollWcRatesId")
    @Expose
    private Integer payrollWcRatesId;
    @SerializedName("jobWcClassificationCode")
    @Expose
    private String jobWcClassificationCode;
    @SerializedName("areaCovered")
    @Expose
    private String areaCovered;
    @SerializedName("branchCode")
    @Expose
    private String branchCode;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("billingRate")
    @Expose
    private String billingRate;
    @SerializedName("billingMode")
    @Expose
    private String billingMode;
    @SerializedName("wcBindingDate")
    @Expose
    private String wcBindingDate;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("approvalAttachment")
    @Expose
    private Object approvalAttachment;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
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

    public Integer getClientApprovedWccodeId() {
        return clientApprovedWccodeId;
    }

    public void setClientApprovedWccodeId(Integer clientApprovedWccodeId) {
        this.clientApprovedWccodeId = clientApprovedWccodeId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getPayrollWcRatesId() {
        return payrollWcRatesId;
    }

    public void setPayrollWcRatesId(Integer payrollWcRatesId) {
        this.payrollWcRatesId = payrollWcRatesId;
    }

    public String getJobWcClassificationCode() {
        return jobWcClassificationCode;
    }

    public void setJobWcClassificationCode(String jobWcClassificationCode) {
        this.jobWcClassificationCode = jobWcClassificationCode;
    }

    public String getAreaCovered() {
        return areaCovered;
    }

    public void setAreaCovered(String areaCovered) {
        this.areaCovered = areaCovered;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getBillingRate() {
        return billingRate;
    }

    public void setBillingRate(String billingRate) {
        this.billingRate = billingRate;
    }

    public String getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(String billingMode) {
        this.billingMode = billingMode;
    }

    public String getWcBindingDate() {
        return wcBindingDate;
    }

    public void setWcBindingDate(String wcBindingDate) {
        this.wcBindingDate = wcBindingDate;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getApprovalAttachment() {
        return approvalAttachment;
    }

    public void setApprovalAttachment(Object approvalAttachment) {
        this.approvalAttachment = approvalAttachment;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
