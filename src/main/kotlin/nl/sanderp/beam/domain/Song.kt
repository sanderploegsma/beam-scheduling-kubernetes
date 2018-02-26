package nl.sanderp.beam.domain

import org.apache.beam.sdk.coders.DefaultCoder
import org.apache.beam.sdk.coders.SerializableCoder
import java.io.Serializable

@DefaultCoder(SerializableCoder::class)
data class Song(
        val title: String,
        val artist: String,
        val lyrics: String? = null
) : Serializable