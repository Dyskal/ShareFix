@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.tasks.PackageAndroidArtifact

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "me.dyskal.sharefix"
    compileSdk = 37

    defaultConfig {
        applicationId = "me.dyskal.sharefix"
        minSdk = 26
        targetSdk = 37
        versionCode = 4
        versionName = "1.4"
        base.archivesName = rootProject.name
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

    signingConfigs {
        create("release") {
            if (System.getenv("KEYSTORE_FILE") != null) {
                storeFile = file(System.getenv("KEYSTORE_FILE"))
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
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
            signingConfig = signingConfigs.getByName("release")
        }
    }

    packaging {
        resources.excludes += listOf(
            "**/META-INF/**",
            "**/kotlin/**",
            "kotlin-tooling-metadata.json",
        )
    }
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        progressiveMode = true
        extraWarnings = true
    }
}

tasks.withType<PackageAndroidArtifact> {
    doFirst {
        appMetadata.asFile.orNull?.writeText("")
    }
}
