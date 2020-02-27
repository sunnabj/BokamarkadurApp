package com.example.bokamarkadur.data.model.remote;

import android.telephony.mbms.FileInfo;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileService {

    @Multipart
    @POST("upload")
    Call<FileInfo> upload(@Part MultipartBody.Part file);
}
