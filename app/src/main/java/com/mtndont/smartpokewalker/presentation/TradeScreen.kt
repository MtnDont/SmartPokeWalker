package com.mtndont.smartpokewalker.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.OutlinedButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.wear.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.wear.compose.foundation.CurvedDirection
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.CurvedModifier
import androidx.wear.compose.foundation.curvedRow
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.IconButton
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.curvedText
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.compose.layout.ColumnItemType
import com.google.android.horologist.compose.layout.rememberResponsiveColumnPadding
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.ble.DiscoveredHost
import com.mtndont.smartpokewalker.data.MonsterDefinitions
import com.mtndont.smartpokewalker.data.MonsterModel

@Composable
fun TradeScreen(
    monster: MonsterModel,
    returnOnClick: () -> Unit,
    viewModel: TradeViewModel = hiltViewModel()
) {
    viewModel.setMonster(monster)
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    if (ActivityCompat.checkSelfPermission(
            context.applicationContext,
            Manifest.permission.BLUETOOTH_CONNECT
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Text("missing ble permissions")
    } else {
        if (state == TradeState.Idle) {
            HostOrJoinWidget(
                hostOnClick = {
                    viewModel.hostTrade()
                },
                joinOnClick = {
                    viewModel.joinTrade()
                }
            )
        }
        TradeWidget(
            state = state,
            acceptOnClick = {
                viewModel.submitDecision(true)
            },
            cancelOnClick = {
                viewModel.submitDecision(false)
            },
            codeSelectOnClick = { host ->
                viewModel.selectHost(host)
            },
            returnOnClick = returnOnClick
        )
    }
}

@SuppressLint("ResourceType")
@Composable
fun HostOrJoinWidget(
    hostOnClick: () -> Unit,
    joinOnClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.link_trade),
                color = colorResource(R.color.black),
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(R.font.pixelfont)
                ),
                textAlign = TextAlign.Center
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = BitmapPainter(
                        image = ImageBitmap.imageResource(
                            R.raw.ball
                        ),
                        filterQuality = FilterQuality.None
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
                Image(
                    painter = BitmapPainter(
                        image = ImageBitmap.imageResource(
                            R.raw.item
                        ),
                        filterQuality = FilterQuality.None
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            HorizontalDivider(
                color = colorResource(R.color.light_gray),
                thickness = 2.dp,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Button(
                label = {
                    Text(
                        text = stringResource(R.string.host_trade),
                        color = colorResource(R.color.light_gray),
                        fontSize = 24.sp,
                        fontFamily = FontFamily(
                            Font(R.font.pixelfont)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                onClick = hostOnClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.black)
                ),
                modifier = Modifier.fillMaxWidth(0.6f)
            )
            OutlinedButton(
                label = {
                    Text(
                        text = stringResource(R.string.find_trade),
                        color = colorResource(R.color.black),
                        fontSize = 24.sp,
                        fontFamily = FontFamily(
                            Font(R.font.pixelfont)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                onClick = joinOnClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    secondaryContentColor = colorResource(R.color.black)
                ),
                border = ButtonDefaults.outlinedButtonBorder(
                    enabled = true,
                    borderColor = colorResource(R.color.black),
                    borderWidth = 2.dp
                ),
                modifier = Modifier.fillMaxWidth(0.6f)
            )
        }
    }
}

@Composable
fun TradeCancelledScreen(
    onScreenTap: () -> Unit
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

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onScreenTap
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background_gray))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.link_trade),
                    color = colorResource(R.color.black),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    )
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    color = colorResource(R.color.light_gray),
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(vertical = 6.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = "Cancel Release",
                    tint = colorResource(R.color.black),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(
                    modifier = Modifier.size(4.dp)
                )
                Text(
                    text = stringResource(R.string.trade_cancelled),
                    color = colorResource(R.color.black),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    ),
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    color = colorResource(R.color.light_gray),
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(vertical = 6.dp)
                )
                Text(
                    text = stringResource(R.string.tap_to_return),
                    color = colorResource(R.color.black),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .alpha(textFlash)
                )
            }
        }
    }
}

@Composable
fun TradeWidget(
    state: TradeState,
    acceptOnClick: () -> Unit,
    cancelOnClick: () -> Unit,
    codeSelectOnClick: (DiscoveredHost) -> Unit,
    returnOnClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when (state) {
            is TradeState.ReviewingOffer -> {
                val offer = state.theirMonster
                OfferScreen(
                    monster = offer,
                    acceptOnClick = acceptOnClick,
                    cancelOnClick = cancelOnClick
                )
            }

            is TradeState.Hosting -> {
                val code = state.code
                HostCodeScreen(code)
            }

            is TradeState.DiscoveringHosts -> {
                val hosts = state.hosts
                ListeningPairingCodeScreen(
                    items = hosts,
                    itemOnSelect = { host ->
                        codeSelectOnClick.invoke(host)
                    }
                )
            }

            is TradeState.WaitingForOffer -> {
                WaitingForOfferScreen()
            }

            is TradeState.WaitingForPartner -> {
                WaitingForPartnerScreen(
                    cancelOnClick = cancelOnClick
                )
            }

            is TradeState.Cancelled -> {
                TradeCancelledScreen(
                    onScreenTap = returnOnClick
                )
            }

            is TradeState.Complete -> {
                val received = state.received
                TradeCompleteScreen(
                    monster = received,
                    screenOnClick = returnOnClick
                )
            }

            // Idle, Searching, etc.
            else -> {
                Text(
                    text = state.javaClass.simpleName,
                    color = colorResource(R.color.background_gray)
                )
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun HostCodeScreen(
    code: String
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animationDelay = 600
    val duration = 3 * animationDelay

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.link_trade),
                color = colorResource(R.color.black),
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(R.font.pixelfont)
                )
            )
            Spacer(
                modifier = Modifier.size(4.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(3) {
                    val anchor by infiniteTransition.animateFloat(
                        initialValue = 0.2f,
                        targetValue = 0.2f,
                        animationSpec = infiniteRepeatable(
                            animation = keyframes {
                                durationMillis = duration + animationDelay
                                0.2f at (it * animationDelay) using LinearEasing
                                1f at (it * animationDelay) + (animationDelay/2) using FastOutSlowInEasing
                                0.2f at (it * animationDelay) + (animationDelay*2)
                            }
                        )
                    )

                    Image(
                        painter = BitmapPainter(
                            image = ImageBitmap.imageResource(
                                R.raw.ball
                            ),
                            filterQuality = FilterQuality.None
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .alpha(anchor)
                    )
                }
            }
            HorizontalDivider(
                thickness = 2.dp,
                color = colorResource(R.color.light_gray),
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(vertical = 6.dp)
            )
            Text(
                text = stringResource(R.string.your_code),
                color = colorResource(R.color.black),
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(R.font.pixelfont)
                )
            )
            Text(
                text = code.chunked(1).joinToString(" "),
                color = colorResource(R.color.black),
                fontSize = 40.sp,
                fontFamily = FontFamily(
                    Font(R.font.pixelfont)
                ),
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = colorResource(R.color.light_gray),
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(vertical = 6.dp)
            )
            Text(
                text = stringResource(R.string.waiting),
                color = colorResource(R.color.black),
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(R.font.pixelfont)
                )
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun OfferScreen(
    monster: MonsterModel,
    acceptOnClick: () -> Unit,
    cancelOnClick: () -> Unit,
) {
    MonsterImage(
        name = stringResource(
            R.string.name_sex_level,
            monster.name,
            when(monster.sex) {
                1 -> "♂"
                2 -> "♀"
                else -> ""
            },
            monster.getLevel()
        ),
        monsterResId = monster.getFormResId(),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
            .offset(
                y = (-24).dp
            )
    ) {
        Spacer(
            modifier = Modifier.size(12.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = cancelOnClick,
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        colorResource(R.color.black),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = "Decline Trade",
                    tint = colorResource(R.color.background_gray),
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(
                onClick = acceptOnClick,
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        colorResource(R.color.black),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.check),
                    contentDescription = "Accept Trade",
                    tint = colorResource(R.color.background_gray),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun ListeningPairingCodeScreen(
    items: List<DiscoveredHost>,
    itemOnSelect: (DiscoveredHost) -> Unit
) {
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()

    ScreenScaffold(
        scrollState = listState,
        contentPadding = rememberResponsiveColumnPadding(
            first = ColumnItemType.ListHeader,
            last = ColumnItemType.IconButton
        ),
        modifier = Modifier.background(colorResource(R.color.background_gray))
    ) { contentPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (items.isEmpty()) {
                CircularProgressIndicator(
                    color = colorResource(R.color.black)
                )
            }
        }
        TransformingLazyColumn(
            state = listState,
            contentPadding = contentPadding
        ) {
            item {
                Text(
                    text = stringResource(R.string.nearby_traders),
                    color = colorResource(R.color.black),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    )
                )
            }
            items(items) { item ->
                Button(
                    label = {
                        Text(
                            text = item.code,
                            color = colorResource(R.color.background_gray),
                            fontSize = 24.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    onClick = {
                        itemOnSelect.invoke(item)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.black)
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(
                            when {
                                    item.rssi >= -60 -> R.drawable.signal_wifi_4_bar
                                    item.rssi >= -70 -> R.drawable.network_wifi_3_bar
                                    item.rssi >= -80 -> R.drawable.network_wifi_2_bar
                                    item.rssi >= -90 -> R.drawable.network_wifi_1_bar
                                    else -> R.drawable.signal_wifi_0_bar
                                }
                            ),
                            contentDescription = "",
                            tint = colorResource(R.color.background_gray),
                        )
                    },
                    transformation = SurfaceTransformation(transformationSpec)
                )
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun WaitingForOfferScreen() {
    val infiniteTransition = rememberInfiniteTransition()
    val animationDelay = 600
    val duration = 3 * animationDelay

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.link_trade),
                color = colorResource(R.color.black),
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(R.font.pixelfont)
                )
            )
            Spacer(
                modifier = Modifier.size(4.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(3) {
                    val anchor by infiniteTransition.animateFloat(
                        initialValue = 0.2f,
                        targetValue = 0.2f,
                        animationSpec = infiniteRepeatable(
                            animation = keyframes {
                                durationMillis = duration + animationDelay
                                0.2f at (it * animationDelay) using LinearEasing
                                1f at (it * animationDelay) + (animationDelay/2) using FastOutSlowInEasing
                                0.2f at (it * animationDelay) + (animationDelay*2)
                            }
                        )
                    )

                    Image(
                        painter = BitmapPainter(
                            image = ImageBitmap.imageResource(
                                R.raw.ball
                            ),
                            filterQuality = FilterQuality.None
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .alpha(anchor)
                    )
                }
            }
            HorizontalDivider(
                thickness = 2.dp,
                color = colorResource(R.color.light_gray),
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(vertical = 6.dp)
            )
            Text(
                text = stringResource(R.string.waiting_on_partner),
                color = colorResource(R.color.black),
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(R.font.pixelfont)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun WaitingForPartnerScreen(
    cancelOnClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animationDelay = 600
    val duration = 3 * animationDelay

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.link_trade),
                color = colorResource(R.color.black),
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(R.font.pixelfont)
                )
            )
            Spacer(
                modifier = Modifier.size(4.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(3) {
                    val anchor by infiniteTransition.animateFloat(
                        initialValue = 0.2f,
                        targetValue = 0.2f,
                        animationSpec = infiniteRepeatable(
                            animation = keyframes {
                                durationMillis = duration + animationDelay
                                0.2f at (it * animationDelay) using LinearEasing
                                1f at (it * animationDelay) + (animationDelay/2) using FastOutSlowInEasing
                                0.2f at (it * animationDelay) + (animationDelay*2)
                            }
                        )
                    )

                    Image(
                        painter = BitmapPainter(
                            image = ImageBitmap.imageResource(
                                R.raw.ball
                            ),
                            filterQuality = FilterQuality.None
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .alpha(anchor)
                    )
                }
            }
            HorizontalDivider(
                thickness = 2.dp,
                color = colorResource(R.color.light_gray),
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(vertical = 6.dp)
            )
            Text(
                text = stringResource(R.string.offer_accepted),
                color = colorResource(R.color.black),
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(R.font.pixelfont)
                ),
                textAlign = TextAlign.Center
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = colorResource(R.color.light_gray),
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(vertical = 6.dp)
            )
            OutlinedButton(
                label = {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = colorResource(R.color.black),
                        fontSize = 24.sp,
                        fontFamily = FontFamily(
                            Font(R.font.pixelfont)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                onClick = cancelOnClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    secondaryContentColor = colorResource(R.color.black)
                ),
                border = ButtonDefaults.outlinedButtonBorder(
                    enabled = true,
                    borderColor = colorResource(R.color.black),
                    borderWidth = 2.dp
                ),
                modifier = Modifier.fillMaxWidth(0.6f)
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun TradeCompleteScreen(
    monster: MonsterModel,
    screenOnClick: () -> Unit
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

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = screenOnClick
            )
            .fillMaxSize()
            .background(colorResource(R.color.background_gray))
    ) {
        val rotatingText = stringResource(R.string.trade_complete)
        val tapAnywhereText = stringResource(R.string.tap_anywhere)
        val curvedTextColor = colorResource(R.color.black)

        MonsterImage(
            monsterResId = monster.getFormResId(),
            name = stringResource(
                R.string.name_sex_level,
                monster.name,
                when(monster.sex) {
                    1 -> "♂"
                    2 -> "♀"
                    else -> ""
                },
                monster.getLevel()
            ),
            modifier = Modifier
                .fillMaxSize()
        )

        CurvedLayout(
            anchor = 270f + 360f * edgeTextRotate,
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
        CurvedLayout(
            anchor = 90f + 360f * edgeTextRotate,
            modifier = Modifier
                .fillMaxSize()
                .alpha(textFlash)
        ) {
            curvedRow {
                curvedText(
                    text = tapAnywhereText,
                    fontSize = 20.sp,
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

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun HostOrJoinWidgetPreview() {
    HostOrJoinWidget(
        hostOnClick = {},
        joinOnClick = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun TradeCancelledPreview() {
    TradeCancelledScreen(
        onScreenTap = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun HostCodePreview() {
    HostCodeScreen("12345")
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun ListeningPairingCodeScreenPreview() {
    ListeningPairingCodeScreen(
        items = listOf(
            DiscoveredHost(device = null, code = "12345").apply {
                addRssiSample(-60)
            },
            DiscoveredHost(device = null, code = "12345").apply {
                addRssiSample(-70)
            },
            DiscoveredHost(device = null, code = "12345").apply {
                addRssiSample(-80)
            },
            DiscoveredHost(device = null, code = "12345").apply {
                addRssiSample(-90)
            },
            DiscoveredHost(device = null, code = "12345").apply {
                addRssiSample(-100)
            },
        ),
        itemOnSelect = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun WaitingForOfferPreview() {
    WaitingForOfferScreen()
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun WaitingForPartnerPreview() {
    WaitingForPartnerScreen(
        cancelOnClick = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun OfferPreview() {
    OfferScreen(
        monster = MonsterModel.getRandomMonster(
            MonsterDefinitions.entries[0].id
        ),
        acceptOnClick = {},
        cancelOnClick = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun TradeCompletePreview() {
    TradeCompleteScreen(
        monster = MonsterModel.getRandomMonster(
            MonsterDefinitions.entries[0].id
        ),
        screenOnClick = {}
    )
}