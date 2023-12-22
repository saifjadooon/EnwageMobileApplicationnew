package com.ezshifa.aihealthcare.network

import com.example.envagemobileapplication.Models.RequestModels.*
import com.example.envagemobileapplication.Models.RequestModels.AddJobRequestModels.GetCustomJobTemplateReqModel
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddBulkOwnerRsp.AddBulkOwnerResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddClientDescRsp.AddClientDescriptionResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddClientResponse.AddClientResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddJobResponse.AddJobResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteHeadrSmaryRsp.CandidateHeaderSummaryResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteJobRsp.CandidateJobGetJobResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateJobStatusRes.CandidateJobStatusRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSmryEtiondRes.CandidateSummaryEducationRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CndidteSumryExpRes.CandidateSummaryExperienceRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummaryJobsRes.CandidateJobsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CandidateSummarySkillsRes.CandidateSummarySkillsRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CompanyOnboardingRes.GetCompanyOnboardingStatusResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ConvertCandidateResponse.ConvertCandidateResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.CustomTemplateResponse.CustomTemplateResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GnrateOFerLeterRsp.GenerateOFferLetterResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSkillsResponse.GetAllSkillsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandiStatuskeyPipeline.GetCandiStatuskeyPipeline
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCandidateResponse.GetCandidatesResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientcntctHderRes.GetClientContactHeaderResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientContactsResponse.GetClientContactsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsAddJobList.GetClientsAddJobList
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientsResponse.GetClients
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetCountrylistResponse.GetCountrylistResponse
 import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetIndustryListResponse.GetIndustryListResponse
 import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobByJobIdResponse.GetJobByJobIdResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobCanidates.GetJobCandidates
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetEmployeesRes.GetEmployeesRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobHeaderSummary.GetJobHeaderSummary
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsResponse.GetJobsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobsStatusesResponse.GetJobsStatusesResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GtLogdinUserDetailsRsp.GetLoggedinUserDetails
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetOfferLetterTemplates.GetOfferLetterTemplates
 import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRights.GetRigths
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetWcResponse.WcCodeResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.JobSkillsResponse.JobSkillsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.OnlineApplicantsResponse.OnlineApplicantsResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.PayGroupResponse.PayGroupResponse
 import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateCJobStatusRes.UpdateCJobStatusRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateCandidateResponse.UpdateCandidateResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateJobsStatusResponse.UpdateJobsStatusResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateProfileResultResponse.UpdateProfileResultResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRecentJobsRes.GetRecentJobsRes
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.UpdateStatusResponse.UpdateStatusResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.ZipCodeResponse.ZipCodeResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.clientHedrSumryRsp.ClientHeaderSummaryResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getEmailTemplateResponse.GetEmailTemplateResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getJobStatusResponse.GetJobStatusResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getdbUsersResponse.GetdbUsersResponse
import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getjobbyid.Getjobbyid
import com.example.envagemobileapplication.Models.SocialMediaResponse.SocialMediaResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


public interface ApiInterface {


    @GET("api/v1/User/get-rights")
    fun getRights(
        @Header("x-access-token") token: String
    ): Call<GetRigths>


    /* @POST("")
     @FormUrlEncoded
     fun getClients(
         @Header("x-access-token") authorization: String,
         @Field(sort) sortDirection: sortDirection
     ): Call<GetClients>*/


    @POST("api/v1/Client/clients")
    fun getClients(
        @Body requestPayload: sortDirection,
        @Header("x-access-token") authorization: String
    ): Call<GetClients>


    @POST("api/v1/Job/jobs")
    fun getJobs(
        @Body requestPayload: SortDirectionJobs,
        @Header("x-access-token") authorization: String
    ): Call<GetJobsResponse>

    @POST("api/v1/Candidate/candidates")
    fun getCandidates(
        @Body requestPayload: SortDirectionCandidates,
        @Header("x-access-token") authorization: String
    ): Call<GetCandidatesResponse>


    @POST("api/v1/ClientContact/client-contacts")
    fun getClientContacts(
        @Body requestPayload: SortDirectionClientContacts,
        @Header("x-access-token") authorization: String
    ): Call<GetClientContactsResponse>


    @GET("api/v1/CompanyOnBoardingStatus/get-company-onboarding-status-list")
    fun GetCompanyOnboardingStatusResponse(
        @Header("x-access-token") authorization: String
    ): Call<GetCompanyOnboardingStatusResponse>


