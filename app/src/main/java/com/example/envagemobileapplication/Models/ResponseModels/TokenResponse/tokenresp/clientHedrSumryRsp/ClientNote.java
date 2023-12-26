
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class ClientNote {

    @SerializedName("clientNotesId")
    @Expose
    private Integer clientNotesId;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createByUserName")
    @Expose
    private Object createByUserName;
    @SerializedName("createdByProfilePic")
    @Expose
    private String createdByProfilePic;
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
    @SerializedName("isShared")
    @Expose
    private Boolean isShared;
    @SerializedName("byGuest")
    @Expose
    private Boolean byGuest;
    @SerializedName("reason")
    @Expose
    private Object reason;
    @SerializedName("remarks")
    @Expose
    private Object remarks;

    public Integer getClientNotesId() {
        return clientNotesId;
    }

    public void setClientNotesId(Integer clientNotesId) {
        this.clientNotesId = clientNotesId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Object getCreateByUserName() {
        return createByUserName;
    }

    public void setCreateByUserName(Object createByUserName) {
        this.createByUserName = createByUserName;
    }

    public String getCreatedByProfilePic() {
        return createdByProfilePic;
    }

    public void setCreatedByProfilePic(String createdByProfilePic) {
        this.createdByProfilePic = createdByProfilePic;
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

    public Boolean getIsShared() {
        return isShared;
    }

    public void setIsShared(Boolean isShared) {
        this.isShared = isShared;
    }

    public Boolean getByGuest() {
        return byGuest;
    }

    public void setByGuest(Boolean byGuest) {
        this.byGuest = byGuest;
    }

    public Object getReason() {
        return reason;
    }

    public void setReason(Object reason) {
        this.reason = reason;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

}
