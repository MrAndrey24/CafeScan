This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* [/app/shared](./app/shared/src) is for Compose Multiplatform UI code shared across target applications.
  - [commonMain](./app/shared/src/commonMain/kotlin) is for UI code common to all targets.
* [/sharedLogic](./sharedLogic/src) is for shared data, domain models, database, network, and business logic.
* [/app/androidApp](./app/androidApp) contains the Android application entry point.
* [/app/iosApp](./app/iosApp) contains the iOS Xcode application project.
* [/app/desktopApp](./app/desktopApp) contains the Desktop (JVM) application entry point.

### Build and Run Android Application

To build and run the development version of the Android app:
- on macOS/Linux
  ```shell
  ./gradlew :app:androidApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :app:androidApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…