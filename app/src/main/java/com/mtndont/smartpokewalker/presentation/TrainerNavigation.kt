package com.mtndont.smartpokewalker.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mtndont.smartpokewalker.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.compose.layout.ColumnItemType
import com.google.android.horologist.compose.layout.rememberResponsiveColumnPadding
import com.mtndont.smartpokewalker.data.ItemDefinitions
import com.mtndont.smartpokewalker.data.ItemModel
import com.mtndont.smartpokewalker.data.MonsterBoxModel
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.data.PartyModel
import com.mtndont.smartpokewalker.data.PartyMonsterModel

enum class MonsterListAction(
    val labelResId: Int,
    val lastInPartyEnabled: Boolean
) {
    Evolve(R.string.evolve, true),
    Release(R.string.release, false),
    MoveToParty(R.string.move_to_party, true),
    MoveToBox(R.string.move_to_box, false)
}

@Composable
fun TrainerNav(
    viewModel: TrainerDetailsViewModel = hiltViewModel()
) {
    val navController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.background(colorResource(R.color.background_gray))
    ) {
        composable("home") {
            TrainerViewNavigatorApp(
                viewModel = viewModel,
                boxOnClick = {
                    navController.navigate("boxList")
                },
                partyOnClick = {
                    navController.navigate("party")
                },
                itemsOnClick = {
                    navController.navigate("items")
                }
            )
        }

        composable("boxList") {
            BoxListScreen(
                onBoxSelected = { boxId ->
                    navController.navigate("box/$boxId")
                }
            )
        }

        composable("party") {
            val partyList by viewModel.getMonstersInParty().collectAsStateWithLifecycle(
                initialValue = listOf()
            )

            PartyListScreen(
                header = stringResource(R.string.party),
                partyMonsters = partyList,
                partySlotOnHit = { _, monster ->
                    monster?.let {
                        navController.navigate("monster/${it.id}")
                    }
                }
            )
        }

        composable("items") {
            val itemList by viewModel.getAllItems().collectAsStateWithLifecycle(
                initialValue = listOf()
            )

            ItemsListScreen(
                items = itemList
            )
        }

        composable("box/{boxId}") { backStackEntry ->
            val boxId = backStackEntry.arguments?.getString("boxId")?.toInt() ?: 0

            val boxList by viewModel.getMonstersInBox(boxId).collectAsStateWithLifecycle(
                initialValue = listOf()
            )

            MonsterListScreen(
                boxId = boxId+1,
                boxMonsters = boxList,
                onMonsterSelected = { monsterId ->
                    navController.navigate("monster/$monsterId")
                }
            )
        }

        composable("monster/{monsterId}") { backStackEntry ->
            val monsterId = backStackEntry.arguments?.getString("monsterId")?.toLong() ?: 0

            val monster by viewModel.getMonsterFromId(monsterId).collectAsStateWithLifecycle(
                null
            )

            val partyList by viewModel.getMonstersInParty().collectAsStateWithLifecycle(
                initialValue = listOf()
            )

            val itemList by viewModel.getItemsAvailable().collectAsStateWithLifecycle(
                initialValue = listOf()
            )

            val isLastInParty by viewModel.isMonsterExclusiveInParty(monsterId).collectAsStateWithLifecycle(
                true
            )

            MonsterActionScreen(
                monster = monster,
                canEvolve = monster?.getAvailableEvolutions(
                    party = partyList.map { (_, monster) -> monster },
                    items = itemList,
                    includeHidden = false
                )?.isNotEmpty() ?: false,
                isLastInParty = isLastInParty,
                actionOnHit = { action ->
                    when(action) {
                        MonsterListAction.Evolve -> {
                            monster?.let {
                                navController.navigate("monster/${it.id}/evolve")
                            }
                        }
                        MonsterListAction.MoveToParty -> {
                            monster?.let {
                                navController.navigate("monster/${it.id}/moveToParty")
                            }
                        }
                        MonsterListAction.MoveToBox -> {
                            monster?.let {
                                navController.navigate("monster/${it.id}/moveToBox")
                            }
                        }
                        MonsterListAction.Release -> {
                            monster?.let {
                                navController.navigate("monster/${it.id}/${it.name}/confirmRelease")
                            }
                        }
                    }
                }
            )
        }

        composable("monster/{monsterId}/evolve") { backStackEntry ->
            val monsterId = backStackEntry.arguments?.getString("monsterId")?.toLong() ?: 0

            val monster = viewModel.getMonsterInstanceFromId(monsterId)
            val partyList = viewModel.getPartyListInstance()
            val itemList = viewModel.getItemsAvailableInstance()

            val evolutionDefinitions = monster.getAvailableEvolutions(
                party = partyList,
                items = itemList,
                includeHidden = true
            )
            val hiddenMonsters = evolutionDefinitions.filter {
                it.first.hidden
            }.map { (_, definition) ->
                monster.evolveToHiddenDefinition(definition)
            }

            SelectEvolveMonsterScreen(
                monsterToEvolve = monster,
                evolvedDefinitions = evolutionDefinitions.filter {
                        !it.first.hidden
                }.map { it.second },
                confirmOnClick = { newMonster ->
                    viewModel.evolveMonsters(newMonster)
                    hiddenMonsters.forEach {
                        viewModel.evolveHiddenMonster(it)
                    }
                    navController.popBackStack("monster/${monsterId}", true)
                }
            )
        }

        composable("monster/{monsterId}/moveToParty") { backStackEntry ->
            val monsterId = backStackEntry.arguments?.getString("monsterId")?.toLong() ?: 0

            val partyList by viewModel.getMonstersInParty().collectAsStateWithLifecycle(
                initialValue = listOf()
            )

            PartyListScreen(
                header = stringResource(R.string.move_to_party),
                disabledSlot = partyList.firstOrNull {
                    it.monster.id == monsterId
                }?.slot,
                partyMonsters = partyList,
                partySlotOnHit = { partySlot, _ ->
                    viewModel.moveMonsterToParty(monsterId, partySlot)
                    navController.popBackStack("monster/${monsterId}", true)
                }
            )
        }

        composable("monster/{monsterId}/moveToBox") { backStackEntry ->
            val monsterId = backStackEntry.arguments?.getString("monsterId")?.toLong() ?: 0

            val boxesFull by viewModel.getFullBoxSlots().collectAsStateWithLifecycle(
                initialValue = listOf()
            )

            MoveToBoxScreen(
                disabledBoxes = boxesFull,
                boxIdOnHit = { boxId ->
                    viewModel.moveMonsterToBox(monsterId, boxId)
                    navController.popBackStack("monster/${monsterId}", true)
                }
            )
        }

        composable("monster/{monsterId}/{monsterName}/confirmRelease") { backStackEntry ->
            val monsterId = backStackEntry.arguments?.getString("monsterId")?.toLong() ?: 0
            val monsterName = backStackEntry.arguments?.getString("monsterName") ?: ""

            ConfirmReleaseScreen(
                monsterName = monsterName,
                onCancel = {
                    navController.popBackStack("monster/${monsterId}", true)
                },
                onConfirm = {
                    viewModel.releaseMonster(monsterId)
                    navController.popBackStack("monster/${monsterId}", true)
                }
            )
        }
    }
}

