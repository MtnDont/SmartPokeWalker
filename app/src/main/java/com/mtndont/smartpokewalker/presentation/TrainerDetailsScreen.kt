package com.mtndont.smartpokewalker.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.compose.layout.ColumnItemType
import com.google.android.horologist.compose.layout.rememberResponsiveColumnPadding
import com.mtndont.smartpokewalker.R
import com.mtndont.smartpokewalker.data.MonsterModel
import com.mtndont.smartpokewalker.presentation.theme.SmartPokeWalkerTheme
import kotlin.math.floor
import androidx.compose.ui.platform.LocalResources

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
    exploreOnClick: () -> Unit,
    boxOnClick: () -> Unit,
    partyOnClick: () -> Unit,
    itemsOnClick: () -> Unit
) {
    SmartPokeWalkerTheme {
        val resources = LocalResources.current

        val listState = rememberTransformingLazyColumnState(
            initialAnchorItemIndex = 0,
            initialAnchorItemScrollOffset = (0.25f * resources.displayMetrics.heightPixels).toInt()
        )
        val transformationSpec = rememberTransformationSpec()

        ScreenScaffold(
            scrollState = listState,
            contentPadding = rememberResponsiveColumnPadding(
                first = ColumnItemType.ListHeader,
                last = ColumnItemType.IconButton
            ),
            modifier = Modifier.fillMaxSize()
                .background(colorResource(R.color.background_gray))
        ) { contentPadding ->
            TransformingLazyColumn(
                state = listState,
                contentPadding = contentPadding,
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 15.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.trainer_card),
                            fontSize = 20.sp,
                            color = colorResource(R.color.dark_gray),
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                        Text(
                            text = trainerName,
                            fontSize = 28.sp,
                            color = colorResource(R.color.black),
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                        Text(
                            text = "Refreshing Field",
                            color = colorResource(R.color.dark_gray),
                            fontSize = 20.sp,
                            fontFamily = FontFamily(
                                Font(R.font.pixelfont)
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            LinearProgressIndicator(
                                progress = {
                                    // 20 visual steps
                                    0.05f * floor((exploreSteps.toFloat()/MonsterModel.MAX_EXPLORE_STEPS) / 0.05f)
                                },
                                color = colorResource(R.color.black),
                                trackColor = colorResource(R.color.light_gray),
                                strokeCap = StrokeCap.Round,
                                gapSize = (-15).dp,
                                drawStopIndicator = {},
                                modifier = Modifier
                                    .fillMaxWidth(0.55f)
                                    .weight(0.55f)
                                    .padding(end = 2.dp)
                            )
                            Text(
                                text = "${exploreSteps}w/${MonsterModel.MAX_EXPLORE_STEPS}w",
                                color = colorResource(R.color.dark_gray),
                                fontSize = 18.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.pixelfont)
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(0.45f)
                            )
                        }
                    }
                }
                item {
                    Button(
                        label = {
                            Text(
                                text = stringResource(R.string.explore),
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
                            .fillMaxWidth(0.8f)
                            .transformedHeight(this, transformationSpec),
                        transformation = SurfaceTransformation(transformationSpec)
                    )
                }
                item {
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
                            .fillMaxWidth(0.8f)
                            .transformedHeight(this, transformationSpec),
                        transformation = SurfaceTransformation(transformationSpec)
                    )
                }
                item {
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
                            .fillMaxWidth(0.8f)
                            .transformedHeight(this, transformationSpec),
                        transformation = SurfaceTransformation(transformationSpec)
                    )
                }
                item {
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
                            .fillMaxWidth(0.8f)
                            .transformedHeight(this, transformationSpec),
                        transformation = SurfaceTransformation(transformationSpec)
                    )
                }
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
        exploreOnClick = {},
        boxOnClick = {},
        partyOnClick = {},
        itemsOnClick = {}
    )
}