    @PATCH("api/v1/Client/patch-client-onboarding-status/{clientId}")
    fun updateStatus(
        @Header("x-access-token") authorization: String,
        @Body payload: List<UpdateStatusPayload>,
        @Path("clientId") clientId: Int
    ): Call<UpdateStatusResponse>

    @PATCH("api/v1/Job/patch-job/{clientId}")
    fun updateJobStatus(
        @Header("x-access-token") authorization: String,
        @Body payload: List<UpdateStatusPayload>,
        @Path("clientId") clientId: Int
    ): Call<UpdateJobsStatusResponse>

    @GET("api/v1/User/get-loggedin-user")
    fun GetLoggedInUserDetails(
        @Header("x-access-token") authorization: String
    ): Call<GetLoggedinUserDetails>

    @Multipart
    @POST("/api/v1/Client/add-client")
    fun AddClientData(
        @Header("x-access-token") authorization: String,
        @Part name: MultipartBody.Part,
        @Part website: MultipartBody.Part,
        @Part profilepic: MultipartBody.Part,
        @Part part: MultipartBody.Part
    ): Call<AddClientResponse>


    @Multipart
    @POST("api/v1/Client/update-client-profile-image")
    fun UpdateClientProfilepic(
        @Header("x-access-token") authorization: String,
        @Part("clientID") clientID: Int,
        @Part image: MultipartBody.Part
    ): Call<UpdateProfileResultResponse>


    @GET("/api/v1/ClientProfile/get-client-header-summary/{clientId}")
    fun GetClientHeaderSummary(
        @Header("x-access-token") authorization: String,
        @Path("clientId") clientId: Int
    ): Call<ClientHeaderSummaryResponse>


    @GET("api/v1/ClientContact/get-client-contact-summary/{clientId}")
    fun GetContactHeaderSummary(
        @Header("x-access-token") authorization: String,
        @Path("clientId") clientId: Int
    ): Call<GetClientContactHeaderResponse>

    @GET("api/v1/ClientApprovedWcCode/get-all/{clientId}")
    fun GetApprovedWcCodes(
        @Header("x-access-token") authorization: String,
        @Path("clientId") clientId: Int
    ): Call<WcCodeResponse>

    @GET("api/v1/JobStatus/get-job-status")
    fun GetJobStatuses(
        @Header("x-access-token") authorization: String
    ): Call<GetJobsStatusesResponse>


    @GET("api/v1/Common/get-country-list")
    fun GetCountryList(
        @Header("x-access-token") authorization: String
    ): Call<GetCountrylistResponse>

    @PATCH("api/v1/ClientProfile/update-client/{clientId}")
    fun UpdateClient(

        @Header("x-access-token") authorization: String,
        @Body payload: List<UpdateStatusPayload>,
        @Path("clientId") clientId: Int
    ): Call<UpdateJobsStatusResponse>


    @PATCH("api/v1/ClientProfile/update-client-social-media/{clientSocialMediaId}")
    fun UpdateClientSocialMedia(

        @Header("x-access-token") authorization: String,
        @Body payload: List<UpdateStatusPayload>,
        @Path("clientSocialMediaId") socialmediaid: Int
    ): Call<SocialMediaResponse>

    @PATCH("api/v1/ClientProfile/update-client-social-media/{clientSocialMediaId}")
    fun UpdateClientSocialMediaCheckbox(

        @Header("x-access-token") authorization: String,
        @Body payload: List<UpdateStatusPayloadforSocialMediacheckbox>,
        @Path("clientSocialMediaId") socialmediaid: Int
    ): Call<SocialMediaResponse>

    @POST("api/v1/User/get-db-users-admin")
    fun getdbusers(
        @Body requestPayload: SearchRequest,
        @Header("x-access-token") authorization: String
    ): Call<GetdbUsersResponse>

    @POST("api/v1/ClientProfile/add-bulk-owner")
    fun addBulkOwners(
        @Body requestPayload: ClientDataRequestModel,
        @Header("x-access-token") authorization: String
    ): Call<AddBulkOwnerResponse>

    @Multipart
    @POST("api/v1/ClientProfile/add-client-description")
    fun addClientDescription(
        @Header("x-access-token") authorization: String,
        @Part part: MultipartBody.Part,
        @Part("clientID") clientID: Int
    ): Call<AddClientDescriptionResponse>

