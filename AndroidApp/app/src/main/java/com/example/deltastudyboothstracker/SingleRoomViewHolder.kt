package com.example.deltastudyboothstracker

import android.graphics.Color
import android.graphics.Color.red
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SingleRoomViewHolder (private val singleRoomItem: View): RecyclerView.ViewHolder(singleRoomItem) {
    fun bind(item: SingleRoom){
        singleRoomItem.findViewById<TextView>(R.id.room_id_textview).text = "Room " + item.id.toString()
        val textViewOccupied = singleRoomItem.findViewById<TextView>(R.id.room_occupied_textview)
        if(item.occupied){
            textViewOccupied.text = "Occupied"
            textViewOccupied.setBackgroundColor(Color.rgb(153, 0,0))
        } else {
            textViewOccupied.text = "Free"
            textViewOccupied.setBackgroundColor(Color.rgb(0, 150,0))
        }
        singleRoomItem.findViewById<TextView>(R.id.room_timestamp_textView).text = "Timestamp: ${item.timeStamp.toString()}"
    }
}