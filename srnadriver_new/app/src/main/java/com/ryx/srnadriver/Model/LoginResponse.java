package com.ryx.srnadriver.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public class Data {

        @SerializedName("driver")
        @Expose
        private Driver driver;

        public Driver getDriver() {
            return driver;
        }

        public void setDriver(Driver driver) {
            this.driver = driver;
        }

    }
    public class Driver {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("mobileNumber")
        @Expose
        private String mobileNumber;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("picture")
        @Expose
        private String picture;
        @SerializedName("mobileVerified")
        @Expose
        private Boolean mobileVerified;
        @SerializedName("dateOfBirth")
        @Expose
        private String dateOfBirth;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("vehicleTypeId")
        @Expose
        private Integer vehicleTypeId;
        @SerializedName("vehicleType")
        @Expose
        private String vehicleType;
        @SerializedName("vehicleTypeIcon")
        @Expose
        private String vehicleTypeIcon;
        @SerializedName("vehicleMapIcon")
        @Expose
        private String vehicleMapIcon;
        @SerializedName("vehicleModel")
        @Expose
        private Object vehicleModel;
        @SerializedName("vehicleNumber")
        @Expose
        private Object vehicleNumber;
        @SerializedName("vehicleColor")
        @Expose
        private Object vehicleColor;
        @SerializedName("vehiclePicture")
        @Expose
        private Object vehiclePicture;
        @SerializedName("dateOfRegistration")
        @Expose
        private String dateOfRegistration;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("securityCode")
        @Expose
        private String securityCode;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;
        @SerializedName("inactiveReason")
        @Expose
        private String inactiveReason;
        @SerializedName("isNew")
        @Expose
        private Boolean isNew;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public Boolean getMobileVerified() {
            return mobileVerified;
        }

        public void setMobileVerified(Boolean mobileVerified) {
            this.mobileVerified = mobileVerified;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public Integer getVehicleTypeId() {
            return vehicleTypeId;
        }

        public void setVehicleTypeId(Integer vehicleTypeId) {
            this.vehicleTypeId = vehicleTypeId;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getVehicleTypeIcon() {
            return vehicleTypeIcon;
        }

        public void setVehicleTypeIcon(String vehicleTypeIcon) {
            this.vehicleTypeIcon = vehicleTypeIcon;
        }

        public String getVehicleMapIcon() {
            return vehicleMapIcon;
        }

        public void setVehicleMapIcon(String vehicleMapIcon) {
            this.vehicleMapIcon = vehicleMapIcon;
        }

        public Object getVehicleModel() {
            return vehicleModel;
        }

        public void setVehicleModel(Object vehicleModel) {
            this.vehicleModel = vehicleModel;
        }

        public Object getVehicleNumber() {
            return vehicleNumber;
        }

        public void setVehicleNumber(Object vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public Object getVehicleColor() {
            return vehicleColor;
        }

        public void setVehicleColor(Object vehicleColor) {
            this.vehicleColor = vehicleColor;
        }

        public Object getVehiclePicture() {
            return vehiclePicture;
        }

        public void setVehiclePicture(Object vehiclePicture) {
            this.vehiclePicture = vehiclePicture;
        }

        public String getDateOfRegistration() {
            return dateOfRegistration;
        }

        public void setDateOfRegistration(String dateOfRegistration) {
            this.dateOfRegistration = dateOfRegistration;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getSecurityCode() {
            return securityCode;
        }

        public void setSecurityCode(String securityCode) {
            this.securityCode = securityCode;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getInactiveReason() {
            return inactiveReason;
        }

        public void setInactiveReason(String inactiveReason) {
            this.inactiveReason = inactiveReason;
        }

        public Boolean getIsNew() {
            return isNew;
        }

        public void setIsNew(Boolean isNew) {
            this.isNew = isNew;
        }

    }
}


