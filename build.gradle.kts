// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.2.3")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.0")
    }

}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.kotlinAndroidKsp) apply false
    id("com.google.gms.google-services") version "4.4.3" apply false
}