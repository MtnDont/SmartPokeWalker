package com.mtndont.smartpokewalker.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.CurvedTextStyle
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.TimeTextDefaults
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.TimeText
import androidx.wear.compose.material3.timeTextCurvedText
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.data.MonsterDefinitions
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.util.AnimatedDrawableUtil

@Composable
fun StarterMonsterScreen(
    starterMonsters: List<MonsterModel>,
    confirmOnClick: (MonsterModel) -> Unit
) {
    var selectedMonsterIdx by remember { mutableIntStateOf(-1) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
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

    val timetextStyle = TimeTextDefaults.timeTextStyle(
        color = colorResource(R.color.black),
        fontSize = 24.sp
    ) + CurvedTextStyle(
        fontFamily = FontFamily(
            Font(R.font.pixelfont)
        )
    )

    TimeText(
        backgroundColor = Color.Transparent
    ) { time ->
        timeTextCurvedText(
            time,
            style = timetextStyle
        )
    }

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

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
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