    @GET("api/v1/Common/get-zip-city-state-list/{zipCode}")
    fun getzipcityStatelist(
        @Header("x-access-token") authorization: String,
        @Path("zipCode") zipCode: String
    ): Call<ZipCodeResponse>


    @POST("api/v1/JobCustomTemplate/get-all-job-custom-templates-list-by-clientId")
    fun getcustomTemplate(
        @Header("Authorization") authorization: String,
        @Body requestPayload: getCustomTemplateRequestModel
    ): Call<CustomTemplateResponse>


    @POST("api/v1/ClientPayrollPayGroup/search-pay-group-by-name")
    fun searchPaygroup(
        @Header("Authorization") authorization: String,
        @Body requestPayload: PaygroupRequestModel
    ): Call<PayGroupResponse>


    @GET("api/v1/Common/get-industry-list-by-name/a")
    fun getIndustry(
        @Header("Authorization") authorization: String
    ): Call<GetIndustryListResponse>


    //    @GET("api/v1/Job/get-Client-list-by-name/a")
    @GET("api/v1/Job/get-Client-list-by-name/{searchName}")
    fun getClientNames(
        @Header("Authorization") authorization: String,
        @Path("searchName") searchname: String
    ): Call<GetClientsAddJobList>


    @GET("api/v1/JobStatus/get-job-status-by-name/{searchName}")
    fun getJobStatus(
        @Header("Authorization") authorization: String,
        @Path("searchName") searchname: String
    ): Call<GetJobStatusResponse>


    @GET("api/v1/JobSkills/get-job-skills/{searchName}")
    fun getJobSkills(
        @Header("Authorization") authorization: String,
        @Path("searchName") searchname: String
    ): Call<JobSkillsResponse>


    @Multipart
    @POST("api/v1/Job/add-job")
    fun AddJob(
        @Header("x-access-token") authorization: String,
        @Part jobDescription: MultipartBody.Part,
        @Part positionName: MultipartBody.Part,
        @Part clientId: MultipartBody.Part,
        @Part payrollPayGroupId: MultipartBody.Part,
        @Part jobId: MultipartBody.Part,
        @Part industryId: MultipartBody.Part,
        @Part jobNature: MultipartBody.Part,
        @Part address1: MultipartBody.Part,
        @Part address2: MultipartBody.Part,
        @Part country: MultipartBody.Part,
        @Part zipcode: MultipartBody.Part,
        @Part city: MultipartBody.Part,
        @Part state: MultipartBody.Part,
        @Part location: MultipartBody.Part,
        @Part headcount: MultipartBody.Part,
        @Part jobType: MultipartBody.Part,
        @Part startDate: MultipartBody.Part,
        @Part endDate: MultipartBody.Part,
        @Part currency: MultipartBody.Part,
        @Part minimumSalary: MultipartBody.Part,
        @Part maximumSalary: MultipartBody.Part,
        @Part workingDaysNo: MultipartBody.Part,
        @Part estimatedHours: MultipartBody.Part,
        @Part workingDays: MultipartBody.Part,
        @Part jobStatusId: MultipartBody.Part,
        @Part jobSkills: MultipartBody.Part,
        @Part experienceRequired: MultipartBody.Part,
        @Part useTemplate: MultipartBody.Part,
        @Part jobTemplateId: MultipartBody.Part,
        @Part markup: MultipartBody.Part,
        @Part minPayRate: MultipartBody.Part,
        @Part minBillRate: MultipartBody.Part,
        @Part maxPayRate: MultipartBody.Part,
        @Part maxBillRate: MultipartBody.Part,
        @Part targetPayRate: MultipartBody.Part,
        @Part targetBillRate: MultipartBody.Part,
        @Part overTimeMultiplier: MultipartBody.Part,
        @Part overTimeType: MultipartBody.Part,
        @Part overTimeMarkup: MultipartBody.Part,
        @Part overTimePayRate: MultipartBody.Part,
        @Part overTimeBillRate: MultipartBody.Part,
        @Part doubleTimeMultiplier: MultipartBody.Part,
        @Part doubleTimeType: MultipartBody.Part,
        @Part doubleTimeMarkup: MultipartBody.Part,
        @Part doubleTimePayRate: MultipartBody.Part,
        @Part doubleTimeBillRate: MultipartBody.Part,
        @Part frequency: MultipartBody.Part,
        @Part applicationFormId: MultipartBody.Part,
        @Part showSalary: MultipartBody.Part,
        @Part showNature: MultipartBody.Part,
        @Part showClient: MultipartBody.Part,
        @Part showIndustry: MultipartBody.Part,
        @Part showAddress: MultipartBody.Part,
        @Part showType: MultipartBody.Part,
        @Part showSkills: MultipartBody.Part,
        @Part showShift: MultipartBody.Part,
        @Part isPublish: MultipartBody.Part
    ): Call<AddJobResponse>