@Composable
fun BoxListScreen(
    maxBoxes: Int = MonsterBoxModel.MAX_NUM_OF_BOXES,
    onBoxSelected: (Int) -> Unit
) {
    val items = (0 until maxBoxes).toList()

    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()

    ScreenScaffold(
        scrollState = listState,
        contentPadding = rememberResponsiveColumnPadding(
            first = ColumnItemType.ListHeader,
            last = ColumnItemType.IconButton
        ),
        modifier = Modifier.background(colorResource(R.color.background_gray))
    ) { contentPadding ->
        TransformingLazyColumn(
            state = listState,
            contentPadding = contentPadding
        ) {
            item {
                Text(
                    text = "Select a Box",
                    color = colorResource(R.color.black),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    ),
                    textAlign = TextAlign.Center
                )
            }
            items(items) { boxId ->
                Button(
                    label = {
                        Text(
                            text = stringResource(R.string.box_num, boxId+1),
                            color = colorResource(R.color.light_gray),
                            fontSize = 24.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    onClick = { onBoxSelected(boxId) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.black)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec)
                )
            }
        }
    }
}

@Composable
fun MonsterListScreen(
    boxId: Int,
    boxMonsters: List<MonsterModel>,
    onMonsterSelected: (Long) -> Unit
) {
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()

    ScreenScaffold(
        scrollState = listState,
        contentPadding = rememberResponsiveColumnPadding(
            first = ColumnItemType.ListHeader,
            last = ColumnItemType.IconButton
        ),
        modifier = Modifier.background(colorResource(R.color.background_gray))
    ) { contentPadding ->
        TransformingLazyColumn(
            state = listState,
            contentPadding = contentPadding
        ) {
            item {
                Text(
                    text = stringResource(R.string.box_num_monsters, boxId),
                    color = colorResource(R.color.black),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    ),
                    textAlign = TextAlign.Center
                )
            }
            items(boxMonsters) { monster ->
                Button(
                    label = {
                        Text(
                            text = monster.name,
                            color = colorResource(R.color.light_gray),
                            fontSize = 24.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    secondaryLabel = {
                        Text(
                            text = "Lv. ${monster.getLevel()}",
                            color = colorResource(R.color.light_gray),
                            fontSize = 20.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    onClick = { onMonsterSelected(monster.id) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.black)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec)
                )
            }
        }
    }
}

@Composable
fun MonsterActionScreen(
    monster: MonsterModel?,
    canEvolve: Boolean,
    isLastInParty: Boolean,
    actionOnHit: (MonsterListAction) -> Unit
) {
    monster?.let {
        val actions = mutableListOf(
            //"Summary",
            MonsterListAction.MoveToParty,
            MonsterListAction.MoveToBox,
            MonsterListAction.Release,
            //"Rename"
        )

        if (canEvolve) {
            actions.add(0,MonsterListAction.Evolve)
        }

        val listState = rememberTransformingLazyColumnState()
        val transformationSpec = rememberTransformationSpec()

        ScreenScaffold(
            scrollState = listState,
            /*
             * TransformingLazyColumn takes care of the horizontal and vertical
             * padding for the list and handles scrolling.
             * Use workaround from Horologist for padding or wait until fix lands
             */
            contentPadding = rememberResponsiveColumnPadding(
                first = ColumnItemType.ListHeader,
                last = ColumnItemType.IconButton
            ),
            modifier = Modifier.background(colorResource(R.color.background_gray))
        ) { contentPadding ->
            TransformingLazyColumn(
                state = listState,
                contentPadding = contentPadding,
            ) {
                item {
                    Text(
                        text = stringResource(R.string.actions),
                        color = colorResource(R.color.black),
                        fontSize = 24.sp,
                        fontFamily = FontFamily(
                            Font(R.font.pixelfont)
                        ),
                        textAlign = TextAlign.Center
                    )
                }
                items(actions) { action ->
                    val enabled = if (isLastInParty) action.lastInPartyEnabled else true
                    Button(
                        label = {
                            Text(
                                text = stringResource(action.labelResId),
                                color = if (enabled) {
                                    colorResource(R.color.light_gray)
                                } else {
                                    colorResource(R.color.dark_gray)
                                },
                                fontSize = 24.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.pixelfont)
                                )
                            )
                        },
                        enabled = enabled,
                        onClick = {
                            actionOnHit.invoke(action)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.black)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .transformedHeight(this, transformationSpec),
                        transformation = SurfaceTransformation(transformationSpec)
                    )
                }
            }
        }
    }
}

