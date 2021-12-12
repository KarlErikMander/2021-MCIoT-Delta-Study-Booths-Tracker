package com.example.deltastudyboothstracker

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.GradientDrawable
import java.text.SimpleDateFormat
import java.util.*


class SingleRoomViewHolder (private val singleRoomItem: View): RecyclerView.ViewHolder(singleRoomItem) {
    fun bind(item: SingleRoom){
        singleRoomItem.findViewById<TextView>(R.id.room_id_textview).text = "Room " + (item.id + 1).toString()
        val group = singleRoomItem.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.parent)
        val textViewOccupied = singleRoomItem.findViewById<TextView>(R.id.room_occupied_textview)

        if(item.occupied){
            textViewOccupied.text = "Occupied"
            group.setBackgroundColor(Color.rgb(255, 132, 105))
        } else {
            textViewOccupied.text = "Free"
            group.setBackgroundColor(Color.rgb(115, 255, 77))
        }
        val dateFormatter = SimpleDateFormat("dd/MM/yy hh:mm", Locale.getDefault())

        singleRoomItem.findViewById<TextView>(R.id.room_timestamp_textView).text = "Timestamp: ${dateFormatter.format(item.date)}"
    }
}