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
include(":Courses")
include(":Woof")
include(":Superheroes")
include(":PracticeComposeBasics:30days")
include(":DessertClicker")
include(":Unscramble")
include(":Cupcake")
include(":PracticeComposeBasics:LunchTray")
include(":Reply")
include(":PracticeComposeBasics:Sports")
include(":KotlinUtils")
include(":RaceTracker")
include(":MarsPhotos")
include(":PracticeComposeBasics:Amphibians")
include(":bookshelf")
