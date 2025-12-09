package com.mtndont.smartpokewalker.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mtndont.smartpokewalker.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
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
import com.google.android.horologist.compose.layout.ColumnItemType
import com.google.android.horologist.compose.layout.rememberResponsiveColumnPadding
import com.mtndont.smartpokewalker.data.MonsterBoxModel
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.data.PartyModel
import com.mtndont.smartpokewalker.data.PartyMonsterModel

enum class MonsterListAction(val labelResId: Int) {
    Release(R.string.release),
    MoveToParty(R.string.move_to_party),
    MoveToBox(R.string.move_to_box)
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

        composable("box/{boxId}") { backStackEntry ->
            val boxId = backStackEntry.arguments?.getString("boxId")?.toInt() ?: 0

            val boxList by viewModel.getMonstersInBox(boxId).collectAsStateWithLifecycle(
                initialValue = listOf()
            )

            MonsterListScreen(
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

            MonsterActionScreen(
                monster = monster,
                actionOnHit = { action ->
                    when(action) {
                        MonsterListAction.MoveToParty -> {
                            monster?.let {
                                navController.navigate("moveToParty/${it.id}")
                            }
                        }
                        MonsterListAction.MoveToBox -> {
                            monster?.let {
                                navController.navigate("moveToBox/${it.id}")
                            }
                        }
                        MonsterListAction.Release -> {
                            monster?.let {
                                navController.navigate("monster/${it.id}/${it.name}/confirmRelease")
                            }
                        }
                        else -> {
                            Log.d("TrainerNavigation", action.name)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }

        composable("moveToParty/{monsterId}") { backStackEntry ->
            val monsterId = backStackEntry.arguments?.getString("monsterId")?.toLong() ?: 0

            val partyList by viewModel.getMonstersInParty().collectAsStateWithLifecycle(
                initialValue = listOf()
            )

            MoveToPartyScreen(
                partyMonsters = partyList,
                partySlotOnHit = { slot ->
                    viewModel.moveMonsterToParty(monsterId, slot)
                    navController.popBackStack("monster/${monsterId}", true)
                }
            )
        }

        composable("moveToBox/{monsterId}") { backStackEntry ->
            val monsterId = backStackEntry.arguments?.getString("monsterId")?.toLong() ?: 0

            MoveToBoxScreen(
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
            items(items) { boxId ->
                Button(
                    label = {
                        Text(
                            text = "Box $boxId",
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
    actionOnHit: (MonsterListAction) -> Unit
) {
    monster?.let {
        val actions = listOf(
            //"Summary",
            MonsterListAction.MoveToParty,
            MonsterListAction.MoveToBox,
            MonsterListAction.Release,
            //"Rename"
        )

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
                items(actions) { action ->
                    Button(
                        label = {
                            Text(
                                text = stringResource(action.labelResId),
                                color = colorResource(R.color.light_gray),
                                fontSize = 24.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.pixelfont)
                                )
                            )
                        },
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
fun MoveToPartyScreen(
    partyMonsters: List<PartyMonsterModel>,
    partySlotOnHit: (Int) -> Unit
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
            items(PartyModel.MAX_PARTY_SIZE) { idx ->
                Button(
                    label = {
                        Text(
                            text = stringResource(R.string.slot_num, idx+1),
                            color = colorResource(R.color.light_gray),
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
                                color = colorResource(R.color.light_gray),
                                fontSize = 20.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.pixelfont)
                                )
                            )
                        }
                    },
                    onClick = {
                        partySlotOnHit.invoke(idx+1)
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
fun MoveToBoxScreen(
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
    val listState = rememberTransformingLazyColumnState()

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
            contentPadding = contentPadding,
            modifier = Modifier
                .background(colorResource(R.color.background_gray))
        ) {
            item {
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
            }

            // Row of two icon buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    // ❌ Cancel Button
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
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cancel Release",
                            tint = colorResource(R.color.background_gray),
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // ✔ Confirm Button
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
                            imageVector = Icons.Default.Check,
                            contentDescription = "Confirm Release",
                            tint = colorResource(R.color.background_gray),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}