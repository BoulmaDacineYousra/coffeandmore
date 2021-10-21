package com.example.coffeeandmore;

public class UserData {
    private  int Uid ;
    private String UNAME ,EMAIL  ,PWD ;

    public UserData() {

    }

    public UserData(int uid, String UNAME, String EMAIL, String PWD) {
        Uid = uid;
        this.UNAME = UNAME;
        this.EMAIL = EMAIL;
        this.PWD = PWD;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getUNAME() {
        return UNAME;
    }

    public void setUNAME(String UNAME) {
        this.UNAME = UNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPWD() {
        return PWD;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }
}
