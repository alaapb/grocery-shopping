package com.example.groceryshopping.sources.api.nearby

import com.example.groceryshopping.sources.api.nearby.model.NearbyPlaces
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NearbyAPIService {

    @GET("json")
    fun getNearbyPlaces(@Query("location") location : String,
                        @Query("type") type : String,
                        @Query("keyword") key : String,
                        @Query("radius") radius : Int,
                        @Query("key") api_key : String) : Call<NearbyPlaces>
}