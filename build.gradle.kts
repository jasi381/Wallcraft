// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.secrets.plugin) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.kotlinKsp) apply false
    alias(libs.plugins.gmsService) apply false
}
buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}