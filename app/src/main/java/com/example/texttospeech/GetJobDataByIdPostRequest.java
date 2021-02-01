package com.example.texttospeech;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetJobDataByIdPostRequest {

    @SerializedName("job_id")
    @Expose
    private int jobId;

    public GetJobDataByIdPostRequest(int jobId) {
        this.jobId = jobId;

    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}

