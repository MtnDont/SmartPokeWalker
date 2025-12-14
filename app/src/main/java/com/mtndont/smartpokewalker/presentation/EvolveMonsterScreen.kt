package com.mtndont.smartpokewalker.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialShapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.CurvedDirection
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.CurvedModifier
import androidx.wear.compose.foundation.curvedRow
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.curvedText
import androidx.wear.tooling.preview.devices.WearDevices
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.data.MonsterModel

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EvolveMonsterScreen(
    monster: MonsterModel,
    evolvedMonster: MonsterModel,
    onClick: () -> Unit
) {
    var start by remember { mutableStateOf(false) }

    val progress by animateFloatAsState(
        targetValue = if (start) 1f else 0f,
        animationSpec = keyframes {
            durationMillis = 10_000
            0f at 0
            1f at 10_000 using FastOutSlowInEasing
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    if (progress > 0.9f) {
                        onClick.invoke()
                    }
                }
            )
    ) {
        val infiniteTransitionEdgeText = rememberInfiniteTransition()
        val edgeTextRotate by infiniteTransitionEdgeText.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 15_000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        // Loading indicator scale
        val indicatorScale = when {
            progress <= 0.4f -> {
                FastOutSlowInEasing.transform(progress / 0.4f)
            }
            progress <= 0.6f -> 1f
            else -> {
                1f - LinearOutSlowInEasing.transform((progress - 0.6f) / 0.4f)
            }
        }.coerceIn(0f, 1f)

        val image1Alpha = when {
            progress <= 0.4f -> 1f
            progress <= 0.6f -> {
                1f - FastOutSlowInEasing.transform((progress - 0.4f) / 0.2f)
            }
            else -> 0f
        }

        MonsterImage(
            monsterResId = monster.getFormResId(),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = image1Alpha
                }
        )

        AnimatedVisibility(
            visible = progress > 0.6f,
            enter = fadeIn()
        ) {
            val infiniteTransitionText = rememberInfiniteTransition()

            val textFlash by infiniteTransitionText.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 1_000
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )

            MonsterImage(
                monsterResId = evolvedMonster.getFormResId(),
                name = evolvedMonster.getDefaultName(),
                modifier = Modifier
                    .fillMaxSize(0.9f)
            ) {
                AnimatedVisibility(
                    visible = progress > 0.9f,
                    enter = fadeIn()
                ) {
                    Text(
                        text = "Tap anywhere to continue",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(
                            Font(R.font.pixelfont)
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(top = 8.dp)
                            .alpha(textFlash)
                    )
                }
            }
        }

        Crossfade(
            targetState = progress > 0.6f,
            animationSpec = tween(durationMillis = 1_000),
            label = "EvolutionText"
        ) { targetState ->
            val rotatingText = if (targetState) {
                "A New Form!"
            } else {
                "Evolving..."
            }

            val curvedTextColor = colorResource(R.color.black)

            repeat(4) {
                CurvedLayout(
                    anchor = (90f * it) + 360f * edgeTextRotate,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    curvedRow {
                        curvedText(
                            text = rotatingText,
                            fontSize = 24.sp,
                            color = curvedTextColor,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            ),
                            angularDirection = CurvedDirection.Angular.Normal,
                            maxSweepAngle = 360f,
                            modifier = CurvedModifier
                        )
                    }
                }
            }
        }

        LoadingIndicator(
            polygons = listOf(
                MaterialShapes.SoftBurst,
                MaterialShapes.Cookie9Sided,
                MaterialShapes.Pentagon,
                MaterialShapes.Sunny,
                MaterialShapes.Cookie4Sided,
                MaterialShapes.Oval,
            ),
            color = colorResource(R.color.black),
            modifier = Modifier
                .fillMaxSize(1.1f)
                .graphicsLayer {
                    scaleX = indicatorScale
                    scaleY = indicatorScale
                }
        )

        LaunchedEffect(Unit) {
            start = true
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun EvolveMonsterScreenSmallPreview() {
    EvolveMonsterScreen(
        monster = MonsterModel(
            id = 0,
            dexId = 2,
            sex = 1,
            form = 0
        ),
        evolvedMonster = MonsterModel(
            id = 0,
            dexId = 3,
            sex = 1,
            form = 0
        ),
        onClick = {}
    )
}