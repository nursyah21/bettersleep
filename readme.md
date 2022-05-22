# Better Sleep

## Getting Started

### prerequisite

- [nodejs](https://nodejs.org/en/) (recommend version latest 16.0)
- [java jdk](https://adoptopenjdk.net/) (recommend openjdk 11, jvm hotspot)
- [yarn](https://yarnpkg.com/getting-started/install)
- [gradle](https://gradle.org/install/)
- [android sdk](https://developer.android.com/studio/) (install android studio to get android sdk)

set environment
```bash
export ANDROID_SDK_ROOT=~/Android/Sdk
export PATH=$PATH:$ANDROID_SDK_ROOT/emulator
export PATH=$PATH:$ANDROID_SDK_ROOT/platform-tools

export PATH=$HOME/Downloads/jdk-11.0.15+10/bin/:$PATH
export PATH=$HOME/Downloads/node-v16.15.0-linux-x64/bin:$PATH
```
development app
```bash
yarn install
yarn android
```
build app
```bash
expo prebuild
cd android && ./gradlew build
## file apk in android/app/build/outputs/apk/release/app-release.apk
```