    @POST("api/v1/Applicant/get-all")
    fun getOnlineApplicants(
        @Body requestPayload: SortDirectionGetOnlineCandidate,
        @Header("x-access-token") authorization: String
    ): Call<OnlineApplicantsResponse>


    @POST("api/v1/Applicant/convert-candidate/{applicantId}")
    fun convertCandidate(
        @Path("applicantId") searchname: Int,
        @Header("x-access-token") authorization: String
    ): Call<ConvertCandidateResponse>

    @POST("api/v1/Applicant/convert-candidate-bank/{applicantId}")
    fun converttoCandidateBank(
        @Path("applicantId") searchname: Int,
        @Header("x-access-token") authorization: String
    ): Call<ConvertCandidateResponse>

    @DELETE("api/v1/Applicant/delete/{applicantId}")
    fun deleteOnlineCandidate(
        @Path("applicantId") searchname: Int,
        @Header("x-access-token") authorization: String
    ): Call<ConvertCandidateResponse>

    @POST("api/v1/Applicant/get-all-skills/{applicantId}")
    fun getAllSkills(
        @Path("applicantId") searchname: Int,
        @Header("x-access-token") authorization: String
    ): Call<GetAllSkillsResponse>

    @GET("api/v1/Job/get-job-header-summary/{jobGUID}")
    fun GetJobHeaderSummary(
        @Header("x-access-token") authorization: String,
        @Path("jobGUID") jobguid: String
    ): Call<GetJobHeaderSummary>


    @POST("api/v1/Job/job-candidates")
    fun getjobcandidatess(
        @Body requestPayload: SortDirectionCandidateCandidate,
        @Header("x-access-token") authorization: String
    ): Call<GetJobCandidates>


    @GET("api/v1/CandidateJob/get-jobs/{candidateGUID}")
    fun GetCandidteJobBy(
        @Header("x-access-token") authorization: String,
        @Path("candidateGUID") jobguid: String
    ): Call<CandidateJobGetJobResponse>

    @GET("api/v1/Job/get-job-by-jobId/{jobId}")
    fun GetJobbyJobId(
        @Header("x-access-token") authorization: String,
        @Path("jobId") jobguid: String
    ): Call<GetJobByJobIdResponse>


    @POST("api/v1/Candidate/update-candidate-status")
    fun UpdateCandidateStatus(
        @Body requestPayload: UpdateCandidateStatusRequestModel,
        @Header("x-access-token") authorization: String
    ): Call<UpdateCandidateResponse>


    @POST("api/v1/ApplicationForms/get-all-offer-letter-custom-templates")
    fun getofferLetterTemplates(
        @Header("Authorization") authorization: String,
        @Body requestPayload: GetCustomJobTemplateReqModel
    ): Call<GetOfferLetterTemplates>

    @GET("api/v1/ApplicationForms/get-all-email-templates")
    fun getEmailTemplates(
        @Header("Authorization") authorization: String,
    ): Call<GetEmailTemplateResponse>


    @Multipart
    @POST("api/v1/Candidate/genarate-offer-letter-link")
    fun GenerateOFferLetterLink(
        @Header("x-access-token") authorization: String,
        @Part candidateId: MultipartBody.Part,
        @Part jobid: MultipartBody.Part,
        @Part templateid: MultipartBody.Part,
        @Part clientlogo: MultipartBody.Part,
        @Part clientname: MultipartBody.Part,
        @Part clientadress: MultipartBody.Part,
        @Part clientwebsite: MultipartBody.Part,
        @Part clientFacebook: MultipartBody.Part,
        @Part clientinstgram: MultipartBody.Part,
        @Part clientlinkedin: MultipartBody.Part,
        @Part clientTwitter: MultipartBody.Part,
        @Part poweredby: MultipartBody.Part,
        @Part clientpoc: MultipartBody.Part,
        @Part offerletterlink: MultipartBody.Part,
        @Part validtill: MultipartBody.Part
    ): Call<GenerateOFferLetterResponse>

