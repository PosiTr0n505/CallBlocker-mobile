# CallBlocker

An Android application designed to block unwanted calls using customizable phone number prefixes. So far only valid for french phones cuz that's where i live and need it, There is the possibility of adding other prefixes for other countries as well in the future, all you need to do is ask and I will add it. So far no one has done that, I'll update the readme if anyone does (Idk if anyone will read this far, but if you did, Hello <3).

## Features

- **Prefix-based blocking:** Block incoming calls based on their phone number prefixes.
- **Custom prefixes:** Add and manage the prefixes you want to block.
- **Call screening:** Automatically screen incoming calls matching your configured prefixes.
- **[Additional features]:** None for the moment.

## Installation

### Option 1: Download the APK

You can install CallBlocker directly without using the Google Play Store.

1. Go to the releases page.
2. Download the latest APK file.
3. Open the downloaded APK on your Android device.
4. If prompted, allow your browser or file manager to install unknown apps.
5. Follow the installation instructions.

**Note:** Android may display a security warning when installing applications from outside the Play Store. Only install APKs from sources you trust.

### Option 2: Build from source

**Requirements**
- Android Studio
- Android SDK
- Git

**Steps**

1. Clone the repository:

#### using ssh:
   ```bash
   git clone git@github.com:PosiTr0n505/CallBlocker-mobile.git
   ```
#### using HTTPS:
  ```bash
    git clone https://github.com/PosiTr0n505/CallBlocker-mobile.git
  ```

2. Open the project in Android Studio.

3. Allow Gradle to synchronize and download the required dependencies.

4. Connect an Android device or start an emulator.

5. Build and run the application using Android Studio.

Alternatively, build the debug APK from the terminal:

```bash
./gradlew assembleDebug
```

The generated APK can be found at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

To install it using ADB:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Permissions

CallBlocker requires call screening permissions to identify and block incoming calls.

The application will request the necessary permissions when needed.

## Contributing

Contributions, suggestions and bug reports are welcome.

Feel free to open an issue or submit a pull request.

## License

(something something something here)

Have a nice day if you read all the way till here :)

