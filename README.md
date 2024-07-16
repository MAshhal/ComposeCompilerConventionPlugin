# ComposeCompilerConventionPlugin

A guide to implement compose compiler gradle plugin in multi-module project using convention plugins.

> [!IMPORTANT]
> Before proceeding, make sure your kotlin version is at least 2.0.0

> ## You can clone the repo and see the differences between `main` and `before` branch to see the changes directly.

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

In your `ApplicationConventionPlugin`, 
