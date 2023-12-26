
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetRights;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("userRightsId")
    @Expose
    private Integer userRightsId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("rightName")
    @Expose
    private String rightName;
    @SerializedName("moduleName")
    @Expose
    private String moduleName;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("rightsId")
    @Expose
    private Integer rightsId;
    @SerializedName("isAllowed")
    @Expose
    private Boolean isAllowed;
    @SerializedName("subModuleName")
    @Expose
    private String subModuleName;

    public Integer getUserRightsId() {
        return userRightsId;
    }

    public void setUserRightsId(Integer userRightsId) {
        this.userRightsId = userRightsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRightsId() {
        return rightsId;
    }

    public void setRightsId(Integer rightsId) {
        this.rightsId = rightsId;
    }

    public Boolean getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(Boolean isAllowed) {
        this.isAllowed = isAllowed;
    }

    public String getSubModuleName() {
        return subModuleName;
    }

    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
    }

}
