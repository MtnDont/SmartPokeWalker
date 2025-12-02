package com.mtndont.smartpokewalker.data

data class PartyModel(
    val slot: Int = 0,
    val monsterId: Long = -1
) {
    companion object {
        const val MAX_PARTY_SIZE = 6
    }
}