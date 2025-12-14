package com.mtndont.smartpokewalker.data

fun MonsterModel.toLocal() = Monster(
    id = id,
    dexId = dexId,
    name = name,
    experience = experience,
    sex = sex,
    form = form,
    traded = traded
)

fun List<MonsterModel>.toLocal() = map(MonsterModel::toLocal)

fun Monster.toExternal() = MonsterModel(
    id = id,
    dexId = dexId,
    name = name,
    experience = experience,
    sex = sex,
    form = form,
    traded = traded
)

fun List<Monster>.toExternal() = map(Monster::toExternal)

fun Party.toExternal() = PartyModel(
    slot = slot,
    monsterId = monsterId
)

fun PartyModel.toLocal() = Party(
    slot = slot,
    monsterId = monsterId
)

fun PartyMonster.toExternal() = PartyMonsterModel(
    slot = slot,
    monster = monster.toExternal()
)

@JvmName("partyMonsterToExternal")
fun List<PartyMonster>.toExternal() = map(PartyMonster::toExternal)

fun Item.toExternal() = ItemModel(
    id = id,
    itemCount = itemCount
)

fun ItemModel.toInternal() = Item(
    id = id,
    itemCount = itemCount
)

@JvmName("itemListToExternal")
fun List<Item>.toExternal() = map(Item::toExternal)