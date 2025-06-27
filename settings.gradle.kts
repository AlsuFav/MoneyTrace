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
include(":feature:account")
include(":feature:expenses")
include(":feature:income")
include(":core:navigation")
include(":feature:stats")
include(":core:data")
include(":common:ui")
include(":common:navigation")
include(":common:domain")
include(":common:data")
include(":common:util")
include(":common:network")
include(":feature:categories")
include(":feature:transaction:api")
include(":feature:transaction:impl")
include(":feature:account:api")
include(":feature:account:impl")
include(":feature:categories:impl")
include(":feature:expenses:impl")
include(":feature:income:impl")
include(":feature:settings:impl")
