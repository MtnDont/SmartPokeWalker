package com.mtndont.smartpokewalker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "items"
)
data class Item(
    @PrimaryKey
    val id: Int,
    val itemCount: Int
)