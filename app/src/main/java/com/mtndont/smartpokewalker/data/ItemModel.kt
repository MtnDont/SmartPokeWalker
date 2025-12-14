package com.mtndont.smartpokewalker.data

data class ItemModel(
    val id: Int,
    val itemCount: Int
) {
    fun getItemName(): String {
        return ItemDefinitions.entries[id].name
    }

    companion object {
        fun getRandomItem(): ItemDefinition {
            return ItemDefinitions.entries.random()
        }
    }
}