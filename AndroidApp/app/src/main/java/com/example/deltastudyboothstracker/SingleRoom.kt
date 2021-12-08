package com.example.deltastudyboothstracker

import java.sql.Timestamp

data class SingleRoom (
        val id: Int,
        val timeStamp: Timestamp,
        val occupied: Boolean
        )
