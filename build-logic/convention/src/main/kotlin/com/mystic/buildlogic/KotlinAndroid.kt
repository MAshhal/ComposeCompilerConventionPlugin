package com.mystic.buildlogic

import com.android.build.api.dsl.CommonExtension
import libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Created using Android Studio
 * User: ashal
 * Date: 7/16/2024
 * Time: 8:59 PM
 */


internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {

    commonExtension.apply {
        compileSdk = libs.versions.compileSdk.get().toInt()
        defaultConfig { minSdk = libs.versions.minSdk.get().toInt() } // My default is 21

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }
    }

    configureKotlin()

    dependencies {
	// required for using java.time classes before api 26
        add("coreLibraryDesugaring", libs.android.desugarJdkLibs.get())
    }

}

internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    configureKotlin()
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)

            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningAsErrors=true in your ~/.gradle/gradle.properties
            val warningAsErrors: String? by project
            allWarningsAsErrors.set(warningAsErrors.toBoolean())
            val compilerArgs = listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview"
            )
            freeCompilerArgs.addAll(compilerArgs)
        }
    }
}