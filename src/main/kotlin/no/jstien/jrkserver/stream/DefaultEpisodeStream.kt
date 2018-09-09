package no.jstien.jrkserver.stream

import no.jstien.jrkserver.episodes.Episode
import no.jstien.jrkserver.episodes.EpisodeSegment

class DefaultEpisodeStream(episode: Episode): EpisodeStream {
    private val episode: Episode = episode
    private var startTime = Double.MIN_VALUE

    override fun setStartAvailability(startTime: Double) {
        this.startTime = startTime
    }

    override fun getAvailableSegments(availabilitySecondsInterval: Double, currentTime: Double): List<EpisodeSegment> {
        verifyStartTimeDefined()

        val list = ArrayList<EpisodeSegment>()

        // Find the first segment to inlcude
        var time = startTime
        var index = 0
        while (index < episode.segmentCount && time + episode.segments[index].length < currentTime) {
            time += episode.segments[index].length
            index++
        }

        val endTime = currentTime + availabilitySecondsInterval.toDouble()
        while (time < endTime && index < episode.segmentCount) {
            list.add(episode.segments[index])
            time += episode.segments[index].length
            index++
        }

        return list
    }

    fun getRemainingTime(currentTime: Double): Double {
        verifyStartTimeDefined()
        val episodeLength = episode.length.toLong()
        return (startTime + episodeLength) - currentTime
    }

    private fun verifyStartTimeDefined() {
        if (startTime == Double.MIN_VALUE) {
            throw RuntimeException("setStartAvailability must be called before getAvailableSegments")
        }
    }

}