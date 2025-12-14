package com.mtndont.smartpokewalker.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.CurvedDirection
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.curvedText
import androidx.wear.tooling.preview.devices.WearDevices
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.presentation.theme.SmartPokeWalkerTheme
import java.util.Locale.getDefault

@Composable
fun TrainerDetailsApp(
    viewModel: TrainerDetailsViewModel = hiltViewModel(),
    boxOnClick: () -> Unit,
    partyOnClick: () -> Unit,
    itemsOnClick: () -> Unit
) {
    val trainerName by viewModel.trainerName.collectAsStateWithLifecycle()

    val exploreSteps by viewModel.exploreSteps.collectAsStateWithLifecycle()

    TrainerDetailsScreen(
        trainerName = trainerName,
        exploreSteps = exploreSteps,
        testBluetoothOnClick = {
            viewModel.testBluetooth()
        },
        exploreOnClick = {
            viewModel.explore()
        },
        boxOnClick = boxOnClick,
        partyOnClick = partyOnClick,
        itemsOnClick = itemsOnClick
    )
}

@Composable
fun TrainerDetailsScreen(
    trainerName: String,
    exploreSteps: Int,
    testBluetoothOnClick: () -> Unit,
    exploreOnClick: () -> Unit,
    boxOnClick: () -> Unit,
    partyOnClick: () -> Unit,
    itemsOnClick: () -> Unit
) {
    SmartPokeWalkerTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background_gray)),
            contentAlignment = Alignment.Center
        ) {
            val trainerCardHeader = stringResource(R.string.trainer_card)
            CurvedLayout {
                curvedText(
                    text = trainerCardHeader.uppercase(getDefault()),
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    ),
                    angularDirection = CurvedDirection.Angular.Normal
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 0.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = trainerName,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    )
                )
                Text(
                    text = "Refreshing Field",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    )
                )
                Button(
                    label = {
                        Text(
                            text = stringResource(R.string.explore_button, exploreSteps, MonsterModel.MAX_EXPLORE_STEPS),
                            fontSize = 25.sp,
                            color = if (exploreSteps >= MonsterModel.MAX_EXPLORE_STEPS) {
                                colorResource(R.color.light_gray)
                            } else {
                                colorResource(R.color.dark_gray)
                            },
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    onClick = exploreOnClick,
                    enabled = exploreSteps >= MonsterModel.MAX_EXPLORE_STEPS,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier
                )
                Button(
                    label = {
                        Text(
                            text = stringResource(R.string.box),
                            color = colorResource(R.color.background_gray),
                            fontSize = 25.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    onClick = boxOnClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier
                )
                Button(
                    label = {
                        Text(
                            text = stringResource(R.string.party),
                            color = colorResource(R.color.background_gray),
                            fontSize = 25.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    onClick = partyOnClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier
                )
                Button(
                    label = {
                        Text(
                            text = stringResource(R.string.items),
                            color = colorResource(R.color.background_gray),
                            fontSize = 25.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                    },
                    onClick = itemsOnClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier
                )
                Button(
                    label = {
                        Text(
                            text = "Test Bluetooth Server",
                            color = colorResource(R.color.background_gray)
                        )
                    },
                    onClick = testBluetoothOnClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun TrainerDetailsScreenPreview() {
    TrainerDetailsScreen(
        trainerName = "Red",
        exploreSteps = 499,
        testBluetoothOnClick = {},
        exploreOnClick = {},
        boxOnClick = {},
        partyOnClick = {},
        itemsOnClick = {}
    )
}