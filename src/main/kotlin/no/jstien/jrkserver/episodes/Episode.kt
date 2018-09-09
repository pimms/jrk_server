package no.jstien.jrkserver.episodes

import no.jstien.jrkserver.util.recursiveDelete
import org.apache.logging.log4j.LogManager
import org.hibernate.validator.internal.util.CollectionHelper
import java.io.File


class Episode(rootDirectory: String, segments: List<EpisodeSegment>) {
    companion object {
        private val LOGGER = LogManager.getLogger()
        const val TARGET_SEGMENT_DURATION = 10
    }

    val segmentCount: Int
        get() = segments.size

    val length: Double
        get() = segments.sumByDouble { it.length }

    val segments = CollectionHelper.toImmutableList(segments)

    private val rootDirectory = rootDirectory

    fun getSegment(index: Int): EpisodeSegment {
        return segments.get(index)
    }

    fun cleanUp() {
        LOGGER.info("Cleaning up episode directory $rootDirectory")
        segments.forEach { s -> s.close() }
        recursiveDelete(File(rootDirectory))
    }
}
