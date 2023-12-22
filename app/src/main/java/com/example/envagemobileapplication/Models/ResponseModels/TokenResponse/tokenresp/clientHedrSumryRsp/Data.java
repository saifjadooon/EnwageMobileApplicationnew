
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp;

import java.util.List;
 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class Data {

    @SerializedName("clientInfo")
    @Expose
    private ClientInfo clientInfo;
    @SerializedName("clientSocialMedia")
    @Expose
    private List<ClientSocialMedium> clientSocialMedia;
    @SerializedName("companyBadges")
    @Expose
    private List<CompanyBadge> companyBadges;
    @SerializedName("clientsPocs")
    @Expose
    private List<ClientsPoc> clientsPocs;
    @SerializedName("clientNotes")
    @Expose
    private List<ClientNote> clientNotes;
    @SerializedName("onboardingStatus")
    @Expose
    private OnboardingStatus onboardingStatus;
    @SerializedName("ownerNavigation")
    @Expose
    private OwnerNavigation ownerNavigation;
    @SerializedName("userStafferList")
    @Expose
    private List<Object> userStafferList;

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public List<ClientSocialMedium> getClientSocialMedia() {
        return clientSocialMedia;
    }

    public void setClientSocialMedia(List<ClientSocialMedium> clientSocialMedia) {
        this.clientSocialMedia = clientSocialMedia;
    }

    public List<CompanyBadge> getCompanyBadges() {
        return companyBadges;
    }

    public void setCompanyBadges(List<CompanyBadge> companyBadges) {
        this.companyBadges = companyBadges;
    }

    public List<ClientsPoc> getClientsPocs() {
        return clientsPocs;
    }

    public void setClientsPocs(List<ClientsPoc> clientsPocs) {
        this.clientsPocs = clientsPocs;
    }

    public List<ClientNote> getClientNotes() {
        return clientNotes;
    }

    public void setClientNotes(List<ClientNote> clientNotes) {
        this.clientNotes = clientNotes;
    }

    public OnboardingStatus getOnboardingStatus() {
        return onboardingStatus;
    }

    public void setOnboardingStatus(OnboardingStatus onboardingStatus) {
        this.onboardingStatus = onboardingStatus;
    }

    public OwnerNavigation getOwnerNavigation() {
        return ownerNavigation;
    }

    public void setOwnerNavigation(OwnerNavigation ownerNavigation) {
        this.ownerNavigation = ownerNavigation;
    }

    public List<Object> getUserStafferList() {
        return userStafferList;
    }

    public void setUserStafferList(List<Object> userStafferList) {
        this.userStafferList = userStafferList;
    }

}
