package com.example.deltastudyboothstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.deltastudyboothstracker.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var firebaseDB: FirebaseFirestore = FirebaseFirestore.getInstance();
    private var markerList: HashMap<Int,Marker> = HashMap()
    private var roomCordinates: HashMap<Int, LatLng> = HashMap()
    lateinit var roomListView: RecyclerView
    lateinit var roomAdapter: SingleRoomAdapter
    private val roomList = mutableListOf<SingleRoom>()

    companion object{
        private const val TAG = "MapsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.action_bar_title)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        roomAdapter = SingleRoomAdapter(roomList)
        roomListView = findViewById(R.id.recycler_view_rooms)
        roomListView.adapter = roomAdapter
        roomListView.layoutManager = LinearLayoutManager(applicationContext)

        // Save roomCordinates
        roomCordinates[0] = LatLng(58.3853, 26.72525)
        roomCordinates[1] = LatLng(58.385338, 26.72535)
        roomCordinates[2] = LatLng(58.38527, 26.72532)
        roomCordinates[3] = LatLng(58.38523, 26.72543)
        roomCordinates[4] = LatLng(58.38516, 26.72540)
        roomCordinates[5] = LatLng(58.38510, 26.72548)
        roomCordinates[6] = LatLng(58.38513, 26.72560)
        roomCordinates[7] = LatLng(58.38506, 26.72557)
        roomCordinates[8] = LatLng(58.38501, 26.72569)
        roomCordinates[9]  = LatLng(58.38493, 26.72565)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val docRef = firebaseDB.collection("rooms")
        docRef.addSnapshotListener { snapshot, e ->
            roomList.clear()
            roomAdapter.notifyDataSetChanged()
            snapshot?.forEach {
                Log.i(TAG, it.toString())
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (it != null && it.exists()) {
                    // Set changed data to marker
                    if(markerList.containsKey(it.id.toInt())) {
                        var occupied = it.data["occupied"].toString().toBoolean()
                        var id = it.id.toInt()
                        val timestamp = it["timestamp"] as com.google.firebase.Timestamp
                        val date = timestamp.toDate()
                        val singleRoom = SingleRoom(id, date, occupied)
                        roomList.add(singleRoom)
                        roomAdapter.notifyDataSetChanged()
                        markerList[it.id.toInt()]?.setIcon(
                            if(occupied) BitmapDescriptorFactory.fromResource(R.drawable.occupied) else BitmapDescriptorFactory.fromResource(R.drawable.free)
                        )
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

        val delta = LatLng(58.3852, 26.725450)//58.3854, 26.7247)
        val zoomLevel = 18.5f //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(delta, zoomLevel))

        val groundOverlayOptions = GroundOverlayOptions()

        groundOverlayOptions.image(BitmapDescriptorFactory.fromResource(R.drawable.delta_rooms))
        groundOverlayOptions.position(LatLng(58.385432, 26.725514), 120f, 150f)
        mMap.addGroundOverlay(groundOverlayOptions)

        // Add all markers to map
        for (latLng in roomCordinates.entries){
            var marker = mMap.addMarker(MarkerOptions().position(latLng.value).title("Room nr ${latLng.key + 1}"))
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.occupied))
            markerList.put(latLng.key,marker)
        }
    }
}