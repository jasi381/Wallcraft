plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.secrets.plugin)
    alias(libs.plugins.kotlinKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.gmsService)
    alias(libs.plugins.compose.compiler)
}

android {


    signingConfigs {
        create("release") {
            storeFile = file("C:\\Users\\User\\Desktop\\Wallcraft\\keyStore.jks")
            storePassword = "jasmeet34"
            keyAlias = "release"
            keyPassword = "jasmeet34"
        }
    }
    namespace = "com.jasmeet.wallcraft"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jasmeet.wallcraft"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
composeCompiler {
    enableStrongSkippingMode = true
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.runtime.livedata)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Design
    implementation(libs.androidx.material3)
    implementation(libs.googleFonts)
    implementation(libs.materialIcons)
    implementation(libs.lottie)

    //shared Transition
    implementation(libs.androidx.animation)
    implementation(libs.androidx.foundation)


    // navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.composeloading)
    implementation(libs.androidx.constraintlayout.compose)

    //hilt
    implementation(libs.hiltAndroid)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hiltCompiler)
    ksp(libs.hiltCompilerKapt)

    //room
    implementation(libs.room)
    implementation(libs.roomKtx)
    ksp(libs.roomCompiler)
    implementation(libs.androidx.room.paging)

    //network
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.compose)
    implementation (libs.converter.gson)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)

    //qr code generator
    implementation("io.github.alexzhirkevich:qrose:1.0.1")

    // Mockito for mocking dependencies
    testImplementation("org.mockito:mockito-core:3.12.4")

    // Mockito Kotlin for better Kotlin support
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    // MockK
    testImplementation("io.mockk:mockk-android:1.13.11")
    testImplementation("io.mockk:mockk-agent:1.13.11")

    // For testing Kotlin coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // For testing Robolectric
    testImplementation("org.robolectric:robolectric:4.8.1")

    // For Android Architecture Components testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")
}