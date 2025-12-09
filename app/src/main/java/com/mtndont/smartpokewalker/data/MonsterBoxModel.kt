package com.mtndont.smartpokewalker.data

data class MonsterBoxModel(
    val boxId: Int,
    val boxSlot: Int,
    val monsterId: Long
) {
    companion object {
        const val MAX_BOX_SIZE = 30
        const val MAX_NUM_OF_BOXES = 32
    }
}
