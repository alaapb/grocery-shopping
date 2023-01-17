package com.example.groceryshopping.repository.placesrepo

import androidx.lifecycle.LiveData
import com.example.groceryshopping.sources.api.nearby.model.NearbyPlaces
import com.google.android.gms.maps.model.LatLng

interface PlacesRepository {

    fun getNearbyPlaces(location : LatLng) : LiveData<NearbyPlaces>
}