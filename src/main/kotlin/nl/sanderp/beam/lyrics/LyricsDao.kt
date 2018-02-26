package nl.sanderp.beam.lyrics

class LyricsDao(private val lyricsService: LyricsService) {

    fun findLyrics(title: String, artist: String): String? {
        return lyricsService
                .findLyrics(artist = artist, title = title)
                .map { it.lyrics }
                .orElse(null)
    }
}