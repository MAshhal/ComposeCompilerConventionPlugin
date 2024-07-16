import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.mystic.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    // https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location)) // LibrariesForLibs internal implementation access in code
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "mystic.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        // Redacted
        
	register("androidLibraryCompose") {
            id = "mystic.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
	
	// Redacted
	
    }
}