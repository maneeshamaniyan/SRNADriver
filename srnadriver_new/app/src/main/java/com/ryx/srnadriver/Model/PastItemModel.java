package com.ryx.srnadriver.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PastItemModel {
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

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return isSuccess;
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
        private Double pickupLatitude;
        @SerializedName("pickupLongitude")
        @Expose
        private Double pickupLongitude;
        @SerializedName("pickupAddress")
        @Expose
        private String pickupAddress;
        @SerializedName("estimatedFare")
        @Expose
        private Integer estimatedFare;
        @SerializedName("estimatedTime")
        @Expose
        private Integer estimatedTime;
        @SerializedName("estimatedDistance")
        @Expose
        private Double estimatedDistance;
        @SerializedName("scheduleDate")
        @Expose
        private Object scheduleDate;
        @SerializedName("scheduleTime")
        @Expose
        private Object scheduleTime;

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

        public Double getPickupLatitude() {
            return pickupLatitude;
        }

        public void setPickupLatitude(Double pickupLatitude) {
            this.pickupLatitude = pickupLatitude;
        }

        public Double getPickupLongitude() {
            return pickupLongitude;
        }

        public void setPickupLongitude(Double pickupLongitude) {
            this.pickupLongitude = pickupLongitude;
        }

        public String getPickupAddress() {
            return pickupAddress;
        }

        public void setPickupAddress(String pickupAddress) {
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

        public Object getScheduleDate() {
            return scheduleDate;
        }

        public void setScheduleDate(Object scheduleDate) {
            this.scheduleDate = scheduleDate;
        }

        public Object getScheduleTime() {
            return scheduleTime;
        }

        public void setScheduleTime(Object scheduleTime) {
            this.scheduleTime = scheduleTime;
        }

    }
}
