package com.mobile.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST ("/banque/comptes")
    Call<Compte> createCompte(@Body Compte compte);

    //////@Headers("Content-Type: application/xml")
    @GET("/banque/comptes")
    Call<List<Compte>> getComptes();




}
