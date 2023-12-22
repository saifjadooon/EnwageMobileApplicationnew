
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobSkillsResponse;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Datum {

    @SerializedName("skillId")
    @Expose
    private Integer skillId;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
