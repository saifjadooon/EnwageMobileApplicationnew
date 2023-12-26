
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GnrateOFerLeterRsp;

import java.util.List;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Datum {

    @SerializedName("offerLetterLinkId")
    @Expose
    private Integer offerLetterLinkId;
    @SerializedName("validTill")
    @Expose
    private String validTill;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("modifiedBy")
    @Expose
    private Object modifiedBy;
    @SerializedName("modifiedDate")
    @Expose
    private Object modifiedDate;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("clientLogo")
    @Expose
    private Boolean clientLogo;
    @SerializedName("clientName")
    @Expose
    private Boolean clientName;
    @SerializedName("clientAddress")
    @Expose
    private Boolean clientAddress;
    @SerializedName("clientWebsite")
    @Expose
    private Boolean clientWebsite;
    @SerializedName("clientFacebook")
    @Expose
    private Boolean clientFacebook;
    @SerializedName("clientInstagram")
    @Expose
    private Boolean clientInstagram;
    @SerializedName("clientLinkedin")
    @Expose
    private Boolean clientLinkedin;
    @SerializedName("clientTwitter")
    @Expose
    private Boolean clientTwitter;
    @SerializedName("poweredBy")
    @Expose
    private Boolean poweredBy;
    @SerializedName("clientPoc")
    @Expose
    private Boolean clientPoc;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("externalId")
    @Expose
    private Object externalId;
    @SerializedName("client")
    @Expose
    private Object client;
    @SerializedName("candidateJobs")
    @Expose
    private List<Object> candidateJobs;

    public Integer getOfferLetterLinkId() {
        return offerLetterLinkId;
    }

    public void setOfferLetterLinkId(Integer offerLetterLinkId) {
        this.offerLetterLinkId = offerLetterLinkId;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Boolean getClientLogo() {
        return clientLogo;
    }

    public void setClientLogo(Boolean clientLogo) {
        this.clientLogo = clientLogo;
    }

    public Boolean getClientName() {
        return clientName;
    }

    public void setClientName(Boolean clientName) {
        this.clientName = clientName;
    }

    public Boolean getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(Boolean clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Boolean getClientWebsite() {
        return clientWebsite;
    }

    public void setClientWebsite(Boolean clientWebsite) {
        this.clientWebsite = clientWebsite;
    }

    public Boolean getClientFacebook() {
        return clientFacebook;
    }

    public void setClientFacebook(Boolean clientFacebook) {
        this.clientFacebook = clientFacebook;
    }

    public Boolean getClientInstagram() {
        return clientInstagram;
    }

    public void setClientInstagram(Boolean clientInstagram) {
        this.clientInstagram = clientInstagram;
    }

    public Boolean getClientLinkedin() {
        return clientLinkedin;
    }

    public void setClientLinkedin(Boolean clientLinkedin) {
        this.clientLinkedin = clientLinkedin;
    }

    public Boolean getClientTwitter() {
        return clientTwitter;
    }

    public void setClientTwitter(Boolean clientTwitter) {
        this.clientTwitter = clientTwitter;
    }

    public Boolean getPoweredBy() {
        return poweredBy;
    }

    public void setPoweredBy(Boolean poweredBy) {
        this.poweredBy = poweredBy;
    }

    public Boolean getClientPoc() {
        return clientPoc;
    }

    public void setClientPoc(Boolean clientPoc) {
        this.clientPoc = clientPoc;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Object getExternalId() {
        return externalId;
    }

    public void setExternalId(Object externalId) {
        this.externalId = externalId;
    }

    public Object getClient() {
        return client;
    }

    public void setClient(Object client) {
        this.client = client;
    }

    public List<Object> getCandidateJobs() {
        return candidateJobs;
    }

    public void setCandidateJobs(List<Object> candidateJobs) {
        this.candidateJobs = candidateJobs;
    }

}
