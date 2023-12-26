
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class PayGroup {

    @SerializedName("payrollPayGroupId")
    @Expose
    private Integer payrollPayGroupId;
    @SerializedName("payrollPayGroupTitle")
    @Expose
    private String payrollPayGroupTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("periodStartDate")
    @Expose
    private String periodStartDate;
    @SerializedName("periodEndDate")
    @Expose
    private String periodEndDate;
    @SerializedName("payDate")
    @Expose
    private String payDate;
    @SerializedName("periodNumber")
    @Expose
    private Integer periodNumber;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("modifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("groupType")
    @Expose
    private String groupType;
    @SerializedName("workWeekEndsOn")
    @Expose
    private String workWeekEndsOn;
    @SerializedName("processingScheduleId")
    @Expose
    private Integer processingScheduleId;
    @SerializedName("payrollScheduleId")
    @Expose
    private Integer payrollScheduleId;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("ruleCode")
    @Expose
    private Object ruleCode;
    @SerializedName("payrollScheduleType")
    @Expose
    private Object payrollScheduleType;
    @SerializedName("processingScheduleNameType")
    @Expose
    private Object processingScheduleNameType;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("cutOffDate")
    @Expose
    private Object cutOffDate;

    public Integer getPayrollPayGroupId() {
        return payrollPayGroupId;
    }

    public void setPayrollPayGroupId(Integer payrollPayGroupId) {
        this.payrollPayGroupId = payrollPayGroupId;
    }

    public String getPayrollPayGroupTitle() {
        return payrollPayGroupTitle;
    }

    public void setPayrollPayGroupTitle(String payrollPayGroupTitle) {
        this.payrollPayGroupTitle = payrollPayGroupTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodStartDate(String periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    public String getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(String periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Integer getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(Integer periodNumber) {
        this.periodNumber = periodNumber;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getWorkWeekEndsOn() {
        return workWeekEndsOn;
    }

    public void setWorkWeekEndsOn(String workWeekEndsOn) {
        this.workWeekEndsOn = workWeekEndsOn;
    }

    public Integer getProcessingScheduleId() {
        return processingScheduleId;
    }

    public void setProcessingScheduleId(Integer processingScheduleId) {
        this.processingScheduleId = processingScheduleId;
    }

    public Integer getPayrollScheduleId() {
        return payrollScheduleId;
    }

    public void setPayrollScheduleId(Integer payrollScheduleId) {
        this.payrollScheduleId = payrollScheduleId;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(Object ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Object getPayrollScheduleType() {
        return payrollScheduleType;
    }

    public void setPayrollScheduleType(Object payrollScheduleType) {
        this.payrollScheduleType = payrollScheduleType;
    }

    public Object getProcessingScheduleNameType() {
        return processingScheduleNameType;
    }

    public void setProcessingScheduleNameType(Object processingScheduleNameType) {
        this.processingScheduleNameType = processingScheduleNameType;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Object getCutOffDate() {
        return cutOffDate;
    }

    public void setCutOffDate(Object cutOffDate) {
        this.cutOffDate = cutOffDate;
    }

}
