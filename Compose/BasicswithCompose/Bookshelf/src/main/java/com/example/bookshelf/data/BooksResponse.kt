package com.example.bookshelf.data

import kotlinx.serialization.Serializable

@Serializable
data class BooksResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<Item>,
)

@Serializable
data class Item(
    val accessInfo: AccessInfo,
    val etag: String,
    val id: String,
    val kind: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo,
)

@Serializable
data class AccessInfo(
    val accessViewStatus: String,
    val country: String,
    val embeddable: Boolean,
    val epub: Epub,
    val pdf: Pdf,
    val publicDomain: Boolean,
    val quoteSharingAllowed: Boolean,
    val textToSpeechPermission: String,
    val viewability: String,
    val webReaderLink: String,
)

@Serializable
data class VolumeInfo(
    val allowAnonLogging: Boolean = false,
    val authors: List<String>? = null,
    val averageRating: Int = -1,
    val canonicalVolumeLink: String = "",
    val categories: List<String>? = null,
    val contentVersion: String = "",
    val description: String? = null,
    val imageLinks: ImageLinks? = null,
    val industryIdentifiers: List<IndustryIdentifier>? = null,
    val infoLink: String = "",
    val language: String = "",
    val maturityRating: String = "",
    val pageCount: Int = -1,
    val panelizationSummary: PanelizationSummary? = null,
    val previewLink: String = "",
    val printType: String = "",
    val publishedDate: String = "",
    val publisher: String = "",
    val ratingsCount: Int = -1,
    val readingModes: ReadingModes? = null,
    val subtitle: String = "",
    val title: String = "",
)

@Serializable
data class Epub(
    val acsTokenLink: String? = null,
    val isAvailable: Boolean,
    val downloadLink: String? = null,
)

@Serializable
data class Pdf(
    val acsTokenLink: String? = null,
    val isAvailable: Boolean,
)

@Serializable
data class ListPriceX(
    val amountInMicros: Int,
    val currencyCode: String,
)

@Serializable
data class RetailPrice(
    val amountInMicros: Int,
    val currencyCode: String,
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
)

@Serializable
data class IndustryIdentifier(
    val identifier: String,
    val type: String,
)

@Serializable
data class PanelizationSummary(
    val containsEpubBubbles: Boolean,
    val containsImageBubbles: Boolean,
)

@Serializable
data class ReadingModes(
    val image: Boolean,
    val text: Boolean,
)
