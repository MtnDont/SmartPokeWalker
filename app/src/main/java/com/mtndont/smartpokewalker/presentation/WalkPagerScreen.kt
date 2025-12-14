package com.mtndont.smartpokewalker.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.CurvedAlignment
import androidx.wear.compose.foundation.CurvedDirection
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.curvedComposable
import androidx.wear.compose.foundation.curvedRow
import androidx.wear.compose.foundation.pager.HorizontalPager
import androidx.wear.compose.foundation.pager.VerticalPager
import androidx.wear.compose.foundation.pager.rememberPagerState
import androidx.wear.compose.foundation.rotary.RotaryScrollableDefaults
import androidx.wear.compose.foundation.rotary.rotaryScrollable
import androidx.wear.compose.material3.CircularProgressIndicator
import androidx.wear.compose.material3.ProgressIndicatorDefaults
import androidx.wear.tooling.preview.devices.WearDevices
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.data.ItemModel
import com.mtndont.smartpokewalker.data.MonsterModel

@Composable
fun TrainerViewNavigatorApp(
    viewModel: TrainerDetailsViewModel = hiltViewModel(),
    boxOnClick: () -> Unit,
    partyOnClick: () -> Unit,
    itemsOnClick: () -> Unit
) {
    val overlayState by viewModel.overlayState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Crossfade(
            targetState = overlayState,
            label = "WalkPagerOverlay"
        ) { targetState ->
            when (targetState) {
                is AppOverlayState.RouteExploration -> RouteExplorationScreen(durationMillis = 10_000)
                is AppOverlayState.WalkPager -> {
                    val pagerState = rememberPagerState(pageCount = {
                        2
                    })

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.background(colorResource(R.color.background_gray))
                    ) { page ->
                        AnimatedVisibility(
                            visible = pagerState.currentPage == page,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            when (page) {
                                0 -> WalkPagerApp()
                                1 -> TrainerDetailsApp(
                                    viewModel = viewModel,
                                    boxOnClick = boxOnClick,
                                    partyOnClick = partyOnClick,
                                    itemsOnClick = itemsOnClick
                                )

                                else -> WalkPagerApp()
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 3.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(pagerState.pageCount) { iteration ->
                            val color =
                                if (pagerState.currentPage == iteration) colorResource(R.color.dark_gray) else colorResource(
                                    R.color.light_gray
                                )
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(color)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WalkPagerApp(
    viewModel: MainScreenAppViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val partyList by viewModel.partyList.collectAsStateWithLifecycle()
    val itemList by viewModel.itemList.collectAsStateWithLifecycle()
    val partyState by viewModel.partyUiState.collectAsStateWithLifecycle()
    val totalWatts by viewModel.totalWatts.collectAsStateWithLifecycle()

    Crossfade(
        targetState = partyState,
        label = "PrimaryScreens"
    ) { partyUiState ->
        when(partyUiState) {
            is WalkUiState.Loading -> LoadingScreen()
            is WalkUiState.StarterSelection -> StarterMonsterScreen(
                starterMonsters = partyUiState.starterMonsters,
                confirmOnClick = { starterMonster ->
                    viewModel.onEvent(WalkEvent.ConfirmStarter(starterMonster))
                }
            )
            is WalkUiState.Walking -> WalkPagerScreen(
                totalWatts = totalWatts,
                party = partyList ?: listOf(),
                items = itemList ?: listOf()
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun WalkPagerScreen(
    totalWatts: Long,
    party: List<MonsterModel>,
    items: List<ItemModel>
) {
    val pagerState = rememberPagerState(pageCount = {
        party.size
    })
    val focusRequester: FocusRequester = remember { FocusRequester() }

    VerticalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .rotaryScrollable(
                behavior = RotaryScrollableDefaults.snapBehavior(
                    pagerState = pagerState
                ),
                focusRequester = focusRequester
            )
            .focusRequester(focusRequester)
            .focusable()
    ) { page ->
        Box {
            MonsterDetailsScreen(
                currentSteps = party[page].experience,
                totalWatts = totalWatts,
                sex = party[page].sex,
                canEvolve = party[page].getAvailableEvolutions(
                    party = party,
                    items = items,
                    includeHidden = false
                ).isNotEmpty()
            )
            MonsterImage(
                monsterResId = party[page].getFormResId(),
                name = party[page].name,
                modifier = Modifier.graphicsLayer()
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    CurvedLayout(
        anchor = 15f,
        modifier = Modifier.padding(2.dp)
    ) {
        curvedRow(
            radialAlignment = CurvedAlignment.Radial.Center,
            angularDirection = CurvedDirection.Angular.Clockwise
        ) {
            repeat(pagerState.pageCount) { iteration ->
                curvedComposable {
                    Box(
                        modifier = Modifier
                            .padding(1.dp)
                            .size(10.dp)
                    ) {
                        Image(
                            painter = BitmapPainter(
                                image = ImageBitmap.imageResource(
                                    if (pagerState.currentPage == iteration) R.raw.ball else R.raw.ball2
                                ),
                                filterQuality = FilterQuality.None
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
    ) {
        CircularProgressIndicator(
            colors = ProgressIndicatorDefaults.colors(
                indicatorColor = colorResource(R.color.dark_gray),
                trackColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxSize(0.4f)
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun WalkPagerScreenLargePreview() {
    WalkPagerScreen(
        totalWatts = 88888888L,
        party = listOf(
            MonsterModel(id = 0, name = "Android", experience = 99000L),
            MonsterModel(id = 0),
            MonsterModel(id = 0),
            MonsterModel(id = 0),
            MonsterModel(id = 0),
            MonsterModel(id = 0)
        ),
        items = listOf()
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun LoadingScreenLargePreview() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
    ) {
        CircularProgressIndicator(
            colors = ProgressIndicatorDefaults.colors(
                indicatorColor = colorResource(R.color.dark_gray),
                trackColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxSize(0.4f)
        )
    }
}