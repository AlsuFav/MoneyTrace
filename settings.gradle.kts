pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MoneyTrace"
include(":app")
include(":core:base")
include(":core:base-feature")
include(":core:domain")
include(":feature:settings")
include(":feature:articles")
include(":feature:account")
include(":feature:expenses")
include(":feature:income")
include(":core:navigation")
include(":feature:stats")
include(":core:data")
