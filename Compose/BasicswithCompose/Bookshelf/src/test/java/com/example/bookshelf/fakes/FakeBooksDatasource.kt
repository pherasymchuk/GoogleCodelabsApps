package com.example.bookshelf.fakes

import com.example.bookshelf.data.AccessInfo
import com.example.bookshelf.data.BooksResponse
import com.example.bookshelf.data.Epub
import com.example.bookshelf.data.ImageLinks
import com.example.bookshelf.data.IndustryIdentifier
import com.example.bookshelf.data.Item
import com.example.bookshelf.data.PanelizationSummary
import com.example.bookshelf.data.Pdf
import com.example.bookshelf.data.ReadingModes
import com.example.bookshelf.data.VolumeInfo

object FakeBooksDatasource {
    fun getBooks(): BooksResponse {
        val response = BooksResponse(
            kind = "books#volumes",
            totalItems = 2,
            items = listOf(
                Item(
                    accessInfo = AccessInfo(
                        accessViewStatus = "SAMPLE_ACCESS_VIEW_STATUS_1",
                        country = "SAMPLE_COUNTRY_1",
                        embeddable = true,
                        epub = Epub(isAvailable = true),
                        pdf = Pdf(isAvailable = true),
                        publicDomain = false,
                        quoteSharingAllowed = true,
                        textToSpeechPermission = "SAMPLE_TEXT_TO_SPEECH_PERMISSION_1",
                        viewability = "SAMPLE_VIEWABILITY_1",
                        webReaderLink = "SAMPLE_WEB_READER_LINK_1"
                    ),
                    etag = "SAMPLE_ETAG_1",
                    id = "SAMPLE_ID_1",
                    kind = "books#volume",
                    selfLink = "SAMPLE_SELF_LINK_1",
                    volumeInfo = VolumeInfo(
                        allowAnonLogging = false,
                        authors = listOf("SAMPLE_AUTHOR_1"),
                        averageRating = 5,
                        canonicalVolumeLink = "SAMPLE_CANONICAL_VOLUME_LINK_1",
                        categories = listOf("SAMPLE_CATEGORY_1"),
                        contentVersion = "SAMPLE_CONTENT_VERSION_1",
                        description = "SAMPLE_DESCRIPTION_1",
                        imageLinks = ImageLinks(
                            smallThumbnail = "SAMPLE_SMALL_THUMBNAIL_1",
                            thumbnail = "SAMPLE_THUMBNAIL_1"
                        ),
                        industryIdentifiers = listOf(
                            IndustryIdentifier(
                                identifier = "SAMPLE_IDENTIFIER_1",
                                type = "SAMPLE_TYPE_1"
                            )
                        ),
                        infoLink = "SAMPLE_INFO_LINK_1",
                        language = "SAMPLE_LANGUAGE_1",
                        maturityRating = "SAMPLE_MATURITY_RATING_1",
                        pageCount = 100,
                        panelizationSummary = PanelizationSummary(
                            containsEpubBubbles = true,
                            containsImageBubbles = true
                        ),
                        previewLink = "SAMPLE_PREVIEW_LINK_1",
                        printType = "SAMPLE_PRINT_TYPE_1",
                        publishedDate = "SAMPLE_PUBLISHED_DATE_1",
                        publisher = "SAMPLE_PUBLISHER_1",
                        ratingsCount = 10,
                        readingModes = ReadingModes(
                            image = true,
                            text = true
                        ),
                        subtitle = "SAMPLE_SUBTITLE_1",
                        title = "SAMPLE_TITLE_1"
                    )
                ),
                // Repeat the above Item object for 4 more times with different data
                Item(
                    accessInfo = AccessInfo(
                        accessViewStatus = "SAMPLE_ACCESS_VIEW_STATUS_2",
                        country = "SAMPLE_COUNTRY_2",
                        embeddable = true,
                        epub = Epub(isAvailable = true),
                        pdf = Pdf(isAvailable = true),
                        publicDomain = false,
                        quoteSharingAllowed = true,
                        textToSpeechPermission = "SAMPLE_TEXT_TO_SPEECH_PERMISSION_2",
                        viewability = "SAMPLE_VIEWABILITY_2",
                        webReaderLink = "SAMPLE_WEB_READER_LINK_2"
                    ),
                    etag = "SAMPLE_ETAG_2",
                    id = "SAMPLE_ID_2",
                    kind = "books#volume",
                    selfLink = "SAMPLE_SELF_LINK_2",
                    volumeInfo = VolumeInfo(
                        allowAnonLogging = false,
                        authors = listOf("SAMPLE_AUTHOR_2"),
                        averageRating = 5,
                        canonicalVolumeLink = "SAMPLE_CANONICAL_VOLUME_LINK_2",
                        categories = listOf("SAMPLE_CATEGORY_2"),
                        contentVersion = "SAMPLE_CONTENT_VERSION_2",
                        description = "SAMPLE_DESCRIPTION_2",
                        imageLinks = ImageLinks(
                            smallThumbnail = "SAMPLE_SMALL_THUMBNAIL_2",
                            thumbnail = "SAMPLE_THUMBNAIL_2"
                        ),
                        industryIdentifiers = listOf(
                            IndustryIdentifier(
                                identifier = "SAMPLE_IDENTIFIER_2",
                                type = "SAMPLE_TYPE_2"
                            )
                        ),
                        infoLink = "SAMPLE_INFO_LINK_2",
                        language = "SAMPLE_LANGUAGE_2",
                        maturityRating = "SAMPLE_MATURITY_RATING_2",
                        pageCount = 100,
                        panelizationSummary = PanelizationSummary(
                            containsEpubBubbles = true,
                            containsImageBubbles = true
                        ),
                        previewLink = "SAMPLE_PREVIEW_LINK_2",
                        printType = "SAMPLE_PRINT_TYPE_2",
                        publishedDate = "SAMPLE_PUBLISHED_DATE_2",
                        publisher = "SAMPLE_PUBLISHER_2",
                        ratingsCount = 10,
                        readingModes = ReadingModes(
                            image = true,
                            text = true
                        ),
                        subtitle = "SAMPLE_SUBTITLE_2",
                        title = "SAMPLE_TITLE_2"
                    )
                ),
            )
        )
        return response
    }
}
