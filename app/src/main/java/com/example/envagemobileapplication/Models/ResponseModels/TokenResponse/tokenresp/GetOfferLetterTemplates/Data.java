
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetOfferLetterTemplates;

import java.util.List;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 
public class Data {

    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("isFirstPage")
    @Expose
    private Boolean isFirstPage;
    @SerializedName("isLastPage")
    @Expose
    private Boolean isLastPage;
    @SerializedName("totalRecords")
    @Expose
    private Integer totalRecords;
    @SerializedName("records")
    @Expose
    private List<Record> records;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(Boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public Boolean getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(Boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

}
