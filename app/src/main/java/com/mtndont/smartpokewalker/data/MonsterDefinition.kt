package com.mtndont.smartpokewalker.data

data class MonsterDefinition(
    val id: Int,
    val name: String,
    val hasGenderDifferences: Boolean = false,
    val genderless: Boolean = false,
    val formResIds: List<Int>,
    val evolutions: Map<Int, List<EvolutionDefinition>?>? = null
)