package com.anu.utanglist.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit.RequestInterceptor
import retrofit.RestAdapter
import retrofit.converter.GsonConverter

/**
 * Created by irvan on 2/16/16.
 */
object WebServiceHelper: RequestInterceptor {

    private final val API_ENDPOINT: String = "http://utanglist.mybluemix.net"
    private final val API_DATE_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    var services: Services? = null
        private set

    var accessToken: String? = null

    fun init() {
        val gson: Gson = GsonBuilder()
                .setDateFormat(API_DATE_FORMAT)
                .excludeFieldsWithoutExposeAnnotation()
                .create()

        val adapter: RestAdapter = RestAdapter.Builder()
                .setEndpoint(API_ENDPOINT)
                .setRequestInterceptor(this)
                .setConverter(GsonConverter(gson))
                .build()

        services = adapter.create(Services::class.java)
    }

    override fun intercept(request: RequestInterceptor.RequestFacade?) {
        request?.addHeader("Authorization", "Bearer " + accessToken)
    }

    interface Services {}
}