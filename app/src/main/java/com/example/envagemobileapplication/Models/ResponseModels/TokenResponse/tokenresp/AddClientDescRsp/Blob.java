
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.AddClientDescRsp;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Blob {

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contentType")
    @Expose
    private Object contentType;
    @SerializedName("content")
    @Expose
    private Object content;
    @SerializedName("htmlText")
    @Expose
    private Object htmlText;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getContentType() {
        return contentType;
    }

    public void setContentType(Object contentType) {
        this.contentType = contentType;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(Object htmlText) {
        this.htmlText = htmlText;
    }

}
