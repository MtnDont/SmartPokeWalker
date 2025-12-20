package com.mtndont.smartpokewalker.data

data class MonsterDefinition(
    val id: Int,
    val name: String,
    val genderRate: Int = -1,
    val hasGenderDifferences: Boolean = false,
    val firstInEvolutionChain: Boolean = true,
    val formResIds: List<Int>,
    val evolutions: Map<Int, List<EvolutionDefinition>?>? = null
)