package com.ryx.srnadriver.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentTripResponse {
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

        @SerializedName("tripInfo")
        @Expose
        private List<TripInfo> tripInfo = null;

        public List<TripInfo> getTripInfo() {
            return tripInfo;
        }

        public void setTripInfo(List<TripInfo> tripInfo) {
            this.tripInfo = tripInfo;
        }

    }
    public class TripInfo {

        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("amountTag")
        @Expose
        private Object amountTag;
        @SerializedName("sourceLatitude")
        @Expose
        private Double sourceLatitude;
        @SerializedName("sourceLongitude")
        @Expose
        private Double sourceLongitude;
        @SerializedName("sourceAddress")
        @Expose
        private String sourceAddress;
        @SerializedName("pickupLatitude")
        @Expose
        private Integer pickupLatitude;
        @SerializedName("pickupLongitude")
        @Expose
        private Integer pickupLongitude;
        @SerializedName("pickupAddress")
        @Expose
        private Object pickupAddress;
        @SerializedName("estimatedFare")
        @Expose
        private Integer estimatedFare;
        @SerializedName("estimatedTime")
        @Expose
        private Integer estimatedTime;
        @SerializedName("estimatedDistance")
        @Expose
        private Double estimatedDistance;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Object getAmountTag() {
            return amountTag;
        }

        public void setAmountTag(Object amountTag) {
            this.amountTag = amountTag;
        }

        public Double getSourceLatitude() {
            return sourceLatitude;
        }

        public void setSourceLatitude(Double sourceLatitude) {
            this.sourceLatitude = sourceLatitude;
        }

        public Double getSourceLongitude() {
            return sourceLongitude;
        }

        public void setSourceLongitude(Double sourceLongitude) {
            this.sourceLongitude = sourceLongitude;
        }

        public String getSourceAddress() {
            return sourceAddress;
        }

        public void setSourceAddress(String sourceAddress) {
            this.sourceAddress = sourceAddress;
        }

        public Integer getPickupLatitude() {
            return pickupLatitude;
        }

        public void setPickupLatitude(Integer pickupLatitude) {
            this.pickupLatitude = pickupLatitude;
        }

        public Integer getPickupLongitude() {
            return pickupLongitude;
        }

        public void setPickupLongitude(Integer pickupLongitude) {
            this.pickupLongitude = pickupLongitude;
        }

        public Object getPickupAddress() {
            return pickupAddress;
        }

        public void setPickupAddress(Object pickupAddress) {
            this.pickupAddress = pickupAddress;
        }

        public Integer getEstimatedFare() {
            return estimatedFare;
        }

        public void setEstimatedFare(Integer estimatedFare) {
            this.estimatedFare = estimatedFare;
        }

        public Integer getEstimatedTime() {
            return estimatedTime;
        }

        public void setEstimatedTime(Integer estimatedTime) {
            this.estimatedTime = estimatedTime;
        }

        public Double getEstimatedDistance() {
            return estimatedDistance;
        }

        public void setEstimatedDistance(Double estimatedDistance) {
            this.estimatedDistance = estimatedDistance;
        }

    }
}
