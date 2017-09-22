package com.valensas.littlehut.network

import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by ogun on 19/09/2017.
 * Valensas 2017
 */
interface APIService {

    @GET("bus/attendance")
    fun getBusAttendance(): Observable<BusAttendanceModel>

    @POST("bus/attendance")
    fun postMyAttendance(@Body body: AttendModel): Observable<ResponseBody>

    companion object {

        val BASE_URL = "https://littlehut.herokuapp.com/api/"
        val SLACK_REDIRECTION_URL = "com.valensas.littlehut:/home"
        val SLACK_AUTH = "auth/slack?url=" + SLACK_REDIRECTION_URL
        var TOKEN = ""
        // var service: APIService = null
        // val BASE_URL = "https://www.ntv.com.tr"
        //  val SLACK_AUTH = ""
        // ogun: {
        // id: 123
        //}


        fun create(): APIService {

            // if(service == null) {

            val httpClient = OkHttpClient().newBuilder()
            val interceptor = Interceptor { chain ->
                val request = chain?.request()?.newBuilder()?.addHeader("Authorization", "Bearer " + TOKEN)?.build();
                chain?.proceed(request)
            }
            httpClient.networkInterceptors().add(interceptor)

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .build()

            //      service =
            //   // }
            return retrofit.create(APIService::class.java)
        }
    }
}