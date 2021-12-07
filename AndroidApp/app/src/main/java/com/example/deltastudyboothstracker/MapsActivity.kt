package com.example.deltastudyboothstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.deltastudyboothstracker.databinding.ActivityMapsBinding
import com.google.firebase.firestore.FirebaseFirestore

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var firebaseDB: FirebaseFirestore = FirebaseFirestore.getInstance();
    private var markerList: HashMap<Int,MarkerOptions> = HashMap()
    private var roomCordinates: HashMap<Int, LatLng> = HashMap()
    companion object{
        private const val TAG = "MapsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Save roomCordinates
        roomCordinates[0] = LatLng(59.1, 24.1)
        roomCordinates[1] = LatLng(59.2, 24.1)
        roomCordinates[2] = LatLng(59.3, 24.1)
        roomCordinates[3] = LatLng(59.4, 24.1)
        roomCordinates[4] = LatLng(59.5, 24.1)
        roomCordinates[5] = LatLng(59.6, 24.1)
        roomCordinates[6] = LatLng(59.7, 24.1)
        roomCordinates[7] = LatLng(59.8, 24.1)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val docRef = firebaseDB.collection("rooms")
        docRef.addSnapshotListener { snapshot, e ->
            snapshot?.forEach {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (it != null && it.exists()) {
                    // Set changed data to marker
                        if(markerList.containsKey(it.id.toInt())) {
                            var marker = markerList[it.id.toInt()]
                            var title = it.data["occupied"].toString()
                            marker?.snippet("AA")
                        }
                    Log.d(TAG, "it: ${it.id} .Current data: ${it.data}")
                } else {
                    Log.d(TAG, "Current data: null")
                }
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(TAG, "Map loaded")
        mMap = googleMap
        // Add all markers to map
        for (latLng in roomCordinates.entries){
            var marker = MarkerOptions().position(latLng.value).title("Room nr ${latLng.key}")
            mMap.addMarker(marker)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng.value))
            markerList.put(latLng.key,marker)
        }
    }
}