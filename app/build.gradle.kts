plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    // Apply the Kotlin Parcelize plugin
    id("kotlin-parcelize")
    id("kotlin-android")
}

android {
    namespace = "com.phuocleaf.gaminggearmobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.phuocleaf.gaminggearmobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
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
    androidTestImplementation(libs.androidx.espresso.core)

    //navigation

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //glider
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    //Model mvvm

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("android.arch.lifecycle:extensions:1.1.1")

    //paper
    implementation ("io.github.pilgr:paperdb:2.7.2")

    // shared preferences
    implementation ("androidx.preference:preference-ktx:1.2.1")

    //gson
    implementation ("com.google.code.gson:gson:2.10")

    //couroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    // ma
    implementation ("com.google.android.material:material:1.6.0")
}