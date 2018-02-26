package nl.sanderp.beam.functions

import nl.sanderp.beam.config.ResourceFactory
import nl.sanderp.beam.domain.Song
import nl.sanderp.beam.lyrics.LyricsDao
import org.apache.beam.sdk.transforms.DoFn

class LyricsEnricher(private val resourceFactory: ResourceFactory) : DoFn<Song, Song>() {

    lateinit private var dao: LyricsDao

    @Setup
    fun setup() {
        dao = resourceFactory.get(ResourceFactory.Key.LYRICS_DAO)
    }

    @ProcessElement
    fun processElement(context: ProcessContext) {
        val song = context.element()
        context.output(if (song.lyrics.isNullOrEmpty()) withLyrics(song) else song)
    }

    private fun withLyrics(song: Song) = song.copy(lyrics = dao.findLyrics(title = song.title, artist = song.artist))
}