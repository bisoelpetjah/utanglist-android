package com.anu.utanglist.utils

import com.anu.utanglist.models.Token
import com.anu.utanglist.models.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by irvan on 2/16/16.
 */
object WebServiceHelper: Interceptor {

    private final val BASE_URL: String = "http://192.168.0.33:1337"
//    private final val BASE_URL: String = "http://utanglist.mybluemix.net"
    private final val API_DATE_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    var service: Service? = null
        private set

    var accessToken: String? = null

    fun init() {
        val converter = GsonConverterFactory.create()

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
                .addInterceptor(this)
                .addInterceptor(logger)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(converter)
                .client(httpClient)
                .build()

        service = retrofit.create(Service::class.java)
    }

    override fun intercept(chain: Interceptor.Chain?): Response? {
        return chain?.proceed(chain!!.request().newBuilder().header("Authorization", accessToken).build())
    }

    interface Service {

        @FormUrlEncoded
        @POST("user/login")
        fun login(@Field("access_token") facebookToken: String?): Call<Token>

        @GET("user/me")
        fun getCurrentUser(): Call<User>
    }
}