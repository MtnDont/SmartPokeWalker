package com.mtndont.smartpokewalker.data

fun MonsterModel.toLocal() = Monster(
    id = id,
    dexId = dexId,
    name = name,
    experience = experience,
    sex = sex,
    form = form
)

fun List<MonsterModel>.toLocal() = map(MonsterModel::toLocal)

fun Monster.toExternal() = MonsterModel(
    id = id,
    dexId = dexId,
    name = name,
    experience = experience,
    sex = sex,
    form = form
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