package com.example.whatsappclone.models;
//this class called as a data class
//it contains all the details of the user
//and we ccan get this details by just creating the instance of this class
//so thats why here we declare getter and setter class for the same
//this class is very good practice in it world
//also here we create threee different different constructor because we need this in different different class
public class Users {
    String profilePicture;
    String name;
    String pass,mail,lastmessage,userid,aboutus;

    public String getProfilePicture() {
        return profilePicture;
    }

    public Users(String profilePicture, String name, String pass, String mail, String lastmessage, String userid,String aboutus) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.pass = pass;
        this.mail = mail;
        this.lastmessage = lastmessage;
        this.userid = userid;
        this.aboutus=aboutus;
    }

    public String getUserid() {
        return userid;
    }

    public Users(){

    }

    //constructor for sign up Constructor
    public Users( String name, String pass, String mail) {

        this.name = name;
        this.pass = pass;
        this.mail = mail;

    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getMail() {
        return mail;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public String getUserid(String key) {
        return userid;
    }
}
