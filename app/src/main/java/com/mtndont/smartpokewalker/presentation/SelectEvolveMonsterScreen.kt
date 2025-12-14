package com.mtndont.smartpokewalker.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalConfiguration
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
import androidx.wear.compose.foundation.pager.HorizontalPager
import androidx.wear.compose.foundation.pager.rememberPagerState
import androidx.wear.compose.foundation.rotary.RotaryScrollableDefaults
import androidx.wear.compose.foundation.rotary.rotaryScrollable
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.data.MonsterDefinition
import com.mtndont.smartpokewalker.data.MonsterDefinitions
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.util.AnimatedDrawableUtil
import kotlinx.coroutines.launch

@Composable
fun SelectEvolveMonsterScreen(
    monsterToEvolve: MonsterModel,
    evolvedDefinitions: List<MonsterDefinition>,
    confirmOnClick: (MonsterModel) -> Unit
) {
    val configuration = LocalConfiguration.current

    val evolvedMonsters = evolvedDefinitions.map {
        monsterToEvolve.evolveToDefinition(it)
    }

    val pagerState = rememberPagerState(pageCount = {
        evolvedMonsters.size
    })

    val focusRequester: FocusRequester = remember { FocusRequester() }

    val coroutine = rememberCoroutineScope()
    
    var confirmClicked by remember { mutableStateOf(false) }

    Crossfade(
        targetState = confirmClicked,
        label = "SelectEvolveCrossfade"
    ) { targetState ->
        if (targetState) {
            EvolveMonsterScreen(
                monster = monsterToEvolve,
                evolvedMonster = evolvedMonsters[pagerState.currentPage],
                onClick = {
                    confirmOnClick.invoke(evolvedMonsters[pagerState.currentPage])
                }
            )
        }
        else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.background_gray))
                    .padding(bottom = 30.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_evolution),
                    color = colorResource(R.color.black),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    )
                )

                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(
                        start = configuration.screenWidthDp.dp / 3,
                        end = configuration.screenWidthDp.dp / 3
                    ),
                    modifier = Modifier
                        .rotaryScrollable(
                            behavior = RotaryScrollableDefaults.snapBehavior(
                                pagerState = pagerState
                            ),
                            focusRequester = focusRequester
                        )
                        .focusRequester(focusRequester)
                        .focusable()
                ) { page ->
                    val selectedButtonColor by animateColorAsState(
                        targetValue = if (page != pagerState.currentPage) {
                            colorResource(R.color.light_gray)
                        } else {
                            colorResource(R.color.dark_gray)
                        }
                    )

                    val painter = if (LocalInspectionMode.current) {
                        BitmapPainter(
                            image = ImageBitmap.imageResource(
                                evolvedMonsters[page].getFormResId()
                            ),
                            filterQuality = FilterQuality.None
                        )
                    } else {
                        rememberDrawablePainter(
                            drawable = AnimatedDrawableUtil.createAnimatedImageDrawableFromImageDecoder(
                                LocalContext.current.applicationContext,
                                evolvedMonsters[page].getFormResId()
                            )
                        )
                    }

                    Button(
                        onClick = {
                            if (page != pagerState.currentPage) {
                                coroutine.launch {
                                    pagerState.animateScrollToPage(page)
                                }
                            }
                        },
                        shape = RoundedCornerShape(10),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.background_gray)
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .width(configuration.screenWidthDp.dp)
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
                                text = evolvedMonsters[page].getDefaultName(),
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
                        confirmClicked = true
                        /*confirmOnClick.invoke(
                    evolvedMonsters[pagerState.currentPage]
                )*/
                    },
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
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun SelectEvolveMonsterScreenPreview() {
    SelectEvolveMonsterScreen(
        monsterToEvolve = MonsterModel(
            id = 0,
            dexId = 133,
            sex = 1
        ),
        evolvedDefinitions = listOf(
            MonsterDefinitions.entries[133],
            MonsterDefinitions.entries[134],
            MonsterDefinitions.entries[135],
            MonsterDefinitions.entries[195],
            MonsterDefinitions.entries[196],
            MonsterDefinitions.entries[469],
            MonsterDefinitions.entries[470]
        ),
        confirmOnClick = {}
    )
}