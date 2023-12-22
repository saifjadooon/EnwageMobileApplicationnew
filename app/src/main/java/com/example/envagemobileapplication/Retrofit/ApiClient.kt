package com.ezshifa.aihealthcare.network

import android.content.Context
import com.example.envagemobileapplication.Utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Suppress("UNUSED_PARAMETER")
class ApiClient {

    companion object {
        private var retrofit: Retrofit? = null

        fun getClient(context: Context, baseUrl: String): Retrofit {
            if (retrofit == null) {
                val logging = HttpLoggingInterceptor()
                // set your desired log level
                logging.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()

                        // Request customization: add request headers
                        val requestBuilder = original.newBuilder()
                            .header("Authorization", "Bearer ${Constants.token}")

                        val request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .addInterceptor(logging)

                    .build()


                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit!!
        }
    }
}
