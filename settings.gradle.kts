pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://artifactory.2gis.dev/sdk-maven-release") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://artifactory.2gis.dev/sdk-maven-release") }
    }
}

rootProject.name = "GEKATA_mobile"
include(":app")
