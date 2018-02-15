package nl.sanderp.beam

import org.apache.beam.sdk.Pipeline
import org.apache.beam.sdk.options.PipelineOptionsFactory
import org.apache.beam.sdk.transforms.Count
import org.apache.beam.sdk.transforms.Create
import org.apache.beam.sdk.transforms.DoFn
import org.apache.beam.sdk.transforms.ParDo
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger("Application")

/**
 * Application entrypoint
 */
fun main(args: Array<String>) {
    log.info("Starting Beam application")

    val pipeline = Pipeline.create(PipelineOptionsFactory.fromArgs(*args).create())

    pipeline.apply("Read input data", Create.of("Hello", "World"))
            .apply("Count words", Count.globally())
            .apply("Output", ParDo.of(object : DoFn<Long, Void>() {
                @ProcessElement
                fun processElement(context: ProcessContext) {
                    log.info("Count: ${context.element()}")
                }
            }))

    // make sure to wait until the pipeline completes
    pipeline.run()
            .waitUntilFinish()

}