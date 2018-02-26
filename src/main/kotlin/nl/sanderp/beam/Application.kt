package nl.sanderp.beam

import com.typesafe.config.ConfigFactory
import nl.sanderp.beam.config.ResourceFactory
import nl.sanderp.beam.config.parse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val log: Logger = LoggerFactory.getLogger("Application")

/**
 * Application entrypoint
 */
fun main(args: Array<String>) {
    log.info("Starting Beam application")

    val settings = ConfigFactory.load().parse()
    val resourceFactory = ResourceFactory(settings)

    val job = Job(resourceFactory)
    job.run()
}