package com.mtndont.smartpokewalker.data

enum class EvolutionMode(
    val label: String,
    val hidden: Boolean
) {
    LEVEL_UP("lvl", false),
    TRADE("trade", false),
    SHED("shed", true)
}