@Composable
fun PartyListScreen(
    header: String,
    disabledSlot: Long? = null,
    partyMonsters: List<PartyMonsterModel>,
    partySlotOnHit: (Int, MonsterModel?) -> Unit
) {
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()

    ScreenScaffold(
        scrollState = listState,
        /*
         * TransformingLazyColumn takes care of the horizontal and vertical
         * padding for the list and handles scrolling.
         * Use workaround from Horologist for padding or wait until fix lands
         */
        contentPadding = rememberResponsiveColumnPadding(
            first = ColumnItemType.ListHeader,
            last = ColumnItemType.IconButton
        ),
        modifier = Modifier.background(colorResource(R.color.background_gray))
    ) { contentPadding ->
        TransformingLazyColumn(
            state = listState,
            contentPadding = contentPadding
        ) {
            item {
                Text(
                    text = header,
                    color = colorResource(R.color.black),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    ),
                    textAlign = TextAlign.Center
                )
            }
            items(PartyModel.MAX_PARTY_SIZE) { idx ->
                Button(
                    label = {
                        Text(
                            text = stringResource(R.string.slot_num, idx+1),
                            color = if (disabledSlot != (idx+1).toLong()) {
                                colorResource(R.color.light_gray)
                            } else {
                                colorResource(R.color.dark_gray)
                            },
                            fontSize = 24.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    secondaryLabel = {
                        partyMonsters.firstOrNull {
                            it.slot == (idx+1).toLong()
                        }?.let {
                            Text(
                                text = it.monster.name,
                                color = if (disabledSlot != (idx+1).toLong()) {
                                    colorResource(R.color.light_gray)
                                } else {
                                    colorResource(R.color.dark_gray)
                                },
                                fontSize = 20.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.pixelfont)
                                )
                            )
                        }
                    },
                    enabled = disabledSlot != (idx+1).toLong(),
                    onClick = {
                        partySlotOnHit.invoke(
                            idx+1,
                            partyMonsters.firstOrNull {
                                it.slot == (idx+1).toLong()
                            }?.monster
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.black)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec)
                )
            }
        }
    }
}

