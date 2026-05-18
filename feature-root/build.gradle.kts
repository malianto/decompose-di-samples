plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.metro)
}

android {
    namespace = "com.example.myapplication.root"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    jvmToolchain(jdkVersion = libs.versions.jvmTarget.get().toInt())
}

dependencies {
    implementation(project(":feature-list"))
    implementation(project(":feature-details"))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.decompose.decompose)
    implementation(libs.decompose.extensionsCompose)

    testImplementation(project(":repository"))
    testImplementation(libs.kotlin.test)
}
