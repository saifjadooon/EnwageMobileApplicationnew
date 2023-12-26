
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Record {

    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("industryName")
    @Expose
    private Object industryName;
    @SerializedName("taxId")
    @Expose
    private Object taxId;
    @SerializedName("jobCount")
    @Expose
    private Integer jobCount;
    @SerializedName("onboardingStatusId")
    @Expose
    private Integer onboardingStatusId;
    @SerializedName("onboardingStatus")
    @Expose
    private String onboardingStatus;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;
    @SerializedName("contractStart")
    @Expose
    private Object contractStart;
    @SerializedName("contractEnd")
    @Expose
    private Object contractEnd;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("clientName")
    @Expose
    private Object clientName;
    @SerializedName("clientDba")
    @Expose
    private Object clientDba;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("profilePicturePath")
    @Expose
    private Object profilePicturePath;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("primaryAddress1")
    @Expose
    private String primaryAddress1;
    @SerializedName("primaryAddress2")
    @Expose
    private String primaryAddress2;
    @SerializedName("primaryAddressCountry")
    @Expose
    private String primaryAddressCountry;
    @SerializedName("primaryAddressCity")
    @Expose
    private String primaryAddressCity;
    @SerializedName("primaryAddressState")
    @Expose
    private String primaryAddressState;
    @SerializedName("primaryAddressZipcode")
    @Expose
    private String primaryAddressZipcode;
    @SerializedName("billingAddress1")
    @Expose
    private Object billingAddress1;
    @SerializedName("billingAddress2")
    @Expose
    private Object billingAddress2;
    @SerializedName("billingAddressCountry")
    @Expose
    private String billingAddressCountry;
    @SerializedName("billingAddressCity")
    @Expose
    private String billingAddressCity;
    @SerializedName("billingAddressState")
    @Expose
    private String billingAddressState;
    @SerializedName("billingZipcode")
    @Expose
    private String billingZipcode;
    @SerializedName("websiteUrl")
    @Expose
    private String websiteUrl;
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
    private String modifiedDate;
    @SerializedName("visibilityStatus")
    @Expose
    private String visibilityStatus;
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
    private Object location;
    @SerializedName("primaryAddressLocation")
    @Expose
    private Object primaryAddressLocation;
    @SerializedName("billingAddressLocation")
    @Expose
    private Object billingAddressLocation;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("fein")
    @Expose
    private Object fein;
    @SerializedName("naicsCode")
    @Expose
    private Object naicsCode;
    @SerializedName("sicCode")
    @Expose
    private Object sicCode;
    @SerializedName("payrollSicCodeSetupId")
    @Expose
    private Object payrollSicCodeSetupId;
    @SerializedName("payrollNaicsCodeId")
    @Expose
    private Object payrollNaicsCodeId;
    @SerializedName("businessEntityType")
    @Expose
    private String businessEntityType;
    @SerializedName("phoneExt")
    @Expose
    private Object phoneExt;
    @SerializedName("setupFee")
    @Expose
    private Object setupFee;
    @SerializedName("serviceChargesType")
    @Expose
    private Object serviceChargesType;
    @SerializedName("serviceChargesAmount")
    @Expose
    private Object serviceChargesAmount;
    @SerializedName("backgroundScreeningType")
    @Expose
    private Object backgroundScreeningType;
    @SerializedName("backgroundScreeningAmount")
    @Expose
    private Object backgroundScreeningAmount;
    @SerializedName("textMessagesType")
    @Expose
    private Object textMessagesType;
    @SerializedName("textMessagesAmount")
    @Expose
    private Object textMessagesAmount;
    @SerializedName("resumeParsingType")
    @Expose
    private Object resumeParsingType;
    @SerializedName("resumeParsingAmount")
    @Expose
    private Object resumeParsingAmount;
    @SerializedName("owner")
    @Expose
    private Integer owner;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("primaryPhone")
    @Expose
    private Object primaryPhone;
    @SerializedName("billingPhone")
    @Expose
    private Object billingPhone;
    @SerializedName("subscriptionPlan")
    @Expose
    private Object subscriptionPlan;
    @SerializedName("planFee")
    @Expose
    private Object planFee;
    @SerializedName("invoiceStartDate")
    @Expose
    private Object invoiceStartDate;
    @SerializedName("billCycle")
    @Expose
    private Object billCycle;
    @SerializedName("billingDayOfWeek")
    @Expose
    private Object billingDayOfWeek;
    @SerializedName("billingDay")
    @Expose
    private Object billingDay;
    @SerializedName("billingMonth")
    @Expose
    private Object billingMonth;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Object getIndustryName() {
        return industryName;
    }

    public void setIndustryName(Object industryName) {
        this.industryName = industryName;
    }

    public Object getTaxId() {
        return taxId;
    }

    public void setTaxId(Object taxId) {
        this.taxId = taxId;
    }

    public Integer getJobCount() {
        return jobCount;
    }

    public void setJobCount(Integer jobCount) {
        this.jobCount = jobCount;
    }

    public Integer getOnboardingStatusId() {
        return onboardingStatusId;
    }

    public void setOnboardingStatusId(Integer onboardingStatusId) {
        this.onboardingStatusId = onboardingStatusId;
    }

    public String getOnboardingStatus() {
        return onboardingStatus;
    }

    public void setOnboardingStatus(String onboardingStatus) {
        this.onboardingStatus = onboardingStatus;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public Object getContractStart() {
        return contractStart;
    }

    public void setContractStart(Object contractStart) {
        this.contractStart = contractStart;
    }

    public Object getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Object contractEnd) {
        this.contractEnd = contractEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getClientName() {
        return clientName;
    }

    public void setClientName(Object clientName) {
        this.clientName = clientName;
    }

    public Object getClientDba() {
        return clientDba;
    }

    public void setClientDba(Object clientDba) {
        this.clientDba = clientDba;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Object getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(Object profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public String getPrimaryAddress1() {
        return primaryAddress1;
    }

    public void setPrimaryAddress1(String primaryAddress1) {
        this.primaryAddress1 = primaryAddress1;
    }

    public String getPrimaryAddress2() {
        return primaryAddress2;
    }

    public void setPrimaryAddress2(String primaryAddress2) {
        this.primaryAddress2 = primaryAddress2;
    }

    public String getPrimaryAddressCountry() {
        return primaryAddressCountry;
    }

    public void setPrimaryAddressCountry(String primaryAddressCountry) {
        this.primaryAddressCountry = primaryAddressCountry;
    }

    public String getPrimaryAddressCity() {
        return primaryAddressCity;
    }

    public void setPrimaryAddressCity(String primaryAddressCity) {
        this.primaryAddressCity = primaryAddressCity;
    }

    public String getPrimaryAddressState() {
        return primaryAddressState;
    }

    public void setPrimaryAddressState(String primaryAddressState) {
        this.primaryAddressState = primaryAddressState;
    }

    public String getPrimaryAddressZipcode() {
        return primaryAddressZipcode;
    }

    public void setPrimaryAddressZipcode(String primaryAddressZipcode) {
        this.primaryAddressZipcode = primaryAddressZipcode;
    }

    public Object getBillingAddress1() {
        return billingAddress1;
    }

    public void setBillingAddress1(Object billingAddress1) {
        this.billingAddress1 = billingAddress1;
    }

    public Object getBillingAddress2() {
        return billingAddress2;
    }

    public void setBillingAddress2(Object billingAddress2) {
        this.billingAddress2 = billingAddress2;
    }

    public String getBillingAddressCountry() {
        return billingAddressCountry;
    }

    public void setBillingAddressCountry(String billingAddressCountry) {
        this.billingAddressCountry = billingAddressCountry;
    }

    public String getBillingAddressCity() {
        return billingAddressCity;
    }

    public void setBillingAddressCity(String billingAddressCity) {
        this.billingAddressCity = billingAddressCity;
    }

    public String getBillingAddressState() {
        return billingAddressState;
    }

    public void setBillingAddressState(String billingAddressState) {
        this.billingAddressState = billingAddressState;
    }

    public String getBillingZipcode() {
        return billingZipcode;
    }

    public void setBillingZipcode(String billingZipcode) {
        this.billingZipcode = billingZipcode;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
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

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public void setVisibilityStatus(String visibilityStatus) {
        this.visibilityStatus = visibilityStatus;
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

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Object getPrimaryAddressLocation() {
        return primaryAddressLocation;
    }

    public void setPrimaryAddressLocation(Object primaryAddressLocation) {
        this.primaryAddressLocation = primaryAddressLocation;
    }

    public Object getBillingAddressLocation() {
        return billingAddressLocation;
    }

    public void setBillingAddressLocation(Object billingAddressLocation) {
        this.billingAddressLocation = billingAddressLocation;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getFein() {
        return fein;
    }

    public void setFein(Object fein) {
        this.fein = fein;
    }

    public Object getNaicsCode() {
        return naicsCode;
    }

    public void setNaicsCode(Object naicsCode) {
        this.naicsCode = naicsCode;
    }

    public Object getSicCode() {
        return sicCode;
    }

    public void setSicCode(Object sicCode) {
        this.sicCode = sicCode;
    }

    public Object getPayrollSicCodeSetupId() {
        return payrollSicCodeSetupId;
    }

    public void setPayrollSicCodeSetupId(Object payrollSicCodeSetupId) {
        this.payrollSicCodeSetupId = payrollSicCodeSetupId;
    }

    public Object getPayrollNaicsCodeId() {
        return payrollNaicsCodeId;
    }

    public void setPayrollNaicsCodeId(Object payrollNaicsCodeId) {
        this.payrollNaicsCodeId = payrollNaicsCodeId;
    }

    public String getBusinessEntityType() {
        return businessEntityType;
    }

    public void setBusinessEntityType(String businessEntityType) {
        this.businessEntityType = businessEntityType;
    }

    public Object getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(Object phoneExt) {
        this.phoneExt = phoneExt;
    }

    public Object getSetupFee() {
        return setupFee;
    }

    public void setSetupFee(Object setupFee) {
        this.setupFee = setupFee;
    }

    public Object getServiceChargesType() {
        return serviceChargesType;
    }

    public void setServiceChargesType(Object serviceChargesType) {
        this.serviceChargesType = serviceChargesType;
    }

    public Object getServiceChargesAmount() {
        return serviceChargesAmount;
    }

    public void setServiceChargesAmount(Object serviceChargesAmount) {
        this.serviceChargesAmount = serviceChargesAmount;
    }

    public Object getBackgroundScreeningType() {
        return backgroundScreeningType;
    }

    public void setBackgroundScreeningType(Object backgroundScreeningType) {
        this.backgroundScreeningType = backgroundScreeningType;
    }

    public Object getBackgroundScreeningAmount() {
        return backgroundScreeningAmount;
    }

    public void setBackgroundScreeningAmount(Object backgroundScreeningAmount) {
        this.backgroundScreeningAmount = backgroundScreeningAmount;
    }

    public Object getTextMessagesType() {
        return textMessagesType;
    }

    public void setTextMessagesType(Object textMessagesType) {
        this.textMessagesType = textMessagesType;
    }

    public Object getTextMessagesAmount() {
        return textMessagesAmount;
    }

    public void setTextMessagesAmount(Object textMessagesAmount) {
        this.textMessagesAmount = textMessagesAmount;
    }

    public Object getResumeParsingType() {
        return resumeParsingType;
    }

    public void setResumeParsingType(Object resumeParsingType) {
        this.resumeParsingType = resumeParsingType;
    }

    public Object getResumeParsingAmount() {
        return resumeParsingAmount;
    }

    public void setResumeParsingAmount(Object resumeParsingAmount) {
        this.resumeParsingAmount = resumeParsingAmount;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Object getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(Object primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public Object getBillingPhone() {
        return billingPhone;
    }

    public void setBillingPhone(Object billingPhone) {
        this.billingPhone = billingPhone;
    }

    public Object getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(Object subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public Object getPlanFee() {
        return planFee;
    }

    public void setPlanFee(Object planFee) {
        this.planFee = planFee;
    }

    public Object getInvoiceStartDate() {
        return invoiceStartDate;
    }

    public void setInvoiceStartDate(Object invoiceStartDate) {
        this.invoiceStartDate = invoiceStartDate;
    }

    public Object getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(Object billCycle) {
        this.billCycle = billCycle;
    }

    public Object getBillingDayOfWeek() {
        return billingDayOfWeek;
    }

    public void setBillingDayOfWeek(Object billingDayOfWeek) {
        this.billingDayOfWeek = billingDayOfWeek;
    }

    public Object getBillingDay() {
        return billingDay;
    }

    public void setBillingDay(Object billingDay) {
        this.billingDay = billingDay;
    }

    public Object getBillingMonth() {
        return billingMonth;
    }

    public void setBillingMonth(Object billingMonth) {
        this.billingMonth = billingMonth;
    }

}
