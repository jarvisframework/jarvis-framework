package com.jarvis.framework.security.authentication.idcard;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月29日
 */
public class IdcardModel {

    private String idCardNumber;

    private String name;

    private String sex;

    private String address;

    private String birthDate;

    private String nationality;

    private String extension;

    public IdcardModel() {

    }

    /**
     * @param idCardNumber
     * @param name
     * @param sex
     * @param address
     * @param birthDate
     * @param nationality
     * @param extension
     */
    public IdcardModel(String idCardNumber, String name, String sex, String address, String birthDate,
                       String nationality, String extension) {
        super();
        this.idCardNumber = idCardNumber;
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.extension = extension;
    }

    /**
     * @return the idCardNumber
     */
    public String getIdCardNumber() {
        return idCardNumber;
    }

    /**
     * @param idCardNumber the idCardNumber to set
     */
    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

}
