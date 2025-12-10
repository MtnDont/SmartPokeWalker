package com.mtndont.smartpokewalker.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material3.CircularProgressIndicator
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.ProgressIndicatorDefaults
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.data.MonsterDefinitions
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.util.AnimatedDrawableUtil

@Composable
fun TrainerViewNavigatorApp(
    viewModel: TrainerDetailsViewModel = hiltViewModel(),
    boxOnClick: () -> Unit,
    partyOnClick: () -> Unit
) {
    val overlayState by viewModel.overlayState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(pageCount = {
        2
    })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Crossfade(
            targetState = overlayState
        ) {
            when (it) {
                AppOverlayState.RouteExploration -> RouteExplorationScreen(durationMillis = 10_000)
                AppOverlayState.WalkPager -> {
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
                                    partyOnClick = partyOnClick
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
    val partyState by viewModel.partyUiState.collectAsStateWithLifecycle()
    val totalWatts by viewModel.totalWatts.collectAsStateWithLifecycle()

    Crossfade(targetState = partyState) { partyUiState ->
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
                party = partyList ?: listOf()
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun WalkPagerScreen(
    totalWatts: Long,
    party: List<MonsterModel>
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
                sex = party[page].sex
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
fun StarterMonsterScreen(
    starterMonsters: List<MonsterModel>,
    confirmOnClick: (MonsterModel) -> Unit
) {
    var selectedMonsterIdx by remember { mutableIntStateOf(-1) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.background_gray))
            .padding(bottom = 30.dp)
    ) {
        repeat(starterMonsters.size) {
            val selectedButtonColor by animateColorAsState(
                targetValue = if (selectedMonsterIdx != it) {
                    colorResource(R.color.light_gray)
                } else {
                    colorResource(R.color.dark_gray)
                }
            )

            val painter = if (LocalInspectionMode.current) {
                BitmapPainter(
                    image = ImageBitmap.imageResource(
                        starterMonsters[it].getFormResId()
                    ),
                    filterQuality = FilterQuality.None
                )
            } else {
                rememberDrawablePainter(
                    drawable = AnimatedDrawableUtil.createAnimatedImageDrawableFromImageDecoder(
                        LocalContext.current.applicationContext,
                        starterMonsters[it].getFormResId()
                    )
                )
            }

            Button(
                onClick = {
                    selectedMonsterIdx = it
                },
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.background_gray)
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.6f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.aspectRatio(
                            painter.intrinsicSize.width / painter.intrinsicSize.height
                        )
                    )
                    Text(
                        text = starterMonsters[it].name,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(
                            Font(R.font.pixelfont)
                        ),
                        color = colorResource(R.color.black),
                        modifier = Modifier.basicMarquee()
                    )
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(selectedButtonColor)
                    )
                }
            }
        }
    }

    TimeText(
        timeTextStyle = TimeTextDefaults.timeTextStyle(
            color = colorResource(R.color.black),
            fontSize = 24.sp
        ) + TextStyle(
            fontFamily = FontFamily(
                Font(R.font.pixelfont)
            )
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        EdgeButton(
            onClick = {
                confirmOnClick.invoke(
                    starterMonsters[selectedMonsterIdx]
                )
            },
            enabled = selectedMonsterIdx != -1,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.dark_gray)
            ),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(R.string.confirm),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(R.font.pixelfont)
                ),
                color = colorResource(R.color.background_gray),
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
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
@Composable
fun WalkPagerScreenSmallPreview() {
    WalkPagerScreen(
        totalWatts = 88888888L,
        party = listOf(
            MonsterModel(id = 1, name = "Android", experience = 99000L, sex = 1),
            MonsterModel(id = 0),
            MonsterModel(id = 0),
            MonsterModel(id = 0),
            MonsterModel(id = 0),
            MonsterModel(id = 0)
        )
    )
}

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
        )
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun StarterMonsterScreenSmallPreview() {
    StarterMonsterScreen(
        starterMonsters = listOf(
            MonsterModel.getRandomMonster(MonsterDefinitions.entries[0].id),
            MonsterModel.getRandomMonster(MonsterDefinitions.entries[3].id),
            MonsterModel.getRandomMonster(MonsterDefinitions.entries[6].id)
        ),
        confirmOnClick = {}
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun StarterMonsterScreenLargePreview() {
    StarterMonsterScreen(
        starterMonsters = listOf(
            MonsterModel.getRandomMonster(MonsterDefinitions.entries[0].id),
            MonsterModel.getRandomMonster(MonsterDefinitions.entries[3].id),
            MonsterModel.getRandomMonster(MonsterDefinitions.entries[6].id)
        ),
        confirmOnClick = {}
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun LoadingScreenLargePreview() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
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