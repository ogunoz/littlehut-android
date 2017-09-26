package com.valensas.littlehut.network

import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
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
    fun postMyAttendance(@Body body: AttendModel): Observable<BaseResponse>

    @GET("bus/status")
    fun getBusStatus(): Observable<BusStatusModel>

    @GET("bus/attendance/me/status")
    fun getMyAttendance(): Observable<MyAttendanceModel>

    companion object {

        private var service: APIService? = null
        var headers = HashMap<String, String>()
        val BASE_URL = "https://littlehut.herokuapp.com/api/"
        val SLACK_REDIRECTION_URL = "com.valensas.littlehut:/home"
        val SLACK_AUTH = "auth/slack?url=" + SLACK_REDIRECTION_URL

        fun get(): APIService {
            return service ?: create()
        }

        private fun create(): APIService {

            val httpClient = OkHttpClient().newBuilder()
            val interceptor = Interceptor { chain ->
                val builder = chain.request()?.newBuilder()
                for (key in headers.keys) {
                    builder?.addHeader(key, headers[key]!!)
                }
                val request = builder?.build()
                chain.proceed(request!!)
            }
            httpClient.networkInterceptors().add(interceptor)

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .build()
            val service = retrofit.create(APIService::class.java)
            this.service = service
            return service
        }

        fun putHeader(key: String, value: String) {
            headers.put(key, value)
            service = null
        }

        fun clearHeaders() {
            headers.clear()
            service = null
        }
    }
}