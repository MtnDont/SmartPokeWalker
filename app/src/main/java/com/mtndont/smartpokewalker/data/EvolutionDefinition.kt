package com.mtndont.smartpokewalker.data

data class EvolutionDefinition(
    val sex: Int? = null,
    val minLevel: Int? = null,
    val item: String? = null,
    val timeOfDay: String? = null,
    val requiredMember: Int? = null,
    val otherAction: String? = null
) {
    fun isHidden(): Boolean {
        return when (otherAction) {
            EvolutionMode.SHED.label -> true
            else -> false
        }
    }
}