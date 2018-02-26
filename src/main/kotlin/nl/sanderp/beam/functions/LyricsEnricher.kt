package nl.sanderp.beam.functions

import nl.sanderp.beam.config.ResourceFactory
import nl.sanderp.beam.domain.Song
import nl.sanderp.beam.lyrics.LyricsDao
import org.apache.beam.sdk.transforms.DoFn

class LyricsEnricher(private val resourceFactory: ResourceFactory) : DoFn<Song, Song>() {

    private lateinit var dao: LyricsDao

    @Setup
    fun setup() {
        dao = resourceFactory.get(ResourceFactory.Key.LYRICS_DAO)
    }

    @ProcessElement
    fun processElement(context: ProcessContext) {
        val song = context.element()
        context.output(song.withLyrics())
    }

    private fun Song.withLyrics() = if (!lyrics.isNullOrEmpty()) this else copy(lyrics = dao.findLyrics(title = title, artist = artist))
}