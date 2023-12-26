
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Data {

    @SerializedName("jobInfo")
    @Expose
    private JobInfo jobInfo;
    @SerializedName("client")
    @Expose
    private Client client;
    @SerializedName("jobBadges")
    @Expose
    private List<Object> jobBadges;
    @SerializedName("jobStatus")
    @Expose
    private JobStatus jobStatus;
    @SerializedName("industry")
    @Expose
    private Object industry;
    @SerializedName("jobSkillsSummary")
    @Expose
    private List<JobSkillsSummary> jobSkillsSummary;
    @SerializedName("billingDetails")
    @Expose
    private BillingDetails billingDetails;
    @SerializedName("payGroup")
    @Expose
    private PayGroup payGroup;

    public JobInfo getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(JobInfo jobInfo) {
        this.jobInfo = jobInfo;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Object> getJobBadges() {
        return jobBadges;
    }

    public void setJobBadges(List<Object> jobBadges) {
        this.jobBadges = jobBadges;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Object getIndustry() {
        return industry;
    }

    public void setIndustry(Object industry) {
        this.industry = industry;
    }

    public List<JobSkillsSummary> getJobSkillsSummary() {
        return jobSkillsSummary;
    }

    public void setJobSkillsSummary(List<JobSkillsSummary> jobSkillsSummary) {
        this.jobSkillsSummary = jobSkillsSummary;
    }

    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

    public PayGroup getPayGroup() {
        return payGroup;
    }

    public void setPayGroup(PayGroup payGroup) {
        this.payGroup = payGroup;
    }

}
