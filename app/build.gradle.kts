plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.koin.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.mtndont.smartpokewalker"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

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

    useLibrary("wear-sdk")

    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            listOf(
                "-Xcontext-parameters",
                "-XXLanguage:+PropertyParamAnnotationDefaultTargetMode"
            )
        )
    }
}

koinCompiler {
    compileSafety = true    // Default: true — validates dependency graph
    //userLogs = true         // See what components are detected
}

/**
 * Extracts images from NDS ROM into res/ directory
 */
androidComponents {
    onVariants { variant ->
        val variantName = variant.name.replaceFirstChar { it.uppercase() }
        tasks.matching { it.name == "merge${variantName}Resources" }.configureEach {
            dependsOn(tasks.named("extractImageBinaries"))
        }
    }
}

tasks.register<Exec>("extractImageBinaries") {
    group = "python"
    description = "Runs a Python script safely to extract images from a ROM."

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
    implementation(libs.compose.wear.material)
    implementation(libs.compose.foundation)
    implementation(libs.androidx.room)
    implementation(libs.androidx.compose.navigation)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.annotations)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.compose.navigation3)
    implementation(libs.koin.core)
    implementation(libs.koin.core.viewmodel)

    implementation(libs.wear.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.tiles)
    implementation(libs.tiles.material)
    implementation(libs.tiles.tooling.preview)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.horologist.compose.tools)
    implementation(libs.horologist.tiles)
    implementation(libs.accompanist.drawablepainter)
    implementation(libs.watchface.complications.data.source.ktx)
    implementation(libs.horologist.compose.layout)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.tiles.tooling)
}