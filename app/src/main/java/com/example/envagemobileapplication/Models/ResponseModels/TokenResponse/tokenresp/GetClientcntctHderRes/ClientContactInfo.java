
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientcntctHderRes;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class ClientContactInfo {

    @SerializedName("clientPocId")
    @Expose
    private Integer clientPocId;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("primaryProfileImage")
    @Expose
    private String primaryProfileImage;
    @SerializedName("primaryGender")
    @Expose
    private String primaryGender;
    @SerializedName("primaryFirstName")
    @Expose
    private String primaryFirstName;
    @SerializedName("primaryLastName")
    @Expose
    private String primaryLastName;
    @SerializedName("primaryEmail")
    @Expose
    private String primaryEmail;
    @SerializedName("alternateEmail")
    @Expose
    private String alternateEmail;
    @SerializedName("primaryStatus")
    @Expose
    private Object primaryStatus;
    @SerializedName("primaryLinkedin")
    @Expose
    private String primaryLinkedin;
    @SerializedName("primaryAddress1")
    @Expose
    private String primaryAddress1;
    @SerializedName("primaryAddress2")
    @Expose
    private String primaryAddress2;
    @SerializedName("primaryCountry")
    @Expose
    private String primaryCountry;
    @SerializedName("primaryCity")
    @Expose
    private String primaryCity;
    @SerializedName("primaryZipcode")
    @Expose
    private String primaryZipcode;
    @SerializedName("primaryPhoneNumber")
    @Expose
    private String primaryPhoneNumber;
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
    @SerializedName("primaryState")
    @Expose
    private String primaryState;
    @SerializedName("primaryLocation")
    @Expose
    private String primaryLocation;
    @SerializedName("isPrimaryContact")
    @Expose
    private Boolean isPrimaryContact;
    @SerializedName("contactDepartment")
    @Expose
    private String contactDepartment;
    @SerializedName("contactDescription")
    @Expose
    private Object contactDescription;

    public Integer getClientPocId() {
        return clientPocId;
    }

    public void setClientPocId(Integer clientPocId) {
        this.clientPocId = clientPocId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getPrimaryProfileImage() {
        return primaryProfileImage;
    }

    public void setPrimaryProfileImage(String primaryProfileImage) {
        this.primaryProfileImage = primaryProfileImage;
    }

    public String getPrimaryGender() {
        return primaryGender;
    }

    public void setPrimaryGender(String primaryGender) {
        this.primaryGender = primaryGender;
    }

    public String getPrimaryFirstName() {
        return primaryFirstName;
    }

    public void setPrimaryFirstName(String primaryFirstName) {
        this.primaryFirstName = primaryFirstName;
    }

    public String getPrimaryLastName() {
        return primaryLastName;
    }

    public void setPrimaryLastName(String primaryLastName) {
        this.primaryLastName = primaryLastName;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public Object getPrimaryStatus() {
        return primaryStatus;
    }

    public void setPrimaryStatus(Object primaryStatus) {
        this.primaryStatus = primaryStatus;
    }

    public String getPrimaryLinkedin() {
        return primaryLinkedin;
    }

    public void setPrimaryLinkedin(String primaryLinkedin) {
        this.primaryLinkedin = primaryLinkedin;
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

    public String getPrimaryCountry() {
        return primaryCountry;
    }

    public void setPrimaryCountry(String primaryCountry) {
        this.primaryCountry = primaryCountry;
    }

    public String getPrimaryCity() {
        return primaryCity;
    }

    public void setPrimaryCity(String primaryCity) {
        this.primaryCity = primaryCity;
    }

    public String getPrimaryZipcode() {
        return primaryZipcode;
    }

    public void setPrimaryZipcode(String primaryZipcode) {
        this.primaryZipcode = primaryZipcode;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
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

    public String getPrimaryState() {
        return primaryState;
    }

    public void setPrimaryState(String primaryState) {
        this.primaryState = primaryState;
    }

    public String getPrimaryLocation() {
        return primaryLocation;
    }

    public void setPrimaryLocation(String primaryLocation) {
        this.primaryLocation = primaryLocation;
    }

    public Boolean getIsPrimaryContact() {
        return isPrimaryContact;
    }

    public void setIsPrimaryContact(Boolean isPrimaryContact) {
        this.isPrimaryContact = isPrimaryContact;
    }

    public String getContactDepartment() {
        return contactDepartment;
    }

    public void setContactDepartment(String contactDepartment) {
        this.contactDepartment = contactDepartment;
    }

    public Object getContactDescription() {
        return contactDescription;
    }

    public void setContactDescription(Object contactDescription) {
        this.contactDescription = contactDescription;
    }

}
