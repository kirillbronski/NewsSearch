import java.io.FileInputStream
import java.io.IOException
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.kbcoding.newssearch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kbcoding.newssearch"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val localPropertiesFile = File("local.properties")
        val localProperties = Properties()

        try {
            FileInputStream(localPropertiesFile).use { fileInputStream ->
                localProperties.load(fileInputStream)
                val apiKey = localProperties.getProperty("NEWS_API_KEY")
                buildConfigField("String", "NEWS_API_KEY", "\"${apiKey}\"")
            }
        } catch (e: IOException) {
            println("Error reading local.properties file: ${e.message}")
        }

        buildConfigField("String", "NEWS_BASE_URL", "\"https://newsapi.org/v2/\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(projects.newsApi)
    implementation(projects.database)
    implementation(projects.newsData)
    implementation(projects.features.mainScreen)
    implementation(projects.core.common)
    implementation(projects.core.uiKit)
    implementation(projects.core.commonImpl)

    compileOnly(libs.retrofit.adapters.result)
    debugImplementation(libs.logging.interceptor)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

}