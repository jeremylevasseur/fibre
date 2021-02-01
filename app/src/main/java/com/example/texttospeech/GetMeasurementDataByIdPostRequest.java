package com.example.texttospeech;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMeasurementDataByIdPostRequest {

    @SerializedName("measurement_num")
    @Expose
    private int measurementNumber;

    public GetMeasurementDataByIdPostRequest(int measurementNumber) {
        this.measurementNumber = measurementNumber;

    }

    public int getMeasurementNumber() {
        return measurementNumber;
    }

    public void setMeasurementNumber(int measurementNumber) {
        this.measurementNumber = measurementNumber;
    }
}

