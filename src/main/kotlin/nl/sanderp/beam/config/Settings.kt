package nl.sanderp.beam.config

import com.typesafe.config.Config
import java.io.Serializable

data class Settings(
        val lyricsBaseUrl: String
) : Serializable

fun Config.parse() = Settings(
        lyricsBaseUrl = getString("lyrics.url")
)