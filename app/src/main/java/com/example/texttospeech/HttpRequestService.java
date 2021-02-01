package com.example.texttospeech;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpRequestService {

    /*
        =========================== ENDPOINTS =============================
        1)  POST    /api/v1/create_new_user

    */

    @Headers({"Content-Type: application/json"})
    @POST("/api/main/v1/change_job_status")
    Call<ServerPostResponse> changeJobStatus(@Body ChangeJobStatusPostRequest changeJobStatusPostRequest);

    @Headers({"Content-Type: application/json"})
    @POST("/api/main/v1/get_job_data_by_id")
    Call<List<GetJobDataByIdPostResponse>> getJobDataById(@Body GetJobDataByIdPostRequest getJobDataByIdPostRequest);

    @Headers({"Content-Type: application/json"})
    @POST("/api/main/v1/get_measurement_data_by_id")
    Call<List<GetMeasurementDataByIdPostResponse>> getMeasurementDataById(@Body GetMeasurementDataByIdPostRequest getMeasurementDataByIdPostRequest);

}

