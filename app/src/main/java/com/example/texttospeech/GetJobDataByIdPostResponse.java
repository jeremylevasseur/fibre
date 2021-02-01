package com.example.texttospeech;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetJobDataByIdPostResponse {

    @SerializedName("job_id")
    @Expose
    private int jobId;

    @SerializedName("job_status")
    @Expose
    private String jobStatus;

    @SerializedName("measurement_entry_id")
    @Expose
    private int measurementEntryId;

    @SerializedName("job_requested_time")
    @Expose
    private String jobRequestedTime;

    @SerializedName("job_completion_time")
    @Expose
    private String jobCompletionTime;


    public GetJobDataByIdPostResponse(int jobId, String jobStatus, int measurementEntryId, String jobRequestedTime, String jobCompletionTime) {
        this.jobId = jobId;
        this.jobStatus = jobStatus;
        this.measurementEntryId = measurementEntryId;
        this.jobRequestedTime = jobRequestedTime;
        this.jobCompletionTime = jobCompletionTime;

    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public int getMeasurementEntryId() {
        return measurementEntryId;
    }

    public void setMeasurementEntryId(int measurementEntryId) {
        this.measurementEntryId = measurementEntryId;
    }

    public String getJobRequestedTime() {
        return jobRequestedTime;
    }

    public void setJobRequestedTime(String jobRequestedTime) {
        this.jobRequestedTime = jobRequestedTime;
    }

    public String getJobCompletionTime() {
        return jobCompletionTime;
    }

    public void setJobCompletionTime(String jobCompletionTime) {
        this.jobCompletionTime = jobCompletionTime;
    }

}

