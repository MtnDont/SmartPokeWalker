package com.mtndont.smartpokewalker.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.toUpperCase
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
import com.mtndont.smartpokewalker.presentation.theme.SmartPokeWalkerTheme
import java.util.Locale
import java.util.Locale.getDefault

@Composable
fun TrainerDetailsApp(
    viewModel: TrainerDetailsScreenViewModel = hiltViewModel()
) {
    val trainerName by viewModel.trainerName.collectAsStateWithLifecycle()
    TrainerDetailsScreen(
        trainerName = trainerName,
        addMonsterOnClick = {
            viewModel.createNewMonster()
        },
        addPartyOnClick = {
            viewModel.addMonstersToParty()
        }
    )
}

@Composable
fun TrainerDetailsScreen(
    trainerName: String,
    addMonsterOnClick: () -> Unit,
    addPartyOnClick: () -> Unit
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
                modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 0.dp)
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
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(
                        Font(R.font.pixelfont)
                    )
                )
                Button(
                    onClick = addMonsterOnClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier
                ) {
                    Text(
                        text = "Add Pokemon",
                        color = colorResource(R.color.background_gray)
                    )
                }
                Button(
                    onClick = addPartyOnClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier
                ) {
                    Text(
                        text = "Add to Party",
                        color = colorResource(R.color.background_gray)
                    )
                }
            }
        }
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun TrainerDetailsScreenPreview() {
    TrainerDetailsScreen(
        trainerName = "Red",
        addMonsterOnClick = {},
        addPartyOnClick = {}
    )
}