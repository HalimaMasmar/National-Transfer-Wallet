package com.example.be.Retrofit;

import com.example.be.Benef.BenefModel;
import com.example.be.Datas.AuthenticationDTO;
import com.example.be.Datas.AuthenticationTokenDTO;
import com.example.be.Datas.Client;
import com.example.be.Datas.MultiTransfer;

import com.example.be.Datas.Transfer;
import com.example.be.Spinner.SpinnerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    public String token = null;

    @GET("login")
    Call<List<AuthenticationTokenDTO>> getToken();

    @POST("login")
    Call<AuthenticationTokenDTO> postemail(@Body AuthenticationDTO body);


    @GET("me")
    Call<Client> getclient(@Header("Authorization") String token,
                           @Header("id") Integer id);


    @GET("beneficiaires")
    Call<List<SpinnerModel>> getlistbenef(@Header("Authorization") String token,
                                          @Header("id") Integer id);


    @POST("beneficiaire")
    Call<SpinnerModel> postbenef(@Header("Authorization") String token,
                                 @Header("id") Integer id ,@Body SpinnerModel body);


    @GET("client")
    Call<List<MultiTransfer>> getMultitransfers(@Header("Authorization") String token,
                                                @Header("id") Integer id);
    @POST("createTransfer")
    Call<MultiTransfer> postTransfer(@Header("Authorization") String token,
                                     @Header("id") Integer id,@Body MultiTransfer body);

    @GET("UniqueTransfer/{reference}")
    Call<MultiTransfer> getTransfer(@Header("Authorization") String token,
                                    @Header("id") Integer id, @Path("reference") String reference);
    @PUT("https://client-microserv.herokuapp.com/api_client/UniqueTransfer/return/{reference}")
    Call<MultiTransfer> putTransfer(@Header("Authorization") String token,
                                    @Header("id") Integer id, @Path("reference") String reference,@Query("motif") String motif);
}
