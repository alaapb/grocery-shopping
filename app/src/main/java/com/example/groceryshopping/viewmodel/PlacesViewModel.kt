package com.example.groceryshopping.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.groceryshopping.RecipeApplication
import com.example.groceryshopping.repository.placesrepo.PlacesRepository
import com.example.groceryshopping.sources.api.nearby.model.NearbyPlaces
import com.google.android.gms.maps.model.LatLng

class PlacesViewModel(application : Application) : AndroidViewModel(application) {
    private val placesRepository : PlacesRepository =
        (application as RecipeApplication).placesRepository

    fun getNearbyPlaces(location : LatLng) : LiveData<NearbyPlaces> {
        return placesRepository.getNearbyPlaces(location)
    }
}