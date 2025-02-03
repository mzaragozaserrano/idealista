plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.mzaragozaserrano.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(project(":domain"))

    //-----  GSON ----------/
    implementation(libs.gson)

    //-----  JSON PARSER ----------/
    implementation(libs.moshi.kotlin)

    //----- KOIN ----------/
    implementation(libs.koin.android)

    //----- OKHTTP ----------/
    implementation(libs.okhttp.logging)

    //----- ROOM ----------/
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

}