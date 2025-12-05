package com.mtndont.smartpokewalker.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
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
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.data.MonsterDefinitions
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.util.AnimatedDrawableUtil

@Composable
fun TrainerViewNavigatorApp() {
    val pagerState = rememberPagerState(pageCount = {
        2
    })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                    1 -> TrainerDetailsApp()
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
                val color = if (pagerState.currentPage == iteration) colorResource(R.color.dark_gray) else colorResource(R.color.light_gray)
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

@Composable
fun WalkPagerApp(
    viewModel: MainScreenAppViewModel = hiltViewModel()
) {
    val currentSteps by viewModel.currentSteps.collectAsStateWithLifecycle()
    val totalWatts by viewModel.totalWatts.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState
    val party by viewModel.party.collectAsStateWithLifecycle()

    AnimatedVisibility(
        visible = party.isNotEmpty(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        WalkPagerScreen(currentSteps, totalWatts, party)
    }
    AnimatedVisibility(
        visible = party.isEmpty(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        StarterMonsterScreen(
            starterMonsters = listOf(
                MonsterModel.getRandomMonster(MonsterDefinitions.entries[0].id),
                MonsterModel.getRandomMonster(MonsterDefinitions.entries[3].id),
                MonsterModel.getRandomMonster(MonsterDefinitions.entries[6].id)
            ),
            confirmOnClick = { starterMonster ->
                viewModel.addMonsterToParty(starterMonster)
            }
        )
    }
}

@SuppressLint("ResourceType")
@Composable
fun WalkPagerScreen(
    currentSteps: Long,
    totalWatts: Long,
    party: List<MonsterModel>
) {
    val pagerState = rememberPagerState(pageCount = {
        party.size
    })
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val context = LocalContext.current

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
            val currentExperience = party[page].experience
            MonsterDetailsScreen(
                currentSteps = currentExperience,
                totalWatts = totalWatts,
                sex = party[page].sex
            )
            AnimatedVisibility(
                visible = pagerState.currentPage == page,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                /*MonsterImage(
                    when (page + 1) {
                        1 -> context.getRawIdentifier("a131") // R.raw.a131 -> ditto
                        2 -> context.getRawIdentifier("a24") // R.raw.a24 -> pikachu
                        3 -> context.getRawIdentifier("a132") // R.raw.a132 -> eevee
                        4 -> context.getRawIdentifier("a654") // R.raw.a654 -> rotom
                        5 -> context.getRawIdentifier("a663") // R.raw.a663 -> pichu_notch
                        6 -> context.getRawIdentifier("a490") // R.raw.a490 -> darkrai
                        else -> context.getRawIdentifier("a131") // R.raw.a131 -> ditto
                    }
                )*/
                MonsterImage(
                    monsterResId = party[page].getFormResId(),
                    name = party[page].name
                )
            }
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
    val selectedMonsterIdx = remember { mutableIntStateOf(-1) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.background_gray))
            .padding(bottom = 30.dp)
    ) {
        repeat(starterMonsters.size) {
            val selectedButtonColor by animateColorAsState(
                targetValue = if (selectedMonsterIdx.intValue != it) {
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
                    selectedMonsterIdx.intValue = it
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
        val buttonEnabled = remember { mutableStateOf(true) }
        EdgeButton(
            onClick = {
                if (selectedMonsterIdx.intValue != -1) {
                    buttonEnabled.value = false
                    confirmOnClick.invoke(
                        starterMonsters[selectedMonsterIdx.intValue]
                    )
                }
            },
            enabled = buttonEnabled.value,
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

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun WalkPagerScreenSmallPreview() {
    WalkPagerScreen(
        currentSteps = 100000L,
        totalWatts = 88888888L,
        party = listOf(
            MonsterModel(id = 1, experience = 99000L, sex = 1),
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
        currentSteps = 0L,
        totalWatts = 88888888L,
        party = listOf(
            MonsterModel(id = 0, experience = 99000L),
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