package org.caojun.library.net

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class RetrofitManager(private val BASE_URL: String,
                      private val authorization: String,
                      private val timeout: Int = 30) {
    private var mOkHttpClient: OkHttpClient.Builder? = null
    private var mRetrofit: Retrofit? = null
    private val apiMap: MutableMap<String, Any?> = ConcurrentHashMap()
    private fun init() {

//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "Bearer $authorization")
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
//            .addInterceptor(loggingInterceptor)
        val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient!!.build())
            .baseUrl(BASE_URL)
        mRetrofit = builder.build()
    }

    fun <T> getServiceApi(clazz: Class<T>): T {
        var obj = apiMap[clazz.toString()] as T
        if (obj != null) {
            return obj
        }
        synchronized(apiMap) {
            obj = apiMap[clazz.toString()] as T
            if (obj == null) {
                obj = mRetrofit!!.create(clazz)
                apiMap[clazz.toString()] = obj
            }
        }
        return obj
    }

    companion object {
        private const val DEFAULT_TIMEOUT = 30
        private val hashMap = HashMap<String, RetrofitManager>()

        fun getInstance(url: String, authorization: String, timeout: Int = DEFAULT_TIMEOUT): RetrofitManager {
            val key = "$url|$authorization"
            if (hashMap.containsKey(key) && hashMap[key] != null) {
                return hashMap[key]!!
            }
            val rm = RetrofitManager(url, authorization, timeout)
            rm.init()
            hashMap[key] = rm
            return rm
        }
    }
}