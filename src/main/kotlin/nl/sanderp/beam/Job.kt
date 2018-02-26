package nl.sanderp.beam

import nl.sanderp.beam.config.ResourceFactory
import nl.sanderp.beam.domain.Song
import nl.sanderp.beam.functions.LyricsEnricher
import org.apache.beam.sdk.Pipeline
import org.apache.beam.sdk.transforms.Create
import org.apache.beam.sdk.transforms.DoFn
import org.apache.beam.sdk.transforms.ParDo
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val log: Logger = LoggerFactory.getLogger("Job")

class Job(private val resourceFactory: ResourceFactory) {

    fun run() {
        val pipeline = Pipeline.create()

        pipeline.apply("Read input", input())
                .apply("Enrich with stuff", ParDo.of(enrichWithLyrics()))
                .apply("Log output", ParDo.of(outputLogger()))

        pipeline.run()
                .waitUntilFinish()
    }

    private fun input() = Create.of(
            Song(title = "We Will Rock You", artist = "Queen"),
            Song(title = "Happy", artist = "Pharrell Williams"),
            Song(title = "Technologic", artist = "Daft Punk", lyrics = "Buy It Use It Break It Fix It...")
    )

    private fun enrichWithLyrics() = LyricsEnricher(resourceFactory)

    private fun outputLogger() = object : DoFn<Song, Void>() {
        @ProcessElement
        fun processElement(context: ProcessContext) {
            log.info("Received ${context.element()}")
        }
    }
}