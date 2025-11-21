package com.mtndont.smartpokewalker.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
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
import androidx.wear.tooling.preview.devices.WearDevices
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.util.getRawIdentifier

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
            MonsterDetailsScreen(currentSteps, totalWatts)
            AnimatedVisibility(
                visible = pagerState.currentPage == page,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                MonsterImage(
                    when (page + 1) {
                        1 -> context.getRawIdentifier("a131") // R.raw.a131 -> ditto
                        2 -> context.getRawIdentifier("a24") // R.raw.a24 -> pikachu
                        3 -> context.getRawIdentifier("a132") // R.raw.a132 -> eevee
                        4 -> context.getRawIdentifier("a654") // R.raw.a654 -> rotom
                        5 -> context.getRawIdentifier("a663") // R.raw.a663 -> pichu_notch
                        6 -> context.getRawIdentifier("a490") // R.raw.a490 -> darkrai
                        else -> context.getRawIdentifier("a131") // R.raw.a131 -> ditto
                    }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    CurvedLayout(
        anchor = 0f,
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

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun WalkPagerScreenSmallPreview() {
    WalkPagerScreen(100000L, 88888888L)
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun WalkPagerScreenLargePreview() {
    WalkPagerScreen(0L, 88888888L)
}