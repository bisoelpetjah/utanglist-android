package com.anu.utanglist.utils

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.RealmObject
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
                .setExclusionStrategies(object: ExclusionStrategy {
                    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                        return false
                    }

                    override fun shouldSkipField(f: FieldAttributes?): Boolean {
                        return f!!.declaringClass.equals(RealmObject::class.java)
                    }
                })
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
        request?.addHeader("Authorization", "Bearer" + accessToken)
    }

    interface Services {}
}