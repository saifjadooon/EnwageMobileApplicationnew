
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientcntctHderRes;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Client {

    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("industryName")
    @Expose
    private String industryName;
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
    private Object onboardingStatus;
    @SerializedName("colorCode")
    @Expose
    private Object colorCode;
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
    private String clientDba;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("profilePicturePath")
    @Expose
    private String profilePicturePath;
    @SerializedName("country")
    @Expose
    private String country;
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
    private String billingAddress1;
    @SerializedName("billingAddress2")
    @Expose
    private String billingAddress2;
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
    private String location;
    @SerializedName("primaryAddressLocation")
    @Expose
    private String primaryAddressLocation;
    @SerializedName("billingAddressLocation")
    @Expose
    private String billingAddressLocation;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("fein")
    @Expose
    private String fein;
    @SerializedName("naicsCode")
    @Expose
    private Object naicsCode;
    @SerializedName("sicCode")
    @Expose
    private Object sicCode;
    @SerializedName("payrollSicCodeSetupId")
    @Expose
    private Integer payrollSicCodeSetupId;
    @SerializedName("payrollNaicsCodeId")
    @Expose
    private Integer payrollNaicsCodeId;
    @SerializedName("businessEntityType")
    @Expose
    private String businessEntityType;
    @SerializedName("phoneExt")
    @Expose
    private String phoneExt;
    @SerializedName("setupFee")
    @Expose
    private Double setupFee;
    @SerializedName("serviceChargesType")
    @Expose
    private String serviceChargesType;
    @SerializedName("serviceChargesAmount")
    @Expose
    private Double serviceChargesAmount;
    @SerializedName("backgroundScreeningType")
    @Expose
    private String backgroundScreeningType;
    @SerializedName("backgroundScreeningAmount")
    @Expose
    private Double backgroundScreeningAmount;
    @SerializedName("textMessagesType")
    @Expose
    private String textMessagesType;
    @SerializedName("textMessagesAmount")
    @Expose
    private Double textMessagesAmount;
    @SerializedName("resumeParsingType")
    @Expose
    private String resumeParsingType;
    @SerializedName("resumeParsingAmount")
    @Expose
    private Double resumeParsingAmount;
    @SerializedName("owner")
    @Expose
    private Integer owner;
    @SerializedName("ownerName")
    @Expose
    private Object ownerName;
    @SerializedName("primaryPhone")
    @Expose
    private Object primaryPhone;
    @SerializedName("billingPhone")
    @Expose
    private String billingPhone;
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
    @SerializedName("systemDefaultDate")
    @Expose
    private String systemDefaultDate;
    @SerializedName("isSystemDefault")
    @Expose
    private Boolean isSystemDefault;
    @SerializedName("waitingPeriodType")
    @Expose
    private Object waitingPeriodType;
    @SerializedName("waitingPeriod")
    @Expose
    private Object waitingPeriod;

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

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
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

    public Object getOnboardingStatus() {
        return onboardingStatus;
    }

    public void setOnboardingStatus(Object onboardingStatus) {
        this.onboardingStatus = onboardingStatus;
    }

    public Object getColorCode() {
        return colorCode;
    }

    public void setColorCode(Object colorCode) {
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

    public String getClientDba() {
        return clientDba;
    }

    public void setClientDba(String clientDba) {
        this.clientDba = clientDba;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
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

    public String getBillingAddress1() {
        return billingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        this.billingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return billingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrimaryAddressLocation() {
        return primaryAddressLocation;
    }

    public void setPrimaryAddressLocation(String primaryAddressLocation) {
        this.primaryAddressLocation = primaryAddressLocation;
    }

    public String getBillingAddressLocation() {
        return billingAddressLocation;
    }

    public void setBillingAddressLocation(String billingAddressLocation) {
        this.billingAddressLocation = billingAddressLocation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFein() {
        return fein;
    }

    public void setFein(String fein) {
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

    public Integer getPayrollSicCodeSetupId() {
        return payrollSicCodeSetupId;
    }

    public void setPayrollSicCodeSetupId(Integer payrollSicCodeSetupId) {
        this.payrollSicCodeSetupId = payrollSicCodeSetupId;
    }

    public Integer getPayrollNaicsCodeId() {
        return payrollNaicsCodeId;
    }

    public void setPayrollNaicsCodeId(Integer payrollNaicsCodeId) {
        this.payrollNaicsCodeId = payrollNaicsCodeId;
    }

    public String getBusinessEntityType() {
        return businessEntityType;
    }

    public void setBusinessEntityType(String businessEntityType) {
        this.businessEntityType = businessEntityType;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public Double getSetupFee() {
        return setupFee;
    }

    public void setSetupFee(Double setupFee) {
        this.setupFee = setupFee;
    }

    public String getServiceChargesType() {
        return serviceChargesType;
    }

    public void setServiceChargesType(String serviceChargesType) {
        this.serviceChargesType = serviceChargesType;
    }

    public Double getServiceChargesAmount() {
        return serviceChargesAmount;
    }

    public void setServiceChargesAmount(Double serviceChargesAmount) {
        this.serviceChargesAmount = serviceChargesAmount;
    }

    public String getBackgroundScreeningType() {
        return backgroundScreeningType;
    }

    public void setBackgroundScreeningType(String backgroundScreeningType) {
        this.backgroundScreeningType = backgroundScreeningType;
    }

    public Double getBackgroundScreeningAmount() {
        return backgroundScreeningAmount;
    }

    public void setBackgroundScreeningAmount(Double backgroundScreeningAmount) {
        this.backgroundScreeningAmount = backgroundScreeningAmount;
    }

    public String getTextMessagesType() {
        return textMessagesType;
    }

    public void setTextMessagesType(String textMessagesType) {
        this.textMessagesType = textMessagesType;
    }

    public Double getTextMessagesAmount() {
        return textMessagesAmount;
    }

    public void setTextMessagesAmount(Double textMessagesAmount) {
        this.textMessagesAmount = textMessagesAmount;
    }

    public String getResumeParsingType() {
        return resumeParsingType;
    }

    public void setResumeParsingType(String resumeParsingType) {
        this.resumeParsingType = resumeParsingType;
    }

    public Double getResumeParsingAmount() {
        return resumeParsingAmount;
    }

    public void setResumeParsingAmount(Double resumeParsingAmount) {
        this.resumeParsingAmount = resumeParsingAmount;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Object getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(Object ownerName) {
        this.ownerName = ownerName;
    }

    public Object getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(Object primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public void setBillingPhone(String billingPhone) {
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

    public String getSystemDefaultDate() {
        return systemDefaultDate;
    }

    public void setSystemDefaultDate(String systemDefaultDate) {
        this.systemDefaultDate = systemDefaultDate;
    }

    public Boolean getIsSystemDefault() {
        return isSystemDefault;
    }

    public void setIsSystemDefault(Boolean isSystemDefault) {
        this.isSystemDefault = isSystemDefault;
    }

    public Object getWaitingPeriodType() {
        return waitingPeriodType;
    }

    public void setWaitingPeriodType(Object waitingPeriodType) {
        this.waitingPeriodType = waitingPeriodType;
    }

    public Object getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(Object waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

}
