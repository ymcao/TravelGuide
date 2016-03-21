package com.material.travel;


import com.material.travel.model.DownloadResult;
import com.material.travel.model.LoginResult;
import com.material.travel.model.Results;
import com.material.travel.model.TravelInfo;
import com.material.travel.model.UploadResult;
import com.material.travel.model.UserModel;
import com.squareup.okhttp.RequestBody;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PartMap;
import retrofit.http.Query;
import rx.Observable;


/**
 * Created by caoyamin on 15/9/16.
 */
public interface APIService {
   
    /**///@FormUrlEncoded - Field

    @GET("apistore/idservice/id")
    Observable<Results> queryIdCard(@Header("apikey") String
    apikey,@Query("id") String id);

    @GET("qunartravel/travellist/travellist")
    Observable<TravelInfo> queryTravelLists(@Header("apikey") String
                                            apikey,@Query("query") String query,@Query("page") int page);
    @Multipart
    @POST("/MobileSpringMVC/UploadController/upload.do")
    Observable<UploadResult> postIdCard(@PartMap Map<String,RequestBody> maps);


    @POST("/MobileSpringMVC/UploadController/loginSubmit.do")
    Observable<LoginResult> loginSubmit(@Body UserModel model);

    @GET("/MobileSpringMVC/UploadController/toDownload.do")
    Call<DownloadResult> downloadFile(@Query("id") String id);
}
