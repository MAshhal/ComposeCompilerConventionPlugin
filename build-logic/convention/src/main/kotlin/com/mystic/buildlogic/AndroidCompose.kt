package com.mystic.buildlogic

import com.android.build.api.dsl.CommonExtension
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

/**
 * Created using Android Studio
 * User: ashal
 * Date: 7/16/2024
 * Time: 8:58 PM
 */


internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    composeExtension: ComposeCompilerGradlePluginExtension // Add this
) {
    
    // Here u can configure your compose compiler parameters

    composeExtension.apply {
        enableStrongSkippingMode.set(true)
        configureComposeMetricsAndReportsDestinationDirectory(this)
    }

    commonExtension.apply {
        buildFeatures { compose = true }
        dependencies {
            val bom = libs.androidx.compose.bom.get()
            "implementation"(platform(bom))
            "androidTestImplementation"(platform(bom))
            "implementation"(libs.androidx.compose.ui.tooling.preview.get())
            "debugImplementation"(libs.androidx.compose.ui.tooling.asProvider().get())
        }
    }
}

private fun Project.configureComposeMetricsAndReportsDestinationDirectory(
    extension: ComposeCompilerGradlePluginExtension
) {
    val relativePath = projectDir.relativeTo(rootDir)
    val buildDir = layout.buildDirectory.get().asFile

    val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
    val enableMetrics = (enableMetricsProvider.orNull == "true")

    if (enableMetrics) {
        val folder = buildDir.resolve("compose-metrics").resolve(relativePath)
        extension.metricsDestination.set(folder)
    }

    val enableReportsProvider = project.providers.gradleProperty("enableComposeCompilerReports")
    val enableReports = (enableReportsProvider.orNull == "true")
    if (enableReports) {
        val folder = buildDir.resolve("compose-reports").resolve(relativePath)
        extension.reportsDestination.set(folder)
    }
}