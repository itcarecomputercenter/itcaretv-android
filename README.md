# ITCareTV Android App

WebView wrapper for [itcaretv.top](https://itcaretv.top). Loads the full web app in a native Android shell.

## Download

Get the latest APK from the [Releases](https://github.com/ITCaretv/itcaretv-android/releases) page.

## Build

```bash
./gradlew assembleDebug    # debug APK
./gradlew assembleRelease  # release APK
```

## Features

- Full-screen WebView loading itcaretv.top
- Hardware-accelerated video playback
- Offline screen when no network
- Back button navigation
- Android TV launcher support (D-pad)
- Deep link handling for itcaretv.top URLs
- Cleartext traffic disabled (HTTPS only)

## Requirements

- Android 5.0+ (API 21)
- Internet connection
