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

rootProject.name = "Basics with Compose"
include(":Greetings")
include(":PracticeComposeBasics")
include(":PracticeComposeBasics:composearticle")
include(":PracticeComposeBasics:taskmanager")
include(":PracticeComposeBasics:composequadrant")
include(":PracticeComposeBasics:businesscard")
include(":diceroller")
