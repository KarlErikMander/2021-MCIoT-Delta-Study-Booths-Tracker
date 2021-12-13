package com.example.deltastudyboothstracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SingleRoomAdapter (private val SingleRoomList: List<SingleRoom>): RecyclerView.Adapter<SingleRoomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRoomViewHolder {
        // Create single view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_room_list_item, parent, false)
        // Create view holder
        return  SingleRoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: SingleRoomViewHolder, position: Int) {
        holder.bind(SingleRoomList[position])
    }

    override fun getItemCount(): Int {
        return SingleRoomList.size
    }

}