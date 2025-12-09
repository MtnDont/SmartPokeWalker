import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.room)
}

android {
    namespace = "com.mtndont.smartpokewalker"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mtndont.smartpokewalker"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
            freeCompilerArgs.addAll(
                listOf(
                    "-Xcontext-parameters",
                    "-XXLanguage:+PropertyParamAnnotationDefaultTargetMode"
                )
            )
        }
    }
    useLibrary("wear-sdk")
    buildFeatures {
        compose = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

/**
 * Extracts images from NDS ROM into res/ directory
 */
android.applicationVariants.all {
    val variantName = name.replaceFirstChar { it.uppercase() }
    val mergeResourcesTask = tasks.named("merge${variantName}Resources")

    mergeResourcesTask.configure {
        dependsOn(tasks.named("extractImageBinaries"))
    }
}

tasks.register<Exec>("extractImageBinaries") {
    group = "python"
    description = "Runs a Python script safely (Gradle 8 compatible)."

    workingDir = rootDir
    commandLine("python", "scripts/extract_walker_images.py", "rom.nds", "-o", "app/src/main/res/raw", "-s")
}

dependencies {

    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.androidx.room)
    implementation(libs.androidx.compose.navigation)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    implementation(libs.wear.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.tiles)
    implementation(libs.tiles.material)
    implementation(libs.tiles.tooling.preview)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.health.services)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.work.ktx)
    implementation(libs.androidx.work)
    implementation(libs.horologist.compose.tools)
    implementation(libs.horologist.tiles)
    implementation(libs.coil3.kt.gif)
    implementation(libs.coil3.kt.compose)
    implementation(libs.apng4android)
    implementation(libs.accompanist.drawablepainter)
    implementation(libs.watchface.complications.data.source.ktx)
    implementation(libs.horologist.compose.layout)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.tiles.tooling)
}