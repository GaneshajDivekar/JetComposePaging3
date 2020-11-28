package net.simplifiedcoding.data

import com.app.model.main.MainModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

   /* @GET("passenger")
    suspend fun getPassengersData(
        @Query("page") page: Int,
        @Query("size") size: Int = 10
    ): JSONResponse*/


    @GET("rest/")
    fun getPassengersData(@Query("method") method: String, @Query("api_key") apiKey: String,
                            @Query("tags") tags: String, @Query("page") page: Int,
                            @Query("per_page") perPage: Int, @Query("format") format: String,
                            @Query("nojsoncallback") noJsonCallback: Long): Deferred<Response<MainModel>>

    companion object {

        //private const val BASE_URL = "https://api.instantwebtools.net/v1/"
        private const val BASE_URL = "https://api.flickr.com/services/"

        operator fun invoke(): MyApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(MyApi::class.java)
    }
}