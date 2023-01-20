package com.jarvis.framework.security.authentication.idcard;

public class IdcardModel {
    private String idCardNumber;
    private String name;
    private String sex;
    private String address;
    private String birthDate;
    private String nationality;
    private String extension;

    public IdcardModel(String idCardNumber, String name, String sex, String address, String birthDate, String nationality, String extension) {
        this.idCardNumber = idCardNumber;
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.extension = extension;
    }

    public String getIdCardNumber() {
        return this.idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
