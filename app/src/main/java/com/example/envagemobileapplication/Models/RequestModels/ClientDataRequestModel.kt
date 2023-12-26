package com.example.envagemobileapplication.Models.RequestModels

data class ClientDataRequestModel(
    val clients: List<Client>,
    val owner: Int
)

data class Client(
    val clientId: Int,
    val companyId: Int? = null,
    val industryName: String? = null,
    val taxId: Int? = null,
    val jobCount: Int? = null,
    val onboardingStatusId: Int? = null,
    val onboardingStatus: String? = null,
    val colorCode: String? = null,
    val contractStart: String? = null,
    val contractEnd: String? = null,
    val name: String? = null,
    val clientName: String? = null,
    val clientDba: String? = null,
    val description: String? = null,
    val profilePicture: String? = null,
    val profilePicturePath: String? = null,
    val country: String? = null,
    val primaryAddress1: String? = null,
    val primaryAddress2: String? = null,
    val primaryAddressCountry: String? = null,
    val primaryAddressCity: String? = null,
    val primaryAddressState: String? = null,
    val primaryAddressZipcode: String? = null,
    val billingAddress1: String? = null,
    val billingAddress2: String? = null,
    val billingAddressCountry: String? = null,
    val billingAddressCity: String? = null,
    val billingAddressState: String? = null,
    val billingZipcode: String? = null,
    val websiteUrl: String? = null,
    val isActive: Boolean? = null,
    val isDeleted: Boolean? = null,
    val createdBy: Int? = null,
    val createdDate: String? = null,
    val modifiedBy: Int? = null,
    val modifiedDate: String? = null,
    val visibilityStatus: String? = null,
    val zipcode: String? = null,
    val city: String? = null,
    val state: String? = null,
    val location: String? = null,
    val primaryAddressLocation: String? = null,
    val billingAddressLocation: String? = null,
    val phone: String? = null,
    val fein: String? = null,
    val naicsCode: String? = null,
    val sicCode: String? = null,
    val payrollSicCodeSetupId: Int? = null,
    val payrollNaicsCodeId: Int? = null,
    val businessEntityType: String? = null,
    val phoneExt: String? = null,
    val setupFee: Int? = null,
    val serviceChargesType: String? = null,
    val serviceChargesAmount: Int? = null,
    val backgroundScreeningType: String? = null,
    val backgroundScreeningAmount: Int? = null,
    val textMessagesType: String? = null,
    val textMessagesAmount: Int? = null,
    val resumeParsingType: String? = null,
    val resumeParsingAmount: Int? = null,
    val owner: Int? = null,
    val ownerName: String? = null,
    val primaryPhone: String? = null,
    val billingPhone: String? = null,
    val subscriptionPlan: String? = null,
    val planFee: String? = null,
    val invoiceStartDate: String? = null,
    val billCycle: String? = null,
    val billingDayOfWeek: String? = null,
    val billingDay: String? = null,
    val billingMonth: String? = null,
    val systemDefaultDate: String? = null,
    val isSystemDefault: Boolean? = null
)