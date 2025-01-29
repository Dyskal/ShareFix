@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.tasks.PackageAndroidArtifact
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "me.dyskal.sharefix"
    compileSdk = 35

    defaultConfig {
        applicationId = "me.dyskal.sharefix"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.1"
        signingConfig = signingConfigs.getByName("debug")
    }

    flavorDimensions += "api"

    productFlavors {
        create("api26") {
            dimension = "api"
        }
        create("api32") {
            dimension = "api"
            minSdk = 32
            isDefault = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            vcsInfo.include = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
            progressiveMode = true
            extraWarnings = true
        }
        jvmToolchain(21)
    }

    packaging {
        resources.excludes += listOf("**/META-INF/**", "**/kotlin/**", "kotlin-tooling-metadata.json")
    }
}

tasks.withType<PackageAndroidArtifact> {
    doFirst { appMetadata.asFile.get().writeText("") }
}
