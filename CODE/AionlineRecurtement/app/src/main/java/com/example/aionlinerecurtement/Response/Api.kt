package com.example.aionlinerecurtement.Response

import com.example.aionlinerecurtement.Company.ViewJobs
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface Api {

    @FormUrlEncoded
    @POST("user.php")
    fun singup(
        @Field("name")name:String,
        @Field("mail")mail:String,
        @Field("password")password:String,
        @Field("mobile")mobile:String,
        @Field("type")type:String
    ):Call<CommonResponse>

    @FormUrlEncoded
    @POST("user.php")
fun login(
    @Field("condition")condition:String,
    @Field("mail")mail:String,
    @Field("password")password:String
):Call<LoginResponse>

@FormUrlEncoded
@POST("user.php")
fun add_company(
    @Field("name")name:String,
    @Field("mobile")mobile:String,
    @Field("mail")mail:String,
    @Field("password")password:String,
    @Field("address")address:String,
    @Field("type")type:String
):Call<CommonResponse>
@FormUrlEncoded
@POST("addjob.php")
    fun add_jobs(
    @Field("jobtitle")jobtitle:String,
    @Field("description")description:String,
    @Field("roles")roles:String,
    @Field("skills")skills:String,
    @Field("id")id:String,
    @Field("encoded")encoded:String
    ):Call<CommonResponse>


    @GET("viewjobs.php")
    fun View_companies():Call<JobsResponse>


    fun viewApplied()
@FormUrlEncoded
    @POST("addresume.php")
     fun add_Resume(
        @Field("encode")encode: String,
        @Field("id")id:String,
        @Field("name")name: String,
        @Field("mail")mail: String,
        @Field("mobile")mobile: String,
        @Field("adress")adress: String,
        @Field("career")career: String,
        @Field("acadmeic")acadmeic: String,
        @Field("profession")profession: String,
        @Field("workexperience")workexperience: String,
        @Field("personaldetails")personaldetails: String
    ):Call<CommonResponse>

     @FormUrlEncoded
     @POST("getresume.php")
     fun view_resume_data(
         @Field("id")id: String
     ):Call<ResumeResponse>


    @Multipart
    @POST("uploadfile.php")
    fun  uploadImage(
        @Part file: MultipartBody.Part,
        @Part("filename") name: RequestBody,
        @Part("mobile")mobile:RequestBody,
        @Part("date")date:RequestBody,
        @Part("userid")userid:RequestBody,
        @Part("companyid")companyid:RequestBody,
        @Part("workid")workid:RequestBody
    ): Call<CommonResponse>
@FormUrlEncoded
    @POST("viewjobdetails.php")
    fun viewdetails(
    @Field("jobid")jobid:String
    ):Call<JobsResponse>

    @FormUrlEncoded
    @POST("viewrequests.php")
     fun viewmycomapny(
        @Field("id")id: String):Call<ResumeResponse>
@FormUrlEncoded
@POST("details.php")
    fun view_applied(
    @Field("userid")userid:String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("updateresume.php")
    fun update(
        @Field("id")id:String,
        @Field("status")status:String
    ):Call<CommonResponse>

@GET("viewrequest.php")
    fun view_details():Call<ResumeResponse>
}


