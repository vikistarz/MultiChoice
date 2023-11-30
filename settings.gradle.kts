pluginManagement {
    repositories {
        google()
        google()
        jcenter()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        jcenter()
        mavenCentral()
//        maven{
//            url = "https://jitpack.io"
//        }

    }
}

rootProject.name = "MulitChoice"
include(":app")
 