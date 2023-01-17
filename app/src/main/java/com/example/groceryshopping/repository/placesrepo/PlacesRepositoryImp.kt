package com.example.groceryshopping.repository.placesrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groceryshopping.sources.api.nearby.NearbyAPIService
import com.example.groceryshopping.sources.api.nearby.model.NearbyPlaces
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlacesRepositoryImp(
    private val nearbyAPIService: NearbyAPIService,
) : PlacesRepository {

    private val apiKey = "AIzaSyA6U3ry8rtL-IImrRnj5c6KKA-exhFTirE"

    override fun getNearbyPlaces(location: LatLng) : LiveData<NearbyPlaces> {
        val result = MutableLiveData<NearbyPlaces>()

        nearbyAPIService.getNearbyPlaces(location.latitude.toString() + "," + location.longitude.toString(), "stores","grocery", 1500, apiKey).enqueue(object :
            Callback<NearbyPlaces> {

                override fun onFailure(call: Call<NearbyPlaces>, t: Throwable) {
                    result.postValue(null)
                }

                override fun onResponse(call: Call<NearbyPlaces>, response: Response<NearbyPlaces>) {
                    if(response.isSuccessful) {
                        result.postValue(response.body())
                    }else{
                        result.postValue(null)
                    }
                }
            })

        return result
    }
}