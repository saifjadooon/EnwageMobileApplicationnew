
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummarySkillsRes;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("candidateSkillsId")
    @Expose
    private Integer candidateSkillsId;
    @SerializedName("candidateId")
    @Expose
    private Integer candidateId;
    @SerializedName("skillId")
    @Expose
    private Integer skillId;
    @SerializedName("skillRating")
    @Expose
    private String skillRating;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getCandidateSkillsId() {
        return candidateSkillsId;
    }

    public void setCandidateSkillsId(Integer candidateSkillsId) {
        this.candidateSkillsId = candidateSkillsId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getSkillRating() {
        return skillRating;
    }

    public void setSkillRating(String skillRating) {
        this.skillRating = skillRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
