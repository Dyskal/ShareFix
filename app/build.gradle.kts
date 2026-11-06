@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.tasks.PackageAndroidArtifact

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "me.dyskal.sharefix"
    compileSdk = 37

    defaultConfig {
        minSdk = 33
        targetSdk = 37
        versionCode = 10
        versionName = "2.0"
        base.archivesName = rootProject.name
    }

    signingConfigs {
        create("release") {
            if (System.getenv("KEYSTORE_FILE") != null) {
                storeFile = file(System.getenv("KEYSTORE_FILE"))
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            } else {
                initWith(getByName("debug"))
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
            "app-metadata.properties",
        )
    }
}

tasks.withType<PackageAndroidArtifact> {
    doFirst {
        appMetadata.asFile.orNull?.writeText("")
    }
}
