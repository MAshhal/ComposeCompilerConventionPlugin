import com.android.build.gradle.LibraryExtension
import com.mystic.buildlogic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

/**
 * Created using Android Studio
 * User: ashal
 * Date: 7/16/2024
 * Time: 8:56 PM
 */

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            pluginManager.apply(libs.plugins.android.library.get().pluginId)

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}