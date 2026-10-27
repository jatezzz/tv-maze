# tv-maze

An Android app that browses TV shows, episodes and cast through the public TVmaze API. Favorites are stored locally and the app is locked behind a PIN or fingerprint.

## Context

Built in October 2021 over three days (12 to 14 Oct).

## What it shows

- MVVM with Fragments, ViewModels and Data Binding, and Navigation Component for the screen graph.
- Hilt for dependency injection, organised by module (HTTP, Retrofit, repositories, services, ViewModels).
- A custom Retrofit `CallAdapter` that wraps every response in a `ResultType`, so errors are handled in one place.
- Coroutines with an injected IO dispatcher for the repository calls.
- Room for favorite shows, AndroidX Biometric for fingerprint unlock, and a secure-storage wrapper for the PIN.

## Stack

Kotlin, Android (min SDK 21, target SDK 30), Hilt, Retrofit, OkHttp, Gson, Coroutines, Room, Navigation, Glide, AndroidX Biometric.

## Run it

Open the project in Android Studio and run the `app` module on an emulator or device.

## Looking back (2026)

The tests are still the template placeholders. Today I would write the UI in Jetpack Compose, use Kotlin Flow and StateFlow instead of LiveData, use Paging 3 for the show list, and test the ViewModels against a fake repository.
