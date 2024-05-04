package com.jasmeet.wallcraft

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canopas.lib.showcase.IntroShowcase
import com.canopas.lib.showcase.IntroShowcaseScope
import com.canopas.lib.showcase.component.IntroShowcaseState
import com.canopas.lib.showcase.component.ShowcaseStyle
import com.canopas.lib.showcase.component.rememberIntroShowcaseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowcaseSample() {
    var showAppIntro by remember {
        mutableStateOf(true)
    }

    val introShowcaseState = rememberIntroShowcaseState()

    IntroShowcase(
        showIntroShowCase = showAppIntro,
        dismissOnClickOutside = false,
        onShowCaseCompleted = {
            //App Intro finished!!
            showAppIntro = false
        },
        state = introShowcaseState,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        BackButton(introShowcaseState)
                    },
                    actions = {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.introShowCaseTarget(
                                index = 0,
                                style = ShowcaseStyle.Default.copy(
                                    backgroundColor = Color(0xFF9AD0EC), // specify color of background
                                    backgroundAlpha = 0.98f, // specify transparency of background
                                    targetCircleColor = Color.White // specify color of target circle
                                ),
                                content = {
                                    Column {
                                        Icon(
                                            Icons.Filled.Search,
                                            contentDescription = "Search",
                                            modifier = Modifier.size(100.dp)
                                        )

                                        Text(
                                            text = "Search anything!!",
                                            color = Color.Black,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "You can search anything by clicking here.",
                                            color = Color.Black,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            )
                        ) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingMailButton()
            }
        ) {
            Content(Modifier.padding(it))
        }
    }
}


@Composable
fun IntroShowcaseScope.FloatingMailButton() {
    FloatingActionButton(
        onClick = {},
        modifier = Modifier.introShowCaseTarget(
            index = 1,
            style = ShowcaseStyle.Default.copy(
                backgroundColor = Color(0xFF1C0A00), // specify color of background
                backgroundAlpha = 0.98f, // specify transparency of background
                targetCircleColor = Color.White // specify color of target circle
            ),
            // specify the content to show to introduce app feature
            content = {
                Column {
                    Text(
                        text = "Check emails",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Click here to check/send emails",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.End),
                        tint = Color.White
                    )
                }
            }
        ),
        containerColor = Color.Blue,
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(6.dp)
    ) {
        Icon(
            Icons.Filled.Email,
            contentDescription = "Email"
        )
    }

}

@Composable
fun IntroShowcaseScope.BackButton(introShowcaseState: IntroShowcaseState) {
    IconButton(
        onClick = {},
        modifier = Modifier.introShowCaseTarget(
            index = 4,
            style = ShowcaseStyle.Default.copy(
                backgroundColor = Color(0xFF7C99AC), // specify color of background
                backgroundAlpha = 0.98f, // specify transparency of background
                targetCircleColor = Color.White // specify color of target circle
            ),
            content = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(top = 10.dp)
                    )
                    Column {
                        Text(
                            text = "Go back!!",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "You can go back by clicking here.",
                            color = Color.White,
                            fontSize = 16.sp
                        )

                        Button(
                            onClick = {
                                // Used to restart the intro showcase
                                introShowcaseState.reset()
                            },
                        ) {
                            Text(text = "Restart Intro")
                        }
                    }
                }
            },
        )
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Search")
    }
}

@Composable
fun IntroShowcaseScope.Content(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxHeight(0.3f)) {

            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(90.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Intro Showcase view", fontWeight = FontWeight.Bold,
                    fontSize = 24.sp, color = Color.Cyan
                )
                Text(
                    text = "This is an example of Intro Showcase view",
                    fontSize = 20.sp, color = Color.Black, textAlign = TextAlign.Center
                )

            }

            Image(
                painter = painterResource(id = R.drawable.ic_unknown_profile),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .clip(CircleShape)
                    .introShowCaseTarget(
                        index = 2, // specify index to show feature in order
                        // ShowcaseStyle is optional
                        style = ShowcaseStyle.Default.copy(
                            backgroundColor = Color(0xFFFFCC80), // specify color of background
                            backgroundAlpha = 0.98f, // specify transparency of background
                            targetCircleColor = Color.White // specify color of target circle
                        ),
                        content = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp)
                            ) {
                                Text(
                                    text = "User profile",
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Click here to update your profile",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    )
            )
        }

        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp)
                .introShowCaseTarget(
                    index = 3,
                    content = {
                        Column {
                            Text(
                                text = "Follow me",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Click here to follow",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                )
        ) {
            Text(text = "Follow")
        }
    }

}