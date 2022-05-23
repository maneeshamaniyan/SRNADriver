package com.ryx.srnadriver.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RideResponse {
    @SerializedName("rideInfo")
    @Expose
    private RideInfo rideInfo;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;

    public RideInfo getRideInfo() {
        return rideInfo;
    }

    public void setRideInfo(RideInfo rideInfo) {
        this.rideInfo = rideInfo;
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

    public static class RideInfo implements Parcelable {

        @SerializedName("requestId")
        @Expose
        private Integer requestId;
        @SerializedName("customerId")
        @Expose
        private Integer customerId;
        @SerializedName("customerName")
        @Expose
        private String customerName;
        @SerializedName("customerPhone")
        @Expose
        private String customerPhone;
        @SerializedName("pickupLocation")
        @Expose
        private Object pickupLocation;
        @SerializedName("pickupLatitude")
        @Expose
        private Integer pickupLatitude;
        @SerializedName("pickupLongitude")
        @Expose
        private Integer pickupLongitude;
        @SerializedName("sourceLatitude")
        @Expose
        private Double sourceLatitude;
        @SerializedName("sourceLongitude")
        @Expose
        private Double sourceLongitude;
        @SerializedName("sourceAddress")
        @Expose
        private String sourceAddress;
        @SerializedName("destinationAddress")
        @Expose
        private String destinationAddress;
        @SerializedName("destinationLatitude")
        @Expose
        private Double destinationLatitude;
        @SerializedName("destinationLongitude")
        @Expose
        private Double destinationLongitude;
        @SerializedName("dropLocation")
        @Expose
        private Object dropLocation;
        @SerializedName("dropLatitude")
        @Expose
        private Integer dropLatitude;
        @SerializedName("dropLogitude")
        @Expose
        private Integer dropLogitude;
        @SerializedName("isScheduleRide")
        @Expose
        private Boolean isScheduleRide;
        @SerializedName("scheduleDate")
        @Expose
        private String scheduleDate;
        @SerializedName("scheduleTime")
        @Expose
        private String scheduleTime;
        @SerializedName("driverVehicleTypeId")
        @Expose
        private Integer driverVehicleTypeId;
        @SerializedName("driverVehicleTypeName")
        @Expose
        private String driverVehicleTypeName;
        @SerializedName("driverPicture")
        @Expose
        private String driverPicture;
        @SerializedName("qRCodeImage")
        @Expose
        private String qRCodeImage;
        @SerializedName("bookingNo")
        @Expose
        private String bookingNo;
        @SerializedName("estimatedFare")
        @Expose
        private double estimatedFare;
        @SerializedName("estimatedTime")
        @Expose
        private Integer estimatedTime;
        @SerializedName("estimatedDistance")
        @Expose
        private Double estimatedDistance;
        @SerializedName("realFare")
        @Expose
        private Integer realFare;
        @SerializedName("realTime")
        @Expose
        private Integer realTime;
        @SerializedName("realDistance")
        @Expose
        private Integer realDistance;
        @SerializedName("discountedFare")
        @Expose
        private Object discountedFare;
        @SerializedName("discountAmount")
        @Expose
        private Object discountAmount;
        @SerializedName("isDiscounted")
        @Expose
        private Boolean isDiscounted;
        @SerializedName("discountCodeId")
        @Expose
        private Object discountCodeId;
        @SerializedName("splitFare")
        @Expose
        private List<SplitFareModel> splitFare = null;
        @SerializedName("isScheduleConfirmed")
        @Expose
        private boolean isScheduleConfirmed;

        protected RideInfo(Parcel in) {
            if (in.readByte() == 0) {
                requestId = null;
            } else {
                requestId = in.readInt();
            }
            if (in.readByte() == 0) {
                customerId = null;
            } else {
                customerId = in.readInt();
            }
            customerName = in.readString();
            customerPhone = in.readString();
            if (in.readByte() == 0) {
                pickupLatitude = null;
            } else {
                pickupLatitude = in.readInt();
            }
            if (in.readByte() == 0) {
                pickupLongitude = null;
            } else {
                pickupLongitude = in.readInt();
            }
            if (in.readByte() == 0) {
                sourceLatitude = null;
            } else {
                sourceLatitude = in.readDouble();
            }
            if (in.readByte() == 0) {
                sourceLongitude = null;
            } else {
                sourceLongitude = in.readDouble();
            }
            sourceAddress = in.readString();
            destinationAddress = in.readString();
            if (in.readByte() == 0) {
                destinationLatitude = null;
            } else {
                destinationLatitude = in.readDouble();
            }
            if (in.readByte() == 0) {
                destinationLongitude = null;
            } else {
                destinationLongitude = in.readDouble();
            }
            if (in.readByte() == 0) {
                dropLatitude = null;
            } else {
                dropLatitude = in.readInt();
            }
            if (in.readByte() == 0) {
                dropLogitude = null;
            } else {
                dropLogitude = in.readInt();
            }
            byte tmpIsScheduleRide = in.readByte();
            isScheduleRide = tmpIsScheduleRide == 0 ? null : tmpIsScheduleRide == 1;
            scheduleDate = in.readString();
            scheduleTime = in.readString();
            if (in.readByte() == 0) {
                driverVehicleTypeId = null;
            } else {
                driverVehicleTypeId = in.readInt();
            }
            driverVehicleTypeName = in.readString();
            driverPicture = in.readString();
            qRCodeImage = in.readString();
            bookingNo = in.readString();
            estimatedFare = in.readDouble();
            if (in.readByte() == 0) {
                estimatedTime = null;
            } else {
                estimatedTime = in.readInt();
            }
            if (in.readByte() == 0) {
                estimatedDistance = null;
            } else {
                estimatedDistance = in.readDouble();
            }
            if (in.readByte() == 0) {
                realFare = null;
            } else {
                realFare = in.readInt();
            }
            if (in.readByte() == 0) {
                realTime = null;
            } else {
                realTime = in.readInt();
            }
            if (in.readByte() == 0) {
                realDistance = null;
            } else {
                realDistance = in.readInt();
            }
            byte tmpIsDiscounted = in.readByte();
            isDiscounted = tmpIsDiscounted == 0 ? null : tmpIsDiscounted == 1;
            isScheduleConfirmed = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (requestId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(requestId);
            }
            if (customerId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(customerId);
            }
            dest.writeString(customerName);
            dest.writeString(customerPhone);
            if (pickupLatitude == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(pickupLatitude);
            }
            if (pickupLongitude == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(pickupLongitude);
            }
            if (sourceLatitude == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(sourceLatitude);
            }
            if (sourceLongitude == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(sourceLongitude);
            }
            dest.writeString(sourceAddress);
            dest.writeString(destinationAddress);
            if (destinationLatitude == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(destinationLatitude);
            }
            if (destinationLongitude == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(destinationLongitude);
            }
            if (dropLatitude == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(dropLatitude);
            }
            if (dropLogitude == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(dropLogitude);
            }
            dest.writeByte((byte) (isScheduleRide == null ? 0 : isScheduleRide ? 1 : 2));
            dest.writeString(scheduleDate);
            dest.writeString(scheduleTime);
            if (driverVehicleTypeId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(driverVehicleTypeId);
            }
            dest.writeString(driverVehicleTypeName);
            dest.writeString(driverPicture);
            dest.writeString(qRCodeImage);
            dest.writeString(bookingNo);
            dest.writeDouble(estimatedFare);
            if (estimatedTime == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(estimatedTime);
            }
            if (estimatedDistance == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(estimatedDistance);
            }
            if (realFare == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(realFare);
            }
            if (realTime == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(realTime);
            }
            if (realDistance == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(realDistance);
            }
            dest.writeByte((byte) (isDiscounted == null ? 0 : isDiscounted ? 1 : 2));
            dest.writeByte((byte) (isScheduleConfirmed ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<RideInfo> CREATOR = new Creator<RideInfo>() {
            @Override
            public RideInfo createFromParcel(Parcel in) {
                return new RideInfo(in);
            }

            @Override
            public RideInfo[] newArray(int size) {
                return new RideInfo[size];
            }
        };

        public Integer getRequestId() {
            return requestId;
        }

        public void setRequestId(Integer requestId) {
            this.requestId = requestId;
        }

        public Integer getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public Object getPickupLocation() {
            return pickupLocation;
        }

        public void setPickupLocation(Object pickupLocation) {
            this.pickupLocation = pickupLocation;
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

        public String getDestinationAddress() {
            return destinationAddress;
        }

        public void setDestinationAddress(String destinationAddress) {
            this.destinationAddress = destinationAddress;
        }

        public Double getDestinationLatitude() {
            return destinationLatitude;
        }

        public void setDestinationLatitude(Double destinationLatitude) {
            this.destinationLatitude = destinationLatitude;
        }

        public Double getDestinationLongitude() {
            return destinationLongitude;
        }

        public void setDestinationLongitude(Double destinationLongitude) {
            this.destinationLongitude = destinationLongitude;
        }

        public Object getDropLocation() {
            return dropLocation;
        }

        public void setDropLocation(Object dropLocation) {
            this.dropLocation = dropLocation;
        }

        public Integer getDropLatitude() {
            return dropLatitude;
        }

        public void setDropLatitude(Integer dropLatitude) {
            this.dropLatitude = dropLatitude;
        }

        public Integer getDropLogitude() {
            return dropLogitude;
        }

        public void setDropLogitude(Integer dropLogitude) {
            this.dropLogitude = dropLogitude;
        }

        public Boolean getIsScheduleRide() {
            return isScheduleRide;
        }

        public void setIsScheduleRide(Boolean isScheduleRide) {
            this.isScheduleRide = isScheduleRide;
        }

        public String getScheduleDate() {
            return scheduleDate;
        }

        public void setScheduleDate(String scheduleDate) {
            this.scheduleDate = scheduleDate;
        }

        public String getScheduleTime() {
            return scheduleTime;
        }

        public void setScheduleTime(String scheduleTime) {
            this.scheduleTime = scheduleTime;
        }

        public Integer getDriverVehicleTypeId() {
            return driverVehicleTypeId;
        }

        public void setDriverVehicleTypeId(Integer driverVehicleTypeId) {
            this.driverVehicleTypeId = driverVehicleTypeId;
        }

        public String getDriverVehicleTypeName() {
            return driverVehicleTypeName;
        }

        public void setDriverVehicleTypeName(String driverVehicleTypeName) {
            this.driverVehicleTypeName = driverVehicleTypeName;
        }

        public String getDriverPicture() {
            return driverPicture;
        }

        public void setDriverPicture(String driverPicture) {
            this.driverPicture = driverPicture;
        }

        public String getqRCodeImage() {
            return qRCodeImage;
        }

        public void setqRCodeImage(String qRCodeImage) {
            this.qRCodeImage = qRCodeImage;
        }

        public String getBookingNo() {
            return bookingNo;
        }

        public void setBookingNo(String bookingNo) {
            this.bookingNo = bookingNo;
        }

        public Double getEstimatedFare() {
            return estimatedFare;
        }

        public void setEstimatedFare(Double estimatedFare) {
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

        public Integer getRealFare() {
            return realFare;
        }

        public void setRealFare(Integer realFare) {
            this.realFare = realFare;
        }

        public Integer getRealTime() {
            return realTime;
        }

        public void setRealTime(Integer realTime) {
            this.realTime = realTime;
        }

        public Integer getRealDistance() {
            return realDistance;
        }

        public void setRealDistance(Integer realDistance) {
            this.realDistance = realDistance;
        }

        public Object getDiscountedFare() {
            return discountedFare;
        }

        public void setDiscountedFare(Object discountedFare) {
            this.discountedFare = discountedFare;
        }

        public Object getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Object discountAmount) {
            this.discountAmount = discountAmount;
        }

        public Boolean getIsDiscounted() {
            return isDiscounted;
        }

        public void setIsDiscounted(Boolean isDiscounted) {
            this.isDiscounted = isDiscounted;
        }

        public Object getDiscountCodeId() {
            return discountCodeId;
        }

        public void setDiscountCodeId(Object discountCodeId) {
            this.discountCodeId = discountCodeId;
        }

        public List<SplitFareModel> getSplitFare()  {
            return splitFare;
        }

        public void setSplitFare(List<SplitFareModel> splitFare) {
            this.splitFare = splitFare;
        }

        public Boolean getScheduleRide() {
            return isScheduleRide;
        }

        public void setScheduleRide(Boolean scheduleRide) {
            isScheduleRide = scheduleRide;
        }

        public void setEstimatedFare(double estimatedFare) {
            this.estimatedFare = estimatedFare;
        }

        public Boolean getDiscounted() {
            return isDiscounted;
        }

        public void setDiscounted(Boolean discounted) {
            isDiscounted = discounted;
        }

        public boolean isScheduleConfirmed() {
            return isScheduleConfirmed;
        }

        public void setScheduleConfirmed(boolean scheduleConfirmed) {
            isScheduleConfirmed = scheduleConfirmed;
        }
    }


}
