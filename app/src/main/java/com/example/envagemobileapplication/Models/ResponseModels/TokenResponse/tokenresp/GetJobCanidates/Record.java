
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobCanidates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Record {

    @SerializedName("candidateId")
    @Expose
    private Integer candidateId;
    @SerializedName("jobId")
    @Expose
    private Object jobId;
    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;
    @SerializedName("position")
    @Expose
    private Object position;
    @SerializedName("ssnNumber")
    @Expose
    private Object ssnNumber;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private Object middleName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("gender")
    @Expose
    private String gender;
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
    @SerializedName("addressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("addressLine2")
    @Expose
    private String addressLine2;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("cellNumber1")
    @Expose
    private Object cellNumber1;
    @SerializedName("cellNumber2")
    @Expose
    private Object cellNumber2;
    @SerializedName("primaryEmail")
    @Expose
    private String primaryEmail;
    @SerializedName("secondaryEmail")
    @Expose
    private Object secondaryEmail;
    @SerializedName("emergencyContactPerson")
    @Expose
    private Object emergencyContactPerson;
    @SerializedName("emergencyContactRelationship")
    @Expose
    private Object emergencyContactRelationship;
    @SerializedName("emergencyPhoneNumber")
    @Expose
    private Object emergencyPhoneNumber;
    @SerializedName("isRightToWork")
    @Expose
    private Boolean isRightToWork;
    @SerializedName("alienRegistrationNumber")
    @Expose
    private String alienRegistrationNumber;
    @SerializedName("alienRegistrationExpiryDate")
    @Expose
    private String alienRegistrationExpiryDate;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("yearsOfExperience")
    @Expose
    private String yearsOfExperience;
    @SerializedName("graduationYear")
    @Expose
    private String graduationYear;
    @SerializedName("currentSalary")
    @Expose
    private String currentSalary;
    @SerializedName("noticePeriod")
    @Expose
    private Object noticePeriod;
    @SerializedName("expectedSalaries")
    @Expose
    private String expectedSalaries;
    @SerializedName("nationaliy")
    @Expose
    private Object nationaliy;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("minWageRate")
    @Expose
    private Object minWageRate;
    @SerializedName("candidateRefferedBy")
    @Expose
    private Object candidateRefferedBy;
    @SerializedName("isDisability")
    @Expose
    private Object isDisability;
    @SerializedName("ethnicity")
    @Expose
    private String ethnicity;
    @SerializedName("preferredLanguages")
    @Expose
    private String preferredLanguages;
    @SerializedName("isVeteran")
    @Expose
    private Object isVeteran;
    @SerializedName("hireType")
    @Expose
    private Object hireType;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createdByUserName")
    @Expose
    private Object createdByUserName;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("modifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("applicantId")
    @Expose
    private Integer applicantId;
    @SerializedName("shift")
    @Expose
    private String shift;
    @SerializedName("legallyWorkable")
    @Expose
    private Object legallyWorkable;
    @SerializedName("transportAvailability")
    @Expose
    private Boolean transportAvailability;
    @SerializedName("candidateNotes")
    @Expose
    private List<Object> candidateNotes;
    @SerializedName("candidateJobs")
    @Expose
    private List<CandidateJob> candidateJobs;
    @SerializedName("referenceCheckRequests")
    @Expose
    private List<Object> referenceCheckRequests;

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Object getJobId() {
        return jobId;
    }

    public void setJobId(Object jobId) {
        this.jobId = jobId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public Object getSsnNumber() {
        return ssnNumber;
    }

    public void setSsnNumber(Object ssnNumber) {
        this.ssnNumber = ssnNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getMiddleName() {
        return middleName;
    }

    public void setMiddleName(Object middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getCellNumber1() {
        return cellNumber1;
    }

    public void setCellNumber1(Object cellNumber1) {
        this.cellNumber1 = cellNumber1;
    }

    public Object getCellNumber2() {
        return cellNumber2;
    }

    public void setCellNumber2(Object cellNumber2) {
        this.cellNumber2 = cellNumber2;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public Object getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(Object secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public Object getEmergencyContactPerson() {
        return emergencyContactPerson;
    }

    public void setEmergencyContactPerson(Object emergencyContactPerson) {
        this.emergencyContactPerson = emergencyContactPerson;
    }

    public Object getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(Object emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    public Object getEmergencyPhoneNumber() {
        return emergencyPhoneNumber;
    }

    public void setEmergencyPhoneNumber(Object emergencyPhoneNumber) {
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }

    public Boolean getIsRightToWork() {
        return isRightToWork;
    }

    public void setIsRightToWork(Boolean isRightToWork) {
        this.isRightToWork = isRightToWork;
    }

    public String getAlienRegistrationNumber() {
        return alienRegistrationNumber;
    }

    public void setAlienRegistrationNumber(String alienRegistrationNumber) {
        this.alienRegistrationNumber = alienRegistrationNumber;
    }

    public String getAlienRegistrationExpiryDate() {
        return alienRegistrationExpiryDate;
    }

    public void setAlienRegistrationExpiryDate(String alienRegistrationExpiryDate) {
        this.alienRegistrationExpiryDate = alienRegistrationExpiryDate;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(String currentSalary) {
        this.currentSalary = currentSalary;
    }

    public Object getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(Object noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public String getExpectedSalaries() {
        return expectedSalaries;
    }

    public void setExpectedSalaries(String expectedSalaries) {
        this.expectedSalaries = expectedSalaries;
    }

    public Object getNationaliy() {
        return nationaliy;
    }

    public void setNationaliy(Object nationaliy) {
        this.nationaliy = nationaliy;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getMinWageRate() {
        return minWageRate;
    }

    public void setMinWageRate(Object minWageRate) {
        this.minWageRate = minWageRate;
    }

    public Object getCandidateRefferedBy() {
        return candidateRefferedBy;
    }

    public void setCandidateRefferedBy(Object candidateRefferedBy) {
        this.candidateRefferedBy = candidateRefferedBy;
    }

    public Object getIsDisability() {
        return isDisability;
    }

    public void setIsDisability(Object isDisability) {
        this.isDisability = isDisability;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getPreferredLanguages() {
        return preferredLanguages;
    }

    public void setPreferredLanguages(String preferredLanguages) {
        this.preferredLanguages = preferredLanguages;
    }

    public Object getIsVeteran() {
        return isVeteran;
    }

    public void setIsVeteran(Object isVeteran) {
        this.isVeteran = isVeteran;
    }

    public Object getHireType() {
        return hireType;
    }

    public void setHireType(Object hireType) {
        this.hireType = hireType;
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

    public Object getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(Object createdByUserName) {
        this.createdByUserName = createdByUserName;
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

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Object getLegallyWorkable() {
        return legallyWorkable;
    }

    public void setLegallyWorkable(Object legallyWorkable) {
        this.legallyWorkable = legallyWorkable;
    }

    public Boolean getTransportAvailability() {
        return transportAvailability;
    }

    public void setTransportAvailability(Boolean transportAvailability) {
        this.transportAvailability = transportAvailability;
    }

    public List<Object> getCandidateNotes() {
        return candidateNotes;
    }

    public void setCandidateNotes(List<Object> candidateNotes) {
        this.candidateNotes = candidateNotes;
    }

    public List<CandidateJob> getCandidateJobs() {
        return candidateJobs;
    }

    public void setCandidateJobs(List<CandidateJob> candidateJobs) {
        this.candidateJobs = candidateJobs;
    }

    public List<Object> getReferenceCheckRequests() {
        return referenceCheckRequests;
    }

    public void setReferenceCheckRequests(List<Object> referenceCheckRequests) {
        this.referenceCheckRequests = referenceCheckRequests;
    }

}
