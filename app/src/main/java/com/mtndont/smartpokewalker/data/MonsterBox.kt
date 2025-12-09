package com.mtndont.smartpokewalker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "box",
    primaryKeys = [
        "boxId",
        "boxSlot"
    ],
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
data class MonsterBox(
    val boxId: Int,
    val boxSlot: Int,
    val monsterId: Long
)