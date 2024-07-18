# ComposeCompilerConventionPlugin

A guide to implement compose compiler gradle plugin in multi-module project using convention plugins.

> [!IMPORTANT]
> Before proceeding, make sure your kotlin version is at least 2.0.0

> ## You can clone the repo and see the differences between `main` and `before` branch to see the changes directly.
> `git diff before main`

You can follow the guide to migrate as well.

- Add the compose compiler gradle plugin in your `libs.versions.toml` file
```toml
# In [plugins] block
compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
```
- Apply the plugin in your project's build.gradle file.

```kotlin
alias(libs.plugins.compose) apply false 
```


- Add the Compose Compiler Gradle Plugin in your :build-logic (convention plugins module) dependencies block

```kotlin
compileOnly(libs.compose.compiler.gradlePlugin)
```

#

- In your `AndroidApplicationComposeConventionPlugin` or `AndroidLibraryComposeConventionPlugin`, add the following lines:
> [!NOTE]
> Checkout [ApplicationPlugin](/build-logic/convention/src/main/kotlin/AndroidApplicationComposeConventionPlugin.kt) or [LibraryPlugin](/build-logic/convention/src/main/kotlin/AndroidLibraryComposeConventionPlugin.kt) for complete implementation

```kotlin
  pluginManager.apply(libs.plugins.compose.get().pluginId)
  val composeExtension = extensions.getByType<ComposeCompilerGradlePluginExtension>()
```

- You can apply any changes you want with the `composeExtension` as follows
```kotlin
    composeExtension.apply {
        enableStrongSkippingMode.set(true)
        configureComposeMetricsAndReportsDestinationDirectory(this)
    }
```
# 
- I've also included an extension function for configuring compose metrics and reports destination directories with the new compiler as the compiler doesn't uses `freeCompilerArgs` anymore.

```kotlin
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
```

#
- I've also updated the plugins to use `compilerOptions` on the `KotlinCompile` extension since `kotlinOptions` has been deprecated.
- You can check that out in [KotlinAndroid.kt](/build-logic/convention/src/main/kotlin/com/mystic/buildlogic/KotlinAndroid.kt)