@Composable
fun ItemsListScreen(
    items: List<ItemModel>
) {
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()

    ScreenScaffold(
        scrollState = listState,
        /*
         * TransformingLazyColumn takes care of the horizontal and vertical
         * padding for the list and handles scrolling.
         * Use workaround from Horologist for padding or wait until fix lands
         */
        contentPadding = rememberResponsiveColumnPadding(
            first = ColumnItemType.ListHeader,
            last = ColumnItemType.IconButton
        ),
        modifier = Modifier.background(colorResource(R.color.background_gray))
    ) { contentPadding ->
        TransformingLazyColumn(
            state = listState,
            contentPadding = contentPadding
        ) {
            item {
                Text(
                    text = stringResource(R.string.items),
                    color = colorResource(R.color.black),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    ),
                    textAlign = TextAlign.Center
                )
            }
            items(items) { item ->
                Button(
                    label = {
                        Text(
                            text = item.getItemName(),
                            color = colorResource(R.color.light_gray),
                            fontSize = 24.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    secondaryLabel = {
                        Text(
                            text = "x${item.itemCount}",
                            color = colorResource(R.color.light_gray),
                            fontSize = 24.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.black)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec)
                )
            }
        }
    }
}

@Composable
fun MoveToBoxScreen(
    disabledBoxes: List<Long>,
    boxIdOnHit: (Int) -> Unit
) {
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()

    ScreenScaffold(
        scrollState = listState,
        /*
         * TransformingLazyColumn takes care of the horizontal and vertical
         * padding for the list and handles scrolling.
         * Use workaround from Horologist for padding or wait until fix lands
         */
        contentPadding = rememberResponsiveColumnPadding(
            first = ColumnItemType.ListHeader,
            last = ColumnItemType.IconButton
        ),
        modifier = Modifier.background(colorResource(R.color.background_gray))
    ) { contentPadding ->
        TransformingLazyColumn(
            state = listState,
            contentPadding = contentPadding
        ) {
            item {
                Text(
                    text = stringResource(R.string.move_to_box),
                    color = colorResource(R.color.black),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    ),
                    textAlign = TextAlign.Center
                )
            }
            items(MonsterBoxModel.MAX_NUM_OF_BOXES) { idx ->
                Button(
                    label = {
                        Text(
                            text = stringResource(R.string.box_num, idx+1),
                            color = colorResource(R.color.light_gray),
                            fontSize = 24.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    onClick = {
                        boxIdOnHit.invoke(idx)
                    },
                    enabled = idx.toLong() !in disabledBoxes,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.black)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec)
                )
            }
        }
    }
}

@Composable
fun ConfirmReleaseScreen(
    monsterName: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
    ) {
        Text(
            text = stringResource(R.string.release_monster, monsterName),
            color = colorResource(R.color.black),
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.pixelfont)),
            modifier = Modifier
                .padding(top = 16.dp, bottom = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = onCancel,
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        colorResource(R.color.black),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = "Cancel Release",
                    tint = colorResource(R.color.background_gray),
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(
                onClick = onConfirm,
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        colorResource(R.color.black),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.check),
                    contentDescription = "Confirm Release",
                    tint = colorResource(R.color.background_gray),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun BoxListScreenLargePreview() {
    BoxListScreen(
        onBoxSelected = {}
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun MonsterListScreenLargePreview() {
    MonsterListScreen(
        boxId = 1,
        boxMonsters = listOf(
            MonsterModel(id = 0, dexId = 1, name = "Android"),
            MonsterModel(id = 0, dexId = 1, name = "Android", experience = 2000L),
            MonsterModel(id = 0, dexId = 1, name = "Android", experience = 99000L),
        ),
        onMonsterSelected = {}
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun MonsterActionScreenLargePreview() {
    MonsterActionScreen(
        monster = MonsterModel(id = 0, dexId = 1, name = "Android"),
        canEvolve = true,
        isLastInParty = false,
        actionOnHit = {}
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun PartyListScreenLargePreview() {
    PartyListScreen(
        header = "Move to Party",
        disabledSlot = 3,
        partyMonsters = listOf(
            PartyMonsterModel(slot = 1, monster = MonsterModel(id = 0, dexId = 1, name = "Android")),
            PartyMonsterModel(slot = 2, monster = MonsterModel(id = 0, dexId = 1, name = "Android"))
        ),
        partySlotOnHit = {_,_ ->}
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun ItemsListScreenLargePreview() {
    ItemsListScreen(
        items = listOf(
            ItemModel(id = ItemDefinitions.entries[0].id, itemCount = 1),
            ItemModel(id = ItemDefinitions.entries[1].id, itemCount = 2)
        )
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun MoveToBoxScreenLargePreview() {
    MoveToBoxScreen(
        disabledBoxes = listOf(
            1
        ),
        boxIdOnHit = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun ConfirmReleaseScreenLargePreview() {
    ConfirmReleaseScreen(
        monsterName = "Android",
        onConfirm = {},
        onCancel = {}
    )
}