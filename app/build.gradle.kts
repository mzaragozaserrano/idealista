plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.mzaragozaserrano.idealista"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mzaragozaserrano.idealista"
        minSdk = 31
        versionCode = 1
        versionName = "1.0.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    //----- MODULES ----------/
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    //----- ANDROIDX ----------/
    androidTestImplementation(libs.androidx.ui.test.junit4)

    //----- KOIN ----------/
    implementation(libs.koin.android)

    //----- KOTLINX ----------/
    implementation(libs.kotlinx.coroutines.android)

    //----- TESTING ----------/
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.room.testing)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockk)
    testImplementation(libs.room.runtime)
    testImplementation(libs.turbine)

}