package com.mtndont.smartpokewalker.data

data class MonsterModel(
    val id: Long,
    val dexId: Int = 0,
    val name: String = "",
    val experience: Long = 0L,
    val sex: Int = 0,
    val form: Int = 0,
)
