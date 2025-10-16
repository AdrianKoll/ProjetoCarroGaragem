plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.koll.meu_app_car"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.koll.meu_app_car"
        minSdk = 24
        targetSdk = 36 // ou a última versão disponível no seu SDK Manager
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // UI
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0") // 👈 resolve o erro de tema

    // Kotlin coroutines (para Dispatchers.IO e launch)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // instrumentation tests (androidTest)
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // for plain unit tests (if you have src/test)
    testImplementation("junit:junit:4.13.2")

}