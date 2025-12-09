package com.mtndont.smartpokewalker.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.wear.compose.material3.CircularProgressIndicator
import androidx.wear.compose.material3.ProgressIndicatorDefaults
import androidx.wear.tooling.preview.devices.WearDevices
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.presentation.theme.SmartPokeWalkerTheme
import com.mtndont.smartpokewalker.widget.ScrollingImageView

@SuppressLint("ResourceType")
@Composable
fun RouteExplorationScreen(
    durationMillis: Int = 0
) {
    SmartPokeWalkerTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background_gray))
        ) {
            if (durationMillis != 0) {
                var progress by remember { mutableFloatStateOf(0f) }
                val progressAnimate by animateFloatAsState(
                    targetValue = progress,
                    animationSpec = tween(
                        durationMillis = durationMillis,
                        easing = LinearEasing
                    )
                )

                LaunchedEffect(Unit) {
                    progress = 1f
                }

                CircularProgressIndicator(
                    colors = ProgressIndicatorDefaults.colors(
                        indicatorColor = colorResource(R.color.dark_gray),
                        trackColor = Color.Transparent
                    ),
                    progress = {
                        progressAnimate
                    },
                    strokeWidth = 4.dp,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(CircleShape)
                    .fillMaxSize()
            ) {
                ScrollingImageView(
                    painter = BitmapPainter(
                        image = ImageBitmap.imageResource(R.raw.route1),
                        filterQuality = FilterQuality.None
                    ),
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .align(Alignment.BottomCenter)
                )
                Box(
                    modifier = Modifier
                        .background(colorResource(R.color.light_gray))
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .align(Alignment.BottomCenter)
                )
                LoadingDots(
                    numOfDots = 4,
                    baseDotColor = colorResource(R.color.dark_gray),
                    dotColor = colorResource(R.color.black),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxHeight(0.5f)
                )
            }
        }
    }
}

@Composable
fun LoadingDots(
    numOfDots: Int,
    baseDotColor: Color,
    dotColor: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier
    ) {
        val animationDelay = 400
        val duration = numOfDots * animationDelay
        repeat(numOfDots) {
            val anchor by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = duration + animationDelay
                        0f at (it * animationDelay) using LinearEasing
                        1f at (it * animationDelay) + (animationDelay/2) using FastOutSlowInEasing
                        0f at (it * animationDelay) + (animationDelay*2)
                    }
                )
            )

            Box(
                modifier = Modifier
                    .size(25.dp)
                    .dotBehaviour(
                        painter = BitmapPainter(
                            image = ImageBitmap.imageResource(
                                when(it % 2) {
                                    0 -> R.raw.left_step
                                    1 -> R.raw.right_step
                                    else -> R.raw.pixel_circle
                                }
                            ),
                            filterQuality = FilterQuality.None
                        ),
                        startColor = dotColor,
                        stopColor = baseDotColor,
                        anchor = anchor
                    )
            )
        }
    }
}

// Shared modifier for all three dots
fun Modifier.dotBehaviour(
    painter: Painter,
    startColor: Color,
    stopColor: Color,
    anchor: Float
) = this
    .scale(lerp(1f, 1.25f, anchor))
    .paint(
        painter = painter,
        colorFilter = ColorFilter.tint(
            color = androidx.compose.ui.graphics.lerp(
                stopColor,
                startColor,
                anchor
            )
        )
    )

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun RouteExplorationScreenSmallPreview() {
    RouteExplorationScreen()
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun RouteExplorationScreenLargePreview() {
    RouteExplorationScreen(10_000)
}