import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.hiltAndroid)
    id("com.google.gms.google-services")

}



tasks.register("incrementVersion") {
    doLast {
        val versionFile = file("version.properties")
        val props = Properties()
        props.load(versionFile.inputStream())

        val versionCode = props["VERSION_CODE"].toString().toInt() + 1
        val versionName = "1.0.$versionCode"

        props["VERSION_CODE"] = versionCode.toString()
        props["VERSION_NAME"] = versionName

        props.store(versionFile.outputStream(), null)
        println("✅ Updated VERSION_CODE to $versionCode and VERSION_NAME to $versionName")
    }
}

val versionPropsFile = rootProject.file("app/version.properties")
val versionProps = Properties().apply {
    load(FileInputStream(versionPropsFile))
}

val versionCodeNumber = versionProps["VERSION_CODE"].toString().toInt()
val versionNameNumber = versionProps["VERSION_NAME"].toString()

android {
    namespace = "com.salem.androidtesting"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.salem.androidtesting"
        minSdk = 24
        targetSdk = 35
        versionCode = versionCodeNumber
        versionName = versionNameNumber

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    signingConfigs {
        create("release") {
            if (project.hasProperty("MYAPP_UPLOAD_STORE_FILE")) {
                storeFile = file(project.property("MYAPP_UPLOAD_STORE_FILE") as String)
                storePassword = project.property("MYAPP_UPLOAD_STORE_PASSWORD") as String
                keyAlias = project.property("MYAPP_UPLOAD_KEY_ALIAS") as String
                keyPassword = project.property("MYAPP_UPLOAD_KEY_PASSWORD") as String
            }
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)

    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)


    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.kotlinx.coroutines.test)


    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)


    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")


    // view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")


    testImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation(kotlin("test"))
    testImplementation("app.cash.turbine:turbine:1.2.1")


    testImplementation("org.amshove.kluent:kluent-android:1.72")


    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
    implementation("com.google.firebase:firebase-analytics")

}


ksp {
    arg("dagger.hilt.disableModulesHaveInstallInCheck", "true")
}