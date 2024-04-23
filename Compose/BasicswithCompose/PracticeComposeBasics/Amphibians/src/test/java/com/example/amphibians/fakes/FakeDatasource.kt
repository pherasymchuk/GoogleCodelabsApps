package com.example.amphibians.fakes

import com.example.amphibians.data.network.Amphibian

object FakeDatasource {
    val fakeAmphibians = listOf(
        Amphibian(
            name = "Great Basin Spadefoot",
            type = "Toad",
            description = "This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots.",
            imgSrc = "fake_url/great-basin-spadefoot.png"
        ),
        Amphibian(
            name = "Roraima Bush Toad",
            type = "Toad",
            description = "This toad is typically found in South America. Specifically on Mount Roraima at the borders of Venezuela, Brazil, and Guyana, hence the name. The Roraima Bush Toad is typically black with yellow spots or marbling along the throat and belly.",
            imgSrc = "fake_url/roraima-bush-toad.png"
        ),
        Amphibian(
            name = "Pacific Chorus Frog",
            type = "Frog",
            description = "Also known as the Pacific Treefrog, it is the most common frog on the Pacific Coast of North America. These frogs can vary in color between green and brown and can be identified by a brown stripe that runs from their nostril, through the eye, to the ear.",
            imgSrc = "fake_url/pacific-chorus-frog.png"
        )
    )

}
