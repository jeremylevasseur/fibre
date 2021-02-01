package com.example.texttospeech;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerPostResponse {

    @SerializedName("status")
    @Expose
    private String status;

    public ServerPostResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
