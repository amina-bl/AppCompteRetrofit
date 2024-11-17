package com.mobile.myapplication;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.100.157:8082";

    public static Retrofit getRetrofitInstance(String rt) {


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Accept", "application/"+rt)
                            .addHeader("Content-type", "application/"+rt)
                            .build();
                    return chain.proceed(request);
                })
                .build();

                   if(rt.equals("json")){
                       retrofit = new Retrofit.Builder()
                               .baseUrl(BASE_URL)
                               .addConverterFactory(GsonConverterFactory.create())
                               .client(okHttpClient)
                               .build();
                   }else{
                       retrofit = new Retrofit.Builder()
                               .baseUrl(BASE_URL)
                              // .addConverterFactory(JaxbConverterFactory.create())
                               .addConverterFactory(GsonConverterFactory.create())
                               //.addConverterFactory(SimpleXmlConverterFactory.create())
                               .client(okHttpClient)
                               .build();
                   }



        return retrofit;
       /* if (retrofit == null) {
            if(rt.equals("JSON")){
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }else{
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(SimpleXmlConverterFactory.create())
                        .build();
            }

        }
            if(request_type.equals("JSON")){
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }else{
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(SimpleXmlConverterFactory.create())
                        .build();
            }


        return retrofit;*/
    }

}
