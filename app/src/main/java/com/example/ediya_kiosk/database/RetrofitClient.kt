package com.example.ediya_kiosk

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


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
data class LoginData(
    val message: String,
    val success : Boolean
    )

data class UserInfo (
    @SerializedName("id") val userId: String?,
    @SerializedName("pw") val userPw: String?,
    @SerializedName("name") val userName: String?,
    @SerializedName("contact") val userContact: String?,
)

data class CategoryData(
    val message: String,
    val success : Boolean,
    @SerializedName("data") val data: ArrayList<CategoryName>,
)

data class CategoryName(
    @SerializedName("category_name") val categoryName: String?
)

data class OrderInfo(
    @SerializedName("id") val userId: String,
    @SerializedName("order_list") val orderList: ArrayList<MenuInfo>,
    @SerializedName("total_price") val totalPrice: String
)

data class MenuInfo(
    @SerializedName("name") val menuName: String,
    @SerializedName("count") val menuCount: Int,
    @SerializedName("sum_price") val menuSumPrice: Int
)

// Api로 요청을 보내는 함수
interface LoginApi {
    @GET("/account/login")
    fun getLogin(
        @Query("id") id: String,
        @Query("pw") pw: String
    ) : Call<LoginData>
}

interface RegisterApi {
    @POST("/account")
    fun postNewUser(
        @Body userData : UserInfo
        ) :Call<UserInfo>
}

interface OverlapApi {
    @GET("/account/overlap")
    fun getOverlapId(
        @Query("id") id: String
    ) : Call<LoginData>
}

interface CategoryApi {
    @GET("/category")
    fun getCategory(
        @Query("lang") lang : String
    ) : Call<CategoryData>
}

interface OrderApi {
    @POST("/order")
    fun postOrder(
        @Body orderData : OrderInfo
    ) : Call<OrderInfo>
}


