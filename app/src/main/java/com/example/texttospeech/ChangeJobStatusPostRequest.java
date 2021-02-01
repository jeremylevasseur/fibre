package com.example.texttospeech;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeJobStatusPostRequest {

    @SerializedName("job_id")
    @Expose
    private int jobId;

    @SerializedName("new_job_status")
    @Expose
    private String newJobStatus;

    public ChangeJobStatusPostRequest(int jobId, String newJobStatus) {
        this.jobId = jobId;
        this.newJobStatus = newJobStatus;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getNewJobStatus() {
        return newJobStatus;
    }

    public void setNewJobStatus(String newJobStatus) {
        this.newJobStatus = newJobStatus;
    }
}
