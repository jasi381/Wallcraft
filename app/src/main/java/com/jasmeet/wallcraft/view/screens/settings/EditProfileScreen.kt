package com.jasmeet.wallcraft.view.screens.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.view.appComponents.InputFieldComponent2
import com.jasmeet.wallcraft.view.appComponents.LoadingButton
import com.jasmeet.wallcraft.view.appComponents.LottieComponent
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.EditProfileScreen(
    animatedVisibilityScope: AnimatedContentScope,
    onBackClick: () -> Unit,
    loginViewModel: LoginSignUpViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        loginViewModel.getUserInfo()
    }

    val error = loginViewModel.errorState.collectAsState()
    val isLoading = loginViewModel.isLoading.collectAsState()
    val userInfo = loginViewModel.userInfo.collectAsState()
    val updateSuccess by loginViewModel.updateSuccess.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }

    val name = userInfo.value?.name ?: ""
    var editableName by remember(name) { mutableStateOf(name) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val clipboard = LocalClipboardManager.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(error.value) {
        error.value?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short,
                withDismissAction = true,
                actionLabel = "Dismiss"
            )
        }
    }


    LaunchedEffect(updateSuccess) {
        updateSuccess?.let {
            showSuccessDialog = true
            delay(4000)
            showSuccessDialog = false
        }
    }



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, modifier = Modifier.clickable {
                val errorMessage = error.value


                clipboard.setText(AnnotatedString(errorMessage.toString()))


            })
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "editProfile"),
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 18.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .padding(8.dp)
            ) {
                // Profile Image
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .clickable { launcher.launch("image/*") }
                ) {
                    AsyncImage(
                        model = imageUri ?: userInfo.value?.imgUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.img_placeholder)
                    )
                }

                // Edit Icon
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground)
                        .clickable { launcher.launch("image/*") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Picture",
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            TextComponent(
                text = "Name",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                textSize = 19.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Name TextField
            InputFieldComponent2(
                value = editableName,
                onValueChange = { editableName = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Name",
                enabled = !isLoading.value
            )


            Spacer(modifier = Modifier.height(22.dp))


            TextComponent(
                text = "Email",
                modifier = Modifier.align(Alignment.Start),
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                textSize = 19.sp,

                )

            Spacer(modifier = Modifier.height(6.dp))

            // Email TextField (Disabled)
            InputFieldComponent2(
                value = userInfo.value?.email ?: "",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                placeholder = "Email"
            )

            Spacer(modifier = Modifier.height(55.dp))

            // Update Button
            LoadingButton(
                onClick = {
                    loginViewModel.updateUserInfo(imageUri, editableName)
                },
                loading = isLoading.value,
                text = "Update",
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            )
        }
    }
    if (showSuccessDialog) {
        TransparentSuccessDialog()
    }
}

@Composable
fun TransparentSuccessDialog() {

    Box(
        modifier = Modifier
            .clickable(enabled = false) { }
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieComponent(rawRes = R.raw.ic_done, speed = 0.2f, modifier = Modifier.size(70.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Profile Updated Successfully!!",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = poppins
            )
        }
    }

}