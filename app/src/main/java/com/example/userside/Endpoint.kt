package com.example.userside

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface Endpoint {

    @Multipart
    @POST("uploadMedia")
    suspend fun uploadMedia(@Part image: MultipartBody.Part,
                            @Part data: MultipartBody.Part): Response<String?>

    @Multipart
    @POST("uploadVocal")
    suspend fun uploadVocal(@Part vocal: MultipartBody.Part,
                            @Part data: MultipartBody.Part): Response<String?>

    @Multipart
    @POST("uploadVideo")
    suspend fun uploadVideo(@Part video: MultipartBody.Part,
                            @Part data: MultipartBody.Part,):Response<String?>

}