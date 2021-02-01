package com.example.texttospeech;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMeasurementDataByIdPostResponse {

    @SerializedName("measurement_num")
    @Expose
    private int measurementNumber;

    @SerializedName("measurement")
    @Expose
    private String measurement;

    @SerializedName("start_time")
    @Expose
    private String startTime;

    @SerializedName("finish_time")
    @Expose
    private String finishTime;

    @SerializedName("graph_picture_location")
    @Expose
    private String graphPictureLocation;

    public GetMeasurementDataByIdPostResponse(int measurementNumber, String measurement, String startTime, String finishTime, String graphPictureLocation) {
        this.measurementNumber = measurementNumber;
        this.measurement = measurement;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.graphPictureLocation = graphPictureLocation;
    }

    public int getMeasurementNumber() {
        return measurementNumber;
    }

    public void setMeasurementNumber(int measurementNumber) {
        this.measurementNumber = measurementNumber;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getGraphPictureLocation() {
        return graphPictureLocation;
    }

    public void setGraphPictureLocation(String graphPictureLocation) {
        this.graphPictureLocation = graphPictureLocation;
    }
}

