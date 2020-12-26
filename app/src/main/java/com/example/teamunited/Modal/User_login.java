package com.example.teamunited.Modal;

public class User_login {
    private int id;
    private  String did;

    private  String number;


    private  String user_id;

    public User_login(int id, String did, String number, String user_id) {
        this.id = id;
        this.did = did;
        this.number = number;
        this.user_id = user_id;
    }

    public String getNumber() {
        return number;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getId() {
        return id;
    }

    public String getDid() {
        return did;
    }

}
