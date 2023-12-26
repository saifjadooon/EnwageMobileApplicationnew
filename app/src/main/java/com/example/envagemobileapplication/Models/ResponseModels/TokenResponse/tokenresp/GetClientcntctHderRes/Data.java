
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetClientcntctHderRes;

 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Data {

    @SerializedName("clientContactInfo")
    @Expose
    private ClientContactInfo clientContactInfo;
    @SerializedName("client")
    @Expose
    private Client client;

    public ClientContactInfo getClientContactInfo() {
        return clientContactInfo;
    }

    public void setClientContactInfo(ClientContactInfo clientContactInfo) {
        this.clientContactInfo = clientContactInfo;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
