package com.example.loginapitest;

public class UserInfoDto {

    private String userinfoId;
    private String userinfoPw;
    private String userinfoAddr;
    private String userinfoTel;

    public UserInfoDto(String userinfoId, String userinfoPw, String userinfoAddr, String userinfoTel) {
        this.userinfoId = userinfoId;
        this.userinfoPw = userinfoPw;
        this.userinfoAddr = userinfoAddr;
        this.userinfoTel = userinfoTel;
    }

    public String getUserinfoId() {
        return userinfoId;
    }

    public void setUserinfoId(String userinfoId) {
        this.userinfoId = userinfoId;
    }

    public String getUserinfoPw() {
        return userinfoPw;
    }

    public void setUserinfoPw(String userinfoPw) {
        this.userinfoPw = userinfoPw;
    }

    public String getUserinfoAddr() {
        return userinfoAddr;
    }

    public void setUserinfoAddr(String userinfoAddr) {
        this.userinfoAddr = userinfoAddr;
    }

    public String getUserinfoTel() {
        return userinfoTel;
    }

    public void setUserinfoTel(String userinfoTel) {
        this.userinfoTel = userinfoTel;
    }
}
