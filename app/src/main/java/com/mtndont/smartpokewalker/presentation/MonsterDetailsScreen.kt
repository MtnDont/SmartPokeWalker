package com.mtndont.smartpokewalker.presentation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.AnchorType
import androidx.wear.compose.foundation.CurvedDirection
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.curvedComposable
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material.curvedText
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.presentation.theme.SmartPokeWalkerTheme
import com.mtndont.smartpokewalker.util.AnimatedDrawableUtil
import kotlin.math.abs
import kotlin.math.min

@Composable
fun MonsterDetailsScreen(
    currentSteps: Long,
    totalWatts: Long,
    sex: Int? = null,
    canEvolve: Boolean = false
) {
    SmartPokeWalkerTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background_gray)),
            contentAlignment = Alignment.Center
        ) {
            val experienceRemainder = if (currentSteps < MonsterModel.MAX_EXPERIENCE) {
                currentSteps % 1000
            } else {
                0
            }

            TimeText(
                timeTextStyle = TimeTextDefaults.timeTextStyle(
                    color = Color.Black,
                    fontSize = 24.sp
                ) + TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    )
                )
            )

            CircularProgressIndicator(
                trackColor = Color.Black,
                startAngle = 15f,
                endAngle = 165f,
                progress = 0.0f,
                strokeWidth = 2.dp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            )
            CircularProgressIndicator(
                progress = experienceRemainder / 1000f,
                trackColor = Color.Transparent,
                indicatorColor = colorResource(R.color.experience_light),
                startAngle = 15f,
                endAngle = 165f,
                strokeWidth = 2.dp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .scale(
                        scaleX = -1f,
                        scaleY = 1f
                    )
            )
            CurvedLayout(
                anchor = 215f //225f
            ) {
                curvedText(
                    text = "$totalWatts w",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    )
                )
            }
            CurvedLayout(
                anchor = 315f
            ) {
                curvedText(
                    text = "${
                        when(sex) {
                            1 -> "♂ "
                            2 -> "♀ "
                            else -> ""
                        }
                    }Lv.${min(abs(currentSteps / 1000) + 1, 100)}",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    )
                )
            }
            CurvedLayout(
                anchor = 165f,
                anchorType = AnchorType.End,
                modifier = Modifier.padding(22.dp)
            ) {
                curvedText(
                    text = "${1000 - experienceRemainder} w",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    ),
                    angularDirection = CurvedDirection.Angular.Reversed
                )
            }
            if (canEvolve) {
                CurvedLayout(
                    anchor = 90f,
                    anchorType = AnchorType.Center,
                    angularDirection = CurvedDirection.Angular.Reversed,
                    modifier = Modifier.padding(22.dp)
                ) {
                    curvedComposable {
                        Icon(
                            painter = painterResource(R.drawable.arrow_shape_up_stack),
                            contentDescription = null,
                            tint = colorResource(R.color.black),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
            CurvedLayout(
                anchor = 15f,
                anchorType = AnchorType.Start,
                modifier = Modifier.padding(22.dp)
            ) {
                curvedText(
                    text = "1000 w",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    ),
                    angularDirection = CurvedDirection.Angular.Reversed
                )
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun MonsterImage(
    modifier: Modifier = Modifier,
    @DrawableRes monsterResId: Int? = null,
    name: String = "",
    content: @Composable (() -> Unit)? = null
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    SmartPokeWalkerTheme {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (LocalInspectionMode.current) {
                        Image(
                            painter = BitmapPainter(
                                //image = ImageBitmap.imageResource(R.raw.dittopng),
                                image = ImageBitmap.imageResource(R.raw.testpng),
                                filterQuality = FilterQuality.None
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .fillMaxHeight(0.6f)
                                .padding(top = (configuration.screenHeightDp / 4.5).dp)
                        )
                    } else {
                        Image(
                            painter = rememberDrawablePainter(
                                drawable = AnimatedDrawableUtil.createAnimatedImageDrawableFromImageDecoder(
                                    context.applicationContext,
                                    //monsterResId ?: R.raw.ditto
                                    monsterResId ?: R.raw.a1
                                )
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .fillMaxHeight(0.6f)
                                .padding(top = (configuration.screenHeightDp / 4.5).dp)
                        )
                    }
                    Text(
                        text = name,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(
                            Font(R.font.pixelfont)
                        )
                    )
                    content?.invoke()
                }
            }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun MonsterDetailsScreenSmallPreview() {
    MonsterDetailsScreen(
        125L,
        1234L,
        canEvolve = true
    )
    MonsterImage(
        name = "Android"
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun MonsterDetailsScreenLargePreview() {
    MonsterDetailsScreen(
        //125L,
        99500L,
        1234L,
        canEvolve = true
    )
    MonsterImage(
        name = "Android"
    )
}

@SuppressLint("ResourceType")
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun MonsterImageLargePreview() {
    MonsterImage(
        name = "Android",
        monsterResId = R.raw.a2,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
    )
}