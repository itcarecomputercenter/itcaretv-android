plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.itcaretv.app'
    compileSdk 34
    buildToolsVersion '34.0.0'
    ndkVersion '25.2.9519653'

    defaultConfig {
        applicationId "com.itcaretv.app"
        minSdk 21
        targetSdk 34
        versionCode 1
        versionName "1.0.0"

        vectorDrawables {
            useSupportLibrary false
        }
    }

    buildTypes {
        release {
            minifyEnabled true
            zipAlignEnabled true
            // ProGuard: keep WebView JS interfaces if we add any later
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
        debug {
            minifyEnabled false
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = '17'
    }

    // Only build APK, not App Bundle
    buildFeatures {
        buildConfig true
    }
}

dependencies {
    implementation 'androidx.core:core-ktx:1.12.0'
}
