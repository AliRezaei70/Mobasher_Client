package ir.mobasher.app.client.model.clientProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientProfile {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("birthDate")
    @Expose
    private Integer birthDate;
    @SerializedName("clientId")
    @Expose
    private String clientId;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("fieldOfStudy")
    @Expose
    private String fieldOfStudy;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("jobTitle")
    @Expose
    private String jobTitle;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("nationalId")
    @Expose
    private String nationalId;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("tel")
    @Expose
    private String tel;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
