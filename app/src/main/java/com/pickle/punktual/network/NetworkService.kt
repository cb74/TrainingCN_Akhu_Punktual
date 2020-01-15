package com.pickle.punktual.network

import com.pickle.punktual.user.User
import com.pickle.punktual.user.UserRegister
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


class APIService {
    companion object {
        val client = OkHttpClient()
        val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}

interface UserService {

    @GET("api/user/find/{name}")
    suspend fun loginUser(@Path("name") userName: String): Response<User>


    @Headers("Content-Type:application/json")
    @POST("/api/user/register")
    suspend fun registerUser(@Body user: UserRegister): Response<User>?
}

/**
 * Main entry point for network access. Call like `DevByteNetwork.devbytes.getPlaylist()`
 */
object PunktualNetworkService {
    // Configure retrofit to parse JSON and use coroutines
    private const val host = "localhost"
    private const val port = 8080

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://${host}:${port}/")
        .addConverterFactory(MoshiConverterFactory.create(APIService.moshi))
        .build()

    val user: UserService = retrofit.create(UserService::class.java)
}