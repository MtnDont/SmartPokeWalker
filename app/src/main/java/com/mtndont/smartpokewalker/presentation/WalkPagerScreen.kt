package com.mtndont.smartpokewalker.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.CurvedAlignment
import androidx.wear.compose.foundation.CurvedDirection
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.curvedComposable
import androidx.wear.compose.foundation.curvedRow
import androidx.wear.compose.foundation.pager.VerticalPager
import androidx.wear.compose.foundation.pager.rememberPagerState
import androidx.wear.compose.foundation.rotary.RotaryScrollableDefaults
import androidx.wear.compose.foundation.rotary.rotaryScrollable
import androidx.wear.tooling.preview.devices.WearDevices
import com.mtndont.smartpokewalker.R

@SuppressLint("ResourceType")
@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun WalkPagerApp(
    viewModel: MainScreenAppViewModel = viewModel()
) {
    val currentSteps by viewModel.currentSteps.collectAsStateWithLifecycle()
    val totalWatts by viewModel.totalWatts.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState

    WalkPagerScreen(currentSteps, totalWatts)
}

@SuppressLint("ResourceType")
@Composable
fun WalkPagerScreen(
    currentSteps: Long,
    totalWatts: Long
) {
    val pagerState = rememberPagerState(pageCount = {
        6
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
            MonsterDetailsScreen(currentSteps, totalWatts)
            AnimatedVisibility(
                visible = pagerState.currentPage == page,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                MonsterImage(
                    when (page + 1) {
                        1 -> R.raw.a131//R.raw.ditto_new
                        2 -> R.raw.a24//R.raw.pikachu
                        3 -> R.raw.a132//R.raw.eevee
                        4 -> R.raw.a654//R.raw.rotom
                        5 -> R.raw.a663//R.raw.pichu_notch
                        6 -> R.raw.a490//R.raw.darkrai
                        else -> R.raw.a131//R.raw.ditto_new
                    }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    CurvedLayout(
        anchor = 90f,
        modifier = Modifier.padding(2.dp)
    ) {
        curvedRow(
            radialAlignment = CurvedAlignment.Radial.Center,
            angularDirection = CurvedDirection.Angular.CounterClockwise
        ) {
            repeat(pagerState.pageCount) { iteration ->
                curvedComposable {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
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

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun WalkPagerScreenPreview() {
    WalkPagerScreen(0L, 0L)
}