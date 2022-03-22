package com.example.ediya_kiosk

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


object RetrofitClient {

    fun initRetrofit() : Retrofit {

        val url = "http://52.79.157.214:3000" //서버 주소
        val gson = Gson()                   // 서버와 주고 받을 데이터 형식
        val clientBuilder = OkHttpClient.Builder().build()

        val connection = Retrofit.Builder()
            .baseUrl(url)
            .client(clientBuilder)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return connection
    }
}

//받아온 데이터를 저장할 개체
data class LoginData(val message: String, val success : Boolean)

//API로 요청을 보내는 함수
interface LoginApi {
    @GET("/account/login")
    fun getLogin(@Query("id") id: String, @Query("pw") pw: String) : Call<LoginData>
}


