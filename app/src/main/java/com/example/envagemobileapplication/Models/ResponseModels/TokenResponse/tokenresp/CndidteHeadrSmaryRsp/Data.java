
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteHeadrSmaryRsp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("candidateInfo")
    @Expose
    private CandidateInfo candidateInfo;
    @SerializedName("candidateSocialMedia")
    @Expose
    private List<CandidateSocialMedium> candidateSocialMedia;
    @SerializedName("candidateBadges")
    @Expose
    private List<Object> candidateBadges;
    @SerializedName("candidateSkills")
    @Expose
    private List<Object> candidateSkills;

    public CandidateInfo getCandidateInfo() {
        return candidateInfo;
    }

    public void setCandidateInfo(CandidateInfo candidateInfo) {
        this.candidateInfo = candidateInfo;
    }

    public List<CandidateSocialMedium> getCandidateSocialMedia() {
        return candidateSocialMedia;
    }

    public void setCandidateSocialMedia(List<CandidateSocialMedium> candidateSocialMedia) {
        this.candidateSocialMedia = candidateSocialMedia;
    }

    public List<Object> getCandidateBadges() {
        return candidateBadges;
    }

    public void setCandidateBadges(List<Object> candidateBadges) {
        this.candidateBadges = candidateBadges;
    }

    public List<Object> getCandidateSkills() {
        return candidateSkills;
    }

    public void setCandidateSkills(List<Object> candidateSkills) {
        this.candidateSkills = candidateSkills;
    }

}