    @GET("api/v1/Job/get-job-by-id/{jobGUID}")
    fun GetJobbyId(
        @Header("x-access-token") authorization: String,
        @Path("jobGUID") jobguid: String
    ): Call<Getjobbyid>


    @Multipart
    @POST("api/v1/Candidate/candidates-offer-letter-and-email")
    fun SendEmailOfferLetter(
        @Header("x-access-token") authorization: String,
        @Part body: MultipartBody.Part,
        @Part html: MultipartBody.Part,
        @Part from: MultipartBody.Part,
        @Part to: MultipartBody.Part,
        @Part cc: MultipartBody.Part,
        @Part bcc: MultipartBody.Part,
        @Part subject: MultipartBody.Part,
        @Part candidateId: MultipartBody.Part,
        @Part jobId: MultipartBody.Part,
        @Part TemplateId: MultipartBody.Part,
        @Part OfferLetterLinkId: MultipartBody.Part,
        @Part OfferLetterLink: MultipartBody.Part,
        @Part validTill: MultipartBody.Part,
        @Part guid: MultipartBody.Part,
        @Part ClientLogo: MultipartBody.Part,
        @Part ClientName: MultipartBody.Part,
        @Part ClientAddress: MultipartBody.Part,
        @Part ClientWebsite: MultipartBody.Part,
        @Part ClientFacebook: MultipartBody.Part,
        @Part ClientInstagram: MultipartBody.Part,
        @Part ClientLinkedin: MultipartBody.Part,
        @Part ClientTwitter: MultipartBody.Part,
        @Part PoweredBy: MultipartBody.Part,
        @Part ClientPoc: MultipartBody.Part

    ): Call<AddJobResponse>

    @GET("/api/v1/CandidateJob/get-jobs/{candidateGUID}")
    fun GetCandidateSummaryJobs(
        @Header("x-access-token") authorization: String,
        @Path("candidateGUID") candidateId: String
    ): Call<CandidateJobsResponse>

    @GET("/api/v1/CandidateEducation/get-all/{candidateGUID}")
    fun GetCandidateSummaryEducation(
        @Header("x-access-token") authorization: String,
        @Path("candidateGUID") candidateId: String
    ): Call<CandidateSummaryEducationRes>

    @GET("/api/v1/CandidateExperience/get-all/{candidateGUID}")
    fun GetCandidateSummaryExperience(
        @Header("x-access-token") authorization: String,
        @Path("candidateGUID") candidateId: String
    ): Call<CandidateSummaryExperienceRes>

    @GET("/api/v1/CandidateSkill/get-candidate-skills/{candidateGUID}")
    fun GetCandidateSummarySkills(
        @Header("x-access-token") authorization: String,
        @Path("candidateGUID") candidateId: String
    ): Call<CandidateSummarySkillsRes>

    @GET("/api/v1/Candidate/summary/{candidateGUID}")
    fun GetCandidateHeaderSummary(
        @Header("x-access-token") authorization: String,
        @Path("candidateGUID") candidateId: String
    ): Call<CandidateHeaderSummaryResponse>

    @POST("api/v1/Employee/employees")
    fun getEmployees(
        @Body requestPayload: SortDirectionEmployees,
        @Header("x-access-token") authorization: String
    ): Call<GetEmployeesRes>

    @POST("/api/v1/Candidate/change-candidate-status-against-job")
    fun updateCandidateJobStatus(
        @Header("x-access-token") authorization: String,
        @Body payload: UpdateJobStatusPayload,
    ): Call<UpdateCJobStatusRes>

    @GET("api/v1/Job/get-job-by-jobId/{jobId}")
    fun GetJobDetailsById(
        @Header("x-access-token") authorization: String,
        @Path("jobId") jobId: Int
    ): Call<GetJobByJobIdResponse>

    @GET("/api/v1/CandidateStatus/get-candidate-status")
    fun GetCandidateStatuses(
        @Header("x-access-token") authorization: String,
    ): Call<CandidateJobStatusRes>

    @GET("/api/v1/CandidateStatus/get-candidate-status-key-pipeline")
    fun GetCandidateStatusKeyPipeline(
        @Header("x-access-token") authorization: String
    ): Call<GetCandiStatuskeyPipeline>

    @GET("/api/v1/Job/get-all-job-mobile/{searchName}")
    fun GetRecentJobs(
        @Header("x-access-token") authorization: String,
        @Path("searchName") searchName: String
        //   @Path(value = "searchName", encoded = true) searchName: String?
    ): Call<GetRecentJobsRes>

}
