package com.mtndont.smartpokewalker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monsters")
data class Monster(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val dexId: Int,
    val name: String,
    val experience: Long,
    val sex: Int,
    val form: Int,
    val traded: Boolean
)