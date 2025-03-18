import java.util.Properties

// Read from local.properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize") //Added to Parcelize, by serializing and de-serializing
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.modulus_labs_dev_test"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.modulus_labs_dev_test"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // Add POKEAPI_BASE_URL from local.properties to BuildConfig
        val pokeApiBaseUrl: String = localProperties["POKEAPI_BASE_URL"] as String
        buildConfigField("String", "POKEAPI_BASE_URL", "\"$pokeApiBaseUrl\"")

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    //JUnit for Unit Testing
    testImplementation(libs.junit)

    //MockK for mocking dependencies
    testImplementation(libs.mockk)

    //Coroutines Testing
    testImplementation(libs.kotlinx.coroutines.test)

    //LiveData Testing
    testImplementation(libs.androidx.core.testing)

    //Navigation
    implementation(libs.androidx.navigation.fragment)

    //Constraint Layout
    implementation(libs.androidx.constraintlayout)

    //RecyclerView
    implementation(libs.androidx.recyclerview)
    // For control over item selection of both touch and mouse driven selection
    implementation(libs.androidx.recyclerview.selection)

    //Fragment
    implementation(libs.androidx.fragment.ktx)

    //Compose ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Network Calls
    implementation(libs.retrofit)

    //Mapping JSON files to Kotlin Objects
    implementation(libs.converter.gson)

    //Image loading
    implementation(libs.coil.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}