# Better Sleep
![image](https://img.shields.io/github/issues/nursyah21/bettersleep) ![image](https://img.shields.io/github/license/nursyah21/bettersleep)


## Getting Started

### prerequisite

- [nodejs](https://nodejs.org/en/) (recommend version lts 16.0)
- [java jdk](https://adoptopenjdk.net/) (recommend openjdk 11, jvm hotspot)
- [yarn](https://yarnpkg.com/getting-started/install)
- [gradle](https://gradle.org/install/)
- [expo-cli](https://docs.expo.dev/workflow/expo-cli/)
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
## change app.json for configuration your app
## file apk in android/app/build/outputs/apk/release/app-release.apk
```


### what to build
[ ] get time 20:30
[ ] set a alarm
[ ] figure out to make it rotate
[ ] 
