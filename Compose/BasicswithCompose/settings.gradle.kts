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
include(":PracticeComposeBasics:ComposeArticle")
include(":PracticeComposeBasics:TaskManager")
include(":PracticeComposeBasics:ComposeQuadrant")
include(":PracticeComposeBasics:BusinessCard")
include(":Diceroller")
include(":Lemonade")
include(":TipTime")
include(":Art")
include(":Affirmations")
