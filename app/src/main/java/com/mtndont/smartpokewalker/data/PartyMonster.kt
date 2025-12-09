package com.mtndont.smartpokewalker.data

import androidx.room.Embedded

data class PartyMonster(
    val slot: Long,
    @Embedded val monster: Monster
)
