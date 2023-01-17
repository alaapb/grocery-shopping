package com.example.groceryshopping

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.example.groceryshopping.databinding.ActivityMapsBinding
import com.example.groceryshopping.viewmodel.PlacesViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    val LAT_LNG_SEPERATOR : String = "%2C"
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var placesViewModel : PlacesViewModel

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    private var locationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        placesViewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // get current location and then pin that

        getLocationPermissions()

        getDeviceLocation()

    }

    private fun getLocationPermissions() {
        if(ContextCompat.checkSelfPermission(this.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION) ==  PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        if(locationPermissionGranted) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { task ->
                if(task != null) {
                    val latlng = LatLng(task.latitude, task.longitude)
                    mMap.isMyLocationEnabled = true
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12.0f))

                    placesViewModel.getNearbyPlaces(latlng).observe(this,
                        {
                            for (places in it.results) {
                                val templatlng =
                                    LatLng(places.geometry.location.lat, places.geometry.location.lng)
                                mMap.addMarker(
                                    MarkerOptions().position(templatlng)
                                        .title(places.name)
                                )
                            }
                    })
                }else{
                    Toast.makeText(this, "Please Check Location Settings", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }

    override fun onMarkerClick(p0: Marker) = false
}
