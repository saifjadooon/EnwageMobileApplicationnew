
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary;

import java.util.List;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class JobInfo {

    @SerializedName("jobId")
    @Expose
    private Integer jobId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("owner")
    @Expose
    private Integer owner;
    @SerializedName("industryId")
    @Expose
    private Object industryId;
    @SerializedName("jobType")
    @Expose
    private String jobType;
    @SerializedName("jobFrequency")
    @Expose
    private String jobFrequency;
    @SerializedName("positionName")
    @Expose
    private String positionName;
    @SerializedName("jobNature")
    @Expose
    private String jobNature;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("zipcode")
    @Expose
    private Object zipcode;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("headcount")
    @Expose
    private Integer headcount;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("expectedCloseDate")
    @Expose
    private Object expectedCloseDate;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("minimumSalary")
    @Expose
    private Double minimumSalary;
    @SerializedName("maximumSalary")
    @Expose
    private Double maximumSalary;
    @SerializedName("workingDays")
    @Expose
    private String workingDays;
    @SerializedName("workingDaysNo")
    @Expose
    private Integer workingDaysNo;
    @SerializedName("estimatedHours")
    @Expose
    private Integer estimatedHours;
    @SerializedName("jopbDescription")
    @Expose
    private String jopbDescription;
    @SerializedName("packageDescription")
    @Expose
    private Object packageDescription;
    @SerializedName("isPublish")
    @Expose
    private Boolean isPublish;
    @SerializedName("attachmentName")
    @Expose
    private Object attachmentName;
    @SerializedName("attachmentPath")
    @Expose
    private Object attachmentPath;
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
    @SerializedName("jobStatusId")
    @Expose
    private Integer jobStatusId;
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("jobStatus")
    @Expose
    private String jobStatus;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;
    @SerializedName("clientProfilePicture")
    @Expose
    private String clientProfilePicture;
    @SerializedName("userName")
    @Expose
    private Object userName;
    @SerializedName("industryName")
    @Expose
    private Object industryName;
    @SerializedName("publishDate")
    @Expose
    private String publishDate;
    @SerializedName("workingDaysList")
    @Expose
    private List<String> workingDaysList;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("closingDate")
    @Expose
    private Object closingDate;
    @SerializedName("minPayRate")
    @Expose
    private Double minPayRate;
    @SerializedName("maxPayRate")
    @Expose
    private Double maxPayRate;
    @SerializedName("targetPayRate")
    @Expose
    private Double targetPayRate;
    @SerializedName("payrollPayGroupId")
    @Expose
    private Integer payrollPayGroupId;
    @SerializedName("payGroupName")
    @Expose
    private String payGroupName;
    @SerializedName("applicationFormId")
    @Expose
    private Object applicationFormId;
    @SerializedName("showSalary")
    @Expose
    private Object showSalary;
    @SerializedName("showNature")
    @Expose
    private Object showNature;
    @SerializedName("showClient")
    @Expose
    private Object showClient;
    @SerializedName("showIndustry")
    @Expose
    private Object showIndustry;
    @SerializedName("showAddress")
    @Expose
    private Object showAddress;
    @SerializedName("showType")
    @Expose
    private Object showType;
    @SerializedName("showShift")
    @Expose
    private Object showShift;
    @SerializedName("showSkills")
    @Expose
    private Object showSkills;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("numberOfWeeksPerYear")
    @Expose
    private Integer numberOfWeeksPerYear;
    @SerializedName("useTemplate")
    @Expose
    private Boolean useTemplate;
    @SerializedName("jobTemplateId")
    @Expose
    private Object jobTemplateId;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Object getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Object industryId) {
        this.industryId = industryId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobFrequency() {
        return jobFrequency;
    }

    public void setJobFrequency(String jobFrequency) {
        this.jobFrequency = jobFrequency;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getJobNature() {
        return jobNature;
    }

    public void setJobNature(String jobNature) {
        this.jobNature = jobNature;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getZipcode() {
        return zipcode;
    }

    public void setZipcode(Object zipcode) {
        this.zipcode = zipcode;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Integer getHeadcount() {
        return headcount;
    }

    public void setHeadcount(Integer headcount) {
        this.headcount = headcount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Object getExpectedCloseDate() {
        return expectedCloseDate;
    }

    public void setExpectedCloseDate(Object expectedCloseDate) {
        this.expectedCloseDate = expectedCloseDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(Double minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public Double getMaximumSalary() {
        return maximumSalary;
    }

    public void setMaximumSalary(Double maximumSalary) {
        this.maximumSalary = maximumSalary;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public Integer getWorkingDaysNo() {
        return workingDaysNo;
    }

    public void setWorkingDaysNo(Integer workingDaysNo) {
        this.workingDaysNo = workingDaysNo;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getJopbDescription() {
        return jopbDescription;
    }

    public void setJopbDescription(String jopbDescription) {
        this.jopbDescription = jopbDescription;
    }

    public Object getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(Object packageDescription) {
        this.packageDescription = packageDescription;
    }

    public Boolean getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Boolean isPublish) {
        this.isPublish = isPublish;
    }

    public Object getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(Object attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Object getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(Object attachmentPath) {
        this.attachmentPath = attachmentPath;
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

    public Integer getJobStatusId() {
        return jobStatusId;
    }

    public void setJobStatusId(Integer jobStatusId) {
        this.jobStatusId = jobStatusId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getClientProfilePicture() {
        return clientProfilePicture;
    }

    public void setClientProfilePicture(String clientProfilePicture) {
        this.clientProfilePicture = clientProfilePicture;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public Object getIndustryName() {
        return industryName;
    }

    public void setIndustryName(Object industryName) {
        this.industryName = industryName;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public List<String> getWorkingDaysList() {
        return workingDaysList;
    }

    public void setWorkingDaysList(List<String> workingDaysList) {
        this.workingDaysList = workingDaysList;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Object getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Object closingDate) {
        this.closingDate = closingDate;
    }

    public Double getMinPayRate() {
        return minPayRate;
    }

    public void setMinPayRate(Double minPayRate) {
        this.minPayRate = minPayRate;
    }

    public Double getMaxPayRate() {
        return maxPayRate;
    }

    public void setMaxPayRate(Double maxPayRate) {
        this.maxPayRate = maxPayRate;
    }

    public Double getTargetPayRate() {
        return targetPayRate;
    }

    public void setTargetPayRate(Double targetPayRate) {
        this.targetPayRate = targetPayRate;
    }

    public Integer getPayrollPayGroupId() {
        return payrollPayGroupId;
    }

    public void setPayrollPayGroupId(Integer payrollPayGroupId) {
        this.payrollPayGroupId = payrollPayGroupId;
    }

    public String getPayGroupName() {
        return payGroupName;
    }

    public void setPayGroupName(String payGroupName) {
        this.payGroupName = payGroupName;
    }

    public Object getApplicationFormId() {
        return applicationFormId;
    }

    public void setApplicationFormId(Object applicationFormId) {
        this.applicationFormId = applicationFormId;
    }

    public Object getShowSalary() {
        return showSalary;
    }

    public void setShowSalary(Object showSalary) {
        this.showSalary = showSalary;
    }

    public Object getShowNature() {
        return showNature;
    }

    public void setShowNature(Object showNature) {
        this.showNature = showNature;
    }

    public Object getShowClient() {
        return showClient;
    }

    public void setShowClient(Object showClient) {
        this.showClient = showClient;
    }

    public Object getShowIndustry() {
        return showIndustry;
    }

    public void setShowIndustry(Object showIndustry) {
        this.showIndustry = showIndustry;
    }

    public Object getShowAddress() {
        return showAddress;
    }

    public void setShowAddress(Object showAddress) {
        this.showAddress = showAddress;
    }

    public Object getShowType() {
        return showType;
    }

    public void setShowType(Object showType) {
        this.showType = showType;
    }

    public Object getShowShift() {
        return showShift;
    }

    public void setShowShift(Object showShift) {
        this.showShift = showShift;
    }

    public Object getShowSkills() {
        return showSkills;
    }

    public void setShowSkills(Object showSkills) {
        this.showSkills = showSkills;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getNumberOfWeeksPerYear() {
        return numberOfWeeksPerYear;
    }

    public void setNumberOfWeeksPerYear(Integer numberOfWeeksPerYear) {
        this.numberOfWeeksPerYear = numberOfWeeksPerYear;
    }

    public Boolean getUseTemplate() {
        return useTemplate;
    }

    public void setUseTemplate(Boolean useTemplate) {
        this.useTemplate = useTemplate;
    }

    public Object getJobTemplateId() {
        return jobTemplateId;
    }

    public void setJobTemplateId(Object jobTemplateId) {
        this.jobTemplateId = jobTemplateId;
    }

}
