
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Record {

    @SerializedName("applicantId")
    @Expose
    private Integer applicantId;
    @SerializedName("jobId")
    @Expose
    private Integer jobId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("graduationYear")
    @Expose
    private String graduationYear;
    @SerializedName("yearsOfExperience")
    @Expose
    private String yearsOfExperience;
    @SerializedName("currentSalary")
    @Expose
    private Double currentSalary;
    @SerializedName("expectedSalary")
    @Expose
    private Double expectedSalary;
    @SerializedName("resume")
    @Expose
    private Object resume;
    @SerializedName("coverLetter")
    @Expose
    private Object coverLetter;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("isReferral")
    @Expose
    private Boolean isReferral;
    @SerializedName("referByFirstName")
    @Expose
    private Object referByFirstName;
    @SerializedName("referByLastName")
    @Expose
    private Object referByLastName;
    @SerializedName("referByEmail")
    @Expose
    private Object referByEmail;
    @SerializedName("referByPhoneNumber")
    @Expose
    private Object referByPhoneNumber;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("modifiedBy")
    @Expose
    private Object modifiedBy;
    @SerializedName("referenceFirstName")
    @Expose
    private Object referenceFirstName;
    @SerializedName("referenceLastName")
    @Expose
    private Object referenceLastName;
    @SerializedName("referenceDesignation")
    @Expose
    private Object referenceDesignation;
    @SerializedName("referenceContactNumber")
    @Expose
    private Object referenceContactNumber;
    @SerializedName("referenceEmail")
    @Expose
    private Object referenceEmail;
    @SerializedName("referenceCompanyName")
    @Expose
    private Object referenceCompanyName;
    @SerializedName("extraDocument")
    @Expose
    private Object extraDocument;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("ssnNumber")
    @Expose
    private String ssnNumber;
    @SerializedName("alienRegistrationNumber")
    @Expose
    private Object alienRegistrationNumber;
    @SerializedName("alienRegistrationExpiryDate")
    @Expose
    private Object alienRegistrationExpiryDate;
    @SerializedName("legallyWorkable")
    @Expose
    private Boolean legallyWorkable;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("transportAvailability")
    @Expose
    private Boolean transportAvailability;
    @SerializedName("shift")
    @Expose
    private String shift;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("ethnicity")
    @Expose
    private String ethnicity;
    @SerializedName("extraDocOrignalFileName")
    @Expose
    private Object extraDocOrignalFileName;

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Double getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(Double currentSalary) {
        this.currentSalary = currentSalary;
    }

    public Double getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(Double expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public Object getResume() {
        return resume;
    }

    public void setResume(Object resume) {
        this.resume = resume;
    }

    public Object getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(Object coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsReferral() {
        return isReferral;
    }

    public void setIsReferral(Boolean isReferral) {
        this.isReferral = isReferral;
    }

    public Object getReferByFirstName() {
        return referByFirstName;
    }

    public void setReferByFirstName(Object referByFirstName) {
        this.referByFirstName = referByFirstName;
    }

    public Object getReferByLastName() {
        return referByLastName;
    }

    public void setReferByLastName(Object referByLastName) {
        this.referByLastName = referByLastName;
    }

    public Object getReferByEmail() {
        return referByEmail;
    }

    public void setReferByEmail(Object referByEmail) {
        this.referByEmail = referByEmail;
    }

    public Object getReferByPhoneNumber() {
        return referByPhoneNumber;
    }

    public void setReferByPhoneNumber(Object referByPhoneNumber) {
        this.referByPhoneNumber = referByPhoneNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Object modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Object getReferenceFirstName() {
        return referenceFirstName;
    }

    public void setReferenceFirstName(Object referenceFirstName) {
        this.referenceFirstName = referenceFirstName;
    }

    public Object getReferenceLastName() {
        return referenceLastName;
    }

    public void setReferenceLastName(Object referenceLastName) {
        this.referenceLastName = referenceLastName;
    }

    public Object getReferenceDesignation() {
        return referenceDesignation;
    }

    public void setReferenceDesignation(Object referenceDesignation) {
        this.referenceDesignation = referenceDesignation;
    }

    public Object getReferenceContactNumber() {
        return referenceContactNumber;
    }

    public void setReferenceContactNumber(Object referenceContactNumber) {
        this.referenceContactNumber = referenceContactNumber;
    }

    public Object getReferenceEmail() {
        return referenceEmail;
    }

    public void setReferenceEmail(Object referenceEmail) {
        this.referenceEmail = referenceEmail;
    }

    public Object getReferenceCompanyName() {
        return referenceCompanyName;
    }

    public void setReferenceCompanyName(Object referenceCompanyName) {
        this.referenceCompanyName = referenceCompanyName;
    }

    public Object getExtraDocument() {
        return extraDocument;
    }

    public void setExtraDocument(Object extraDocument) {
        this.extraDocument = extraDocument;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSsnNumber() {
        return ssnNumber;
    }

    public void setSsnNumber(String ssnNumber) {
        this.ssnNumber = ssnNumber;
    }

    public Object getAlienRegistrationNumber() {
        return alienRegistrationNumber;
    }

    public void setAlienRegistrationNumber(Object alienRegistrationNumber) {
        this.alienRegistrationNumber = alienRegistrationNumber;
    }

    public Object getAlienRegistrationExpiryDate() {
        return alienRegistrationExpiryDate;
    }

    public void setAlienRegistrationExpiryDate(Object alienRegistrationExpiryDate) {
        this.alienRegistrationExpiryDate = alienRegistrationExpiryDate;
    }

    public Boolean getLegallyWorkable() {
        return legallyWorkable;
    }

    public void setLegallyWorkable(Boolean legallyWorkable) {
        this.legallyWorkable = legallyWorkable;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getTransportAvailability() {
        return transportAvailability;
    }

    public void setTransportAvailability(Boolean transportAvailability) {
        this.transportAvailability = transportAvailability;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Object getExtraDocOrignalFileName() {
        return extraDocOrignalFileName;
    }

    public void setExtraDocOrignalFileName(Object extraDocOrignalFileName) {
        this.extraDocOrignalFileName = extraDocOrignalFileName;
    }

}
