import com.android.build.api.dsl.ApplicationExtension
import com.mystic.buildlogic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

/**
 * Created using Android Studio
 * User: ashal
 * Date: 7/16/2024
 * Time: 2:03 PM
 */

class AndroidApplicationComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.plugins.android.application.get().pluginId)
            pluginManager.apply(libs.plugins.compose.get().pluginId) // Add this line

            val extension = extensions.getByType<ApplicationExtension>()
            val composeExtension = extensions.getByType<ComposeCompilerGradlePluginExtension>() // Add this line

            configureAndroidCompose(extension, composeExtension) // Pass the extension to the function
        }
    }
}