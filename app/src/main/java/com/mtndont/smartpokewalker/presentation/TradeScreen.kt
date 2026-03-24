package com.mtndont.smartpokewalker.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.wear.tooling.preview.devices.WearDevices
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.ble.DiscoveredHost
import com.mtndont.smartpokewalker.data.MonsterDefinitions
import com.mtndont.smartpokewalker.data.MonsterModel

@Composable
fun TradeScreen(
    viewModel: TradeViewModel = hiltViewModel()
) {
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
            }
        )
    }
}

@Composable
fun HostOrJoinWidget(
    hostOnClick: () -> Unit,
    joinOnClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = hostOnClick
        ) {
            Text("Host")
        }
        Button(
            onClick = joinOnClick
        ) {
            Text("Join")
        }
    }
}

@Composable
fun TradeWidget(
    state: TradeState,
    acceptOnClick: () -> Unit,
    cancelOnClick: () -> Unit,
    codeSelectOnClick: (DiscoveredHost) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when (state) {
            is TradeState.ReviewingOffer -> {
                val offer = state.theirMonster
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = offer.name,
                        color = colorResource(R.color.background_gray)
                    )
                    Text(
                        text = "Lv${offer.getLevel()}",
                        color = colorResource(R.color.background_gray)
                    )
                    Button(
                        onClick = acceptOnClick
                    ) {
                        Text(
                            text = "Accept"
                        )
                    }
                    Button(
                        onClick = cancelOnClick
                    ) {
                        Text(
                            text = "Decline"
                        )
                    }
                }
            }

            is TradeState.Hosting -> {
                val code = state.code
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = "Your code",
                        color = colorResource(R.color.background_gray)
                    )
                    Text(
                        text = code,
                        color = colorResource(R.color.background_gray)
                    )
                    Text(
                        text = "Share this with your trade partner",
                        color = colorResource(R.color.background_gray),
                        textAlign = TextAlign.Center
                    )
                }
            }

            is TradeState.DiscoveringHosts -> {
                val hosts = state.hosts
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Nearby traders",
                        color = colorResource(R.color.background_gray)
                    )
                    if (hosts.isEmpty()) {
                        CircularProgressIndicator()
                        Text(
                            text = "Searching...",
                            color = colorResource(R.color.background_gray)
                        )
                    } else {
                        hosts.forEach { host ->
                            Button(
                                onClick = {
                                    codeSelectOnClick.invoke(host)
                                }
                            ) {
                                Text(
                                    text = host.code,
                                    color = colorResource(R.color.background_gray)
                                )
                            }
                        }
                    }
                }
            }

            is TradeState.WaitingForPartner -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Waiting for partner...",
                        color = colorResource(R.color.background_gray)
                    )
                    Button(
                        onClick = cancelOnClick
                    ) {
                        Text(
                            text = "Cancel"
                        )
                    }
                }
            }

            is TradeState.Cancelled -> {
                Text(
                    text = "Trade cancelled.",
                    color = colorResource(R.color.background_gray)
                )
            }

            is TradeState.Complete -> {
                val received = state.received
                Text(
                    text = "Got ${received.name}!",
                    color = colorResource(R.color.background_gray)
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
fun TradeWidgetCancelledPreview() {
    TradeWidget(
        state = TradeState.Cancelled,
        acceptOnClick = {},
        cancelOnClick = {},
        codeSelectOnClick = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun TradeWidgetHostingPreview() {
    TradeWidget(
        state = TradeState.Hosting("123456"),
        acceptOnClick = {},
        cancelOnClick = {},
        codeSelectOnClick = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun TradeWidgetDiscoveringHostsPreview() {
    TradeWidget(
        state = TradeState.DiscoveringHosts(listOf()),
        acceptOnClick = {},
        cancelOnClick = {},
        codeSelectOnClick = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun TradeWidgetWaitingForOfferPreview() {
    TradeWidget(
        state = TradeState.WaitingForOffer,
        acceptOnClick = {},
        cancelOnClick = {},
        codeSelectOnClick = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun TradeWidgetWaitingForPartnerPreview() {
    TradeWidget(
        state = TradeState.WaitingForPartner,
        acceptOnClick = {},
        cancelOnClick = {},
        codeSelectOnClick = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun TradeWidgetReviewingOfferPreview() {
    TradeWidget(
        state = TradeState.ReviewingOffer(
            MonsterModel.getRandomMonster(
                MonsterDefinitions.entries[0].id
            )
        ),
        acceptOnClick = {},
        cancelOnClick = {},
        codeSelectOnClick = {}
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun TradeWidgetCompletePreview() {
    TradeWidget(
        state = TradeState.Complete(
            MonsterModel.getRandomMonster(
                MonsterDefinitions.entries[0].id
            )
        ),
        acceptOnClick = {},
        cancelOnClick = {},
        codeSelectOnClick = {}
    )
}