package com.anu.utanglist.utils

import com.anu.utanglist.models.Debt
import com.anu.utanglist.models.Payment
import com.anu.utanglist.models.Token
import com.anu.utanglist.models.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by irvan on 2/16/16.
 */
object WebServiceHelper: Interceptor {

    private final val BASE_URL = "http://utanglist.mybluemix.net"
    private final val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    var service: Service? = null
        private set

    var accessToken: String? = null

    fun init() {
        val gson: Gson = GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .excludeFieldsWithoutExposeAnnotation()
                .create()

        val converter = GsonConverterFactory.create(gson)

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
        return chain?.proceed(chain!!.request().newBuilder().header("Authorization", accessToken?: "").build())
    }

    interface Service {

        @FormUrlEncoded
        @POST("user/login")
        fun login(@Field("access_token") facebookToken: String): Call<Token>

        @GET("user/me")
        fun getCurrentUser(): Call<User>

        @GET("user/autocomplete")
        fun getUserSuggestion(@Query("full_name") name: String): Call<List<User>>

        @GET("debtdemand/feed")
        fun getDebtDemandList(): Call<List<Debt>>

        @GET("debtoffer/feed")
        fun getDebtOfferList(): Call<List<Debt>>

        @GET("debtdemand/me")
        fun getCurrentUserDebtDemandList(): Call<List<Debt>>

        @GET("debtoffer/me")
        fun getCurrentUserDebtOfferList(): Call<List<Debt>>

        @GET("debt/borrow")
        fun getCurrentUserOngoingDebtDemandList(): Call<List<Debt>>

        @GET("debt/lend")
        fun getCurrentUserOngoingDebtOfferList(): Call<List<Debt>>

        @GET("debtdemand/{id}")
        fun getDebtDemandById(@Path("id") debtId: String): Call<Debt>

        @GET("debtoffer/{id}")
        fun getDebtOfferById(@Path("id") debtId: String): Call<Debt>

        @FormUrlEncoded
        @POST("debtdemand")
        fun addDebtDemand(@Field("total_debt") amount: Long, @Field("notes") note: String): Call<Debt>

        @FormUrlEncoded
        @POST("debtoffer")
        fun addDebtOffer(@Field("total_debt") amount: Long, @Field("notes") note: String): Call<Debt>

        @PATCH("debtdemand/{id}/request")
        fun debtDemandRequest(@Path("id") id: String): Call<Debt>

        @PATCH("debtoffer/{id}/request")
        fun debtOfferRequest(@Path("id") id: String): Call<Debt>

        @FormUrlEncoded
        @PATCH("debtdemand/{id}/accept")
        fun debtDemandAccept(@Path("id") id: String, @Field("lender_id") lenderId: String): Call<Debt>

        @FormUrlEncoded
        @PATCH("debtoffer/{id}/accept")
        fun debtOfferAccept(@Path("id") id: String, @Field("borrower_id") borrowerId: String): Call<Debt>

        @FormUrlEncoded
        @POST("payment")
        fun addPayment(@Field("debt") debtId: String, @Field("amount") amount: Long): Call<Payment>
    }
}