package com.example.arkanoid_game.ui.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.arkanoid_game.AppHelper
import com.example.arkanoid_game.R
import com.example.arkanoid_game.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import io.nlopez.smartlocation.SmartLocation


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: MapsViewModel
    private lateinit var binding : ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_maps
        )
        viewModel = ViewModelProviders.of(this,
            AppHelper.viewModelFactory {
                MapsViewModel(this)
            })
            .get(MapsViewModel::class.java)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.playersResults.observe(this, Observer { result ->
            if (result != null) {
                addMarkers()
            } else
                return@Observer
        })
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.maps_theme))

        SmartLocation.with(this).location().start { location ->
            val latLng =
                LatLng(
                    location.latitude,
                    location.longitude
                )
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17f)
            mMap.animateCamera(cameraUpdate)
        }

    }

    private fun addMarkers() {
        viewModel.playersResults.value?.forEach { item ->
            if (item.result?.latitude != null && item.result?.longitude != null) {
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(item.result.latitude, item.result.longitude))
                        .title(
                            item.name + " - " + getString(R.string.game_score, item.result.score)
                        )
                )
            }
        }
    }
}