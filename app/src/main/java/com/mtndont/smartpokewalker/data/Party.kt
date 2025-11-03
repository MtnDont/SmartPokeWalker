package com.mtndont.smartpokewalker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "party",
    foreignKeys = [
        ForeignKey(
            entity = Monster::class,
            parentColumns = ["id"],
            childColumns = ["monsterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("monsterId")]
)
data class Party(
    @PrimaryKey
    val slot: Int,
    val monsterId: Long
)