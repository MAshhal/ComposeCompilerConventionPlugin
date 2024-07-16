import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

/**
 * Created using Android Studio
 * User: ashal
 * Date: 7/16/2024
 * Time: 8:56 PM
 */


//internal val Project.libs : LibrariesForLibs
//    get() = (this as ExtensionAware).extensions.getByName("libs") as LibrariesForLibs

internal val Project.libs: LibrariesForLibs
    get() = this.the<LibrariesForLibs>()