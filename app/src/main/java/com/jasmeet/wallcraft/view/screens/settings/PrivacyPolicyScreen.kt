package com.jasmeet.wallcraft.view.screens.settings

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.view.theme.poppins

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.PrivacyPolicyScreen(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedContentScope,
    onBackClick: () -> Unit
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Privacy Policy",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "privacy_policy"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(500)
                            }
                        )
                    )


                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        androidx.compose.material3.Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )

                    }
                }
            )
        }
    ) {
        PrivacyPolicyContent(modifier = modifier.padding(it))
    }
}

@Composable
fun PrivacyPolicyContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Introduction",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            textDecoration = TextDecoration.Underline,
            fontFamily = poppins
        )
        Text(
            text = "Welcome to Wallcraft. This privacy policy explains how we collect, use, and protect your personal information when you use our app.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = poppins
        )

        Text(
            text = "Information Collection and Use",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
            textDecoration = TextDecoration.Underline,
            fontFamily = poppins
        )
        Text(
            text = "We collect information that you provide directly to us, such as when you create an account or contact us for support. We also collect information automatically as you use our app, including usage data and device information.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = poppins

        )

        Text(
            text = "Third-Party Services",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
            textDecoration = TextDecoration.Underline,
            fontFamily = poppins
        )
        Text(
            text = "We may use third-party services to provide certain features or services within our app. These third-party services may collect information about you in accordance with their own privacy policies.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = poppins
        )

        Text(
            text = "Data Security",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
            textDecoration = TextDecoration.Underline,
            fontFamily = poppins
        )
        Text(
            text = "We take reasonable measures to protect your information from unauthorized access, disclosure, or misuse. However, no security measures are perfect, and we cannot guarantee the security of your information.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = poppins
        )

        Text(
            text = "Your Rights",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
            textDecoration = TextDecoration.Underline,
            fontFamily = poppins
        )
        Text(
            text = "You have the right to access, update, or delete your personal information. You may also have the right to object to or restrict certain processing activities. To exercise these rights, please contact us at [Your Contact Email].",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = poppins
        )

        Text(
            text = "Changes to This Policy",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
            textDecoration = TextDecoration.Underline,
            fontFamily = poppins
        )
        Text(
            text = "We may update this privacy policy from time to time. We will notify you of any changes by posting the new privacy policy on this page. You are advised to review this policy periodically for any changes.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = poppins
        )

        Text(
            text = "Contact Us",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
            textDecoration = TextDecoration.Underline,
            fontFamily = poppins
        )
        Text(
            text = "If you have any questions about this privacy policy, please contact us at sjasmeet438@gmail.com.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = poppins
        )

        Text(
            text = "Last updated: 17 July 2024 ",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(androidx.compose.ui.Alignment.End),
            fontFamily = poppins,
            textDecoration = TextDecoration.Underline
        )
    }
}