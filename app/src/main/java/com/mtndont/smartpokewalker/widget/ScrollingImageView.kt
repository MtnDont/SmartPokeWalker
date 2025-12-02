package com.mtndont.smartpokewalker.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.tooling.preview.devices.WearDevices
import com.mtndont.smartpokewalker.R

@Composable
fun ScrollingImageView(
    painter: Painter,
    modifier: Modifier = Modifier
) {
    SubcomposeLayout(modifier) { constraints ->
        val parentWidth = constraints.maxWidth
        val parentHeight = constraints.maxHeight

        val imageWidth = (parentWidth * 0.55f).toInt()

        val placeables = subcompose("content") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy((-2).dp),
                modifier = modifier.basicMarquee(
                    iterations = Int.MAX_VALUE,
                    animationMode = MarqueeAnimationMode.Immediately,
                    repeatDelayMillis = 0,
                    spacing = MarqueeSpacing(0.dp)
                )
            ) {
                repeat(2) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .offset(x = (-1).dp)
                            .width(imageWidth.toDp())
                            .height(parentHeight.toDp())
                    )
                }
            }
        }.map { it.measure(constraints.copy(minWidth = 0)) }

        layout(parentWidth, parentHeight) {
            placeables.forEach { it.place(0, 0) }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun ScrollingImageViewSmallPreview() {
    ScrollingImageView(
        painter = BitmapPainter(
            image = ImageBitmap.imageResource(R.raw.route1),
            filterQuality = FilterQuality.None
        ),
        modifier = Modifier
            .background(colorResource(R.color.background_gray))
            .fillMaxSize()
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun ScrollingImageViewLargePreview() {
    ScrollingImageView(
        painter = BitmapPainter(
            image = ImageBitmap.imageResource(R.raw.route1),
            filterQuality = FilterQuality.None
        ),
        modifier = Modifier
            .background(colorResource(R.color.background_gray))
            .fillMaxSize()
    )
}