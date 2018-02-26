package nl.sanderp.beam.lyrics

import feign.Param
import feign.RequestLine
import java.util.*

interface LyricsService {

    @RequestLine("GET /v1/{artist}/{title}")
    fun findLyrics(
            @Param("artist") artist: String,
            @Param("title") title: String
    ): Optional<LyricsData>
}