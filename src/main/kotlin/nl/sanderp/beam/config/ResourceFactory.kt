package nl.sanderp.beam.config

import feign.Feign
import feign.gson.GsonDecoder
import feign.optionals.OptionalDecoder
import nl.sanderp.beam.lyrics.LyricsDao
import nl.sanderp.beam.lyrics.LyricsService
import java.io.Serializable
import java.util.concurrent.ConcurrentHashMap

open class ResourceFactory(private val config: Settings) : Serializable {

    @Transient
    private var cache: ConcurrentHashMap<Int, Any>? = ConcurrentHashMap()

    /**
     * Get a resource with the specified [Key] from the cache.
     * The resource will be created if it does not exist yet.
     */
    fun <T: Any> get(key: Key<T>): T {
        // Because the cache is transient, it might not be initialized at this point
        if (cache == null) {
            cache = ConcurrentHashMap()
        }
        // Since we never reassign cache anywhere else, it is 'safe' to enforce non-nullability
        return cache!!.computeIfAbsent(key.hashCode()) { this.create(key) } as T
    }

    /**
     * Create a new resource for the provided [Key]
     */
    open fun <T: Any> create(key: Key<T>) = key.create(this)

    /**
     * Resource keys supported by [ResourceFactory]
     *
     * Use the `create` lambda to define how to create a new instance of the target resource.
     * Note that you can use [ResourceFactory.get] to wire in other resources.
     * Also note that there is no protection against you recursively getting the same [Key], so don't do that.
     */
    sealed class Key<out T: Any>(val create: (ResourceFactory) -> T) {

        object LYRICS_SERVICE: Key<LyricsService>({ resourceFactory ->
            Feign
                    .builder()
                    .decode404()
                    .decoder(OptionalDecoder(GsonDecoder()))
                    .target(LyricsService::class.java, resourceFactory.config.lyricsBaseUrl)
        })

        object LYRICS_DAO: Key<LyricsDao>({ resourceFactory ->
            LyricsDao(lyricsService = resourceFactory.get(LYRICS_SERVICE))
        })

    }
}