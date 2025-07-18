plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.safe.args)
    alias(libs.plugins.gradle.secrets)
}

android {
    namespace = "ru.fav.moneytrace"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "ru.fav.moneytrace"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        versionCode = rootProject.extra.get("versionCode") as Int
        versionName = rootProject.extra.get("versionName") as String

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
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(path = ":common:domain"))
    implementation(project(path = ":common:data"))
    implementation(project(path = ":common:ui"))
    implementation(project(path = ":common:navigation"))
    implementation(project(path = ":common:util"))
    implementation(project(path = ":common:network"))

    implementation(project(path = ":feature:transaction:api"))
    implementation(project(path = ":feature:transaction:impl"))
    implementation(project(path = ":feature:account:api"))
    implementation(project(path = ":feature:account:impl"))
    implementation(project(path = ":feature:settings:impl"))
    implementation(project(path = ":feature:income:impl"))
    implementation(project(path = ":feature:categories:api"))
    implementation(project(path = ":feature:categories:impl"))
    implementation(project(path = ":feature:expenses:impl"))
    implementation(project(path = ":feature:analysis:impl"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.splash)
    implementation(libs.lotti)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.hilt)
    implementation(libs.hilt.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.ksp)
}