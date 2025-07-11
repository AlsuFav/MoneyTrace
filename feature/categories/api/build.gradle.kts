plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "ru.fav.moneytrace.categories.api"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()

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
        sourceCompatibility = JavaVersion.valueOf(
            libs.versions.compatibility.source
                .get(),
        )
        targetCompatibility = JavaVersion.valueOf(
            libs.versions.compatibility.target
                .get(),
        )
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":common:util"))
    implementation(project(":feature:transaction:api"))
    implementation(project(":feature:account:api"))

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
}