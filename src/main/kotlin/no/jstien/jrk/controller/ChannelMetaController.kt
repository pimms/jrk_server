package no.jstien.jrk.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

private data class ChannelMeta(
    val playlistURL: String,
    val streamName: String,
    val streamPictureURL: String,

    val playlist: String,
    val streamPicture: String,

    val nowPlaying: String,
    val nowPlayingURL: String
)

@RestController
@RequestMapping("/")
class ChannelMetaController {
    @Value("\${channel.name}")
    private var channelName: String = "n/a"

    @GetMapping
    fun getChannelMeta(request: HttpServletRequest): ResponseEntity<Any> {
        val playlist = "live/playlist.m3u8"
        val streamPicture = "streamPicture"
        val nowPlaying = "live/nowPlaying"

        val rootUrl = request.requestURL.toString()
        val liveUrl = rootUrl + playlist
        val pictureUrl = rootUrl + streamPicture
        val nowPlayingURL = rootUrl + nowPlaying

        val channelMeta = ChannelMeta(liveUrl, channelName, pictureUrl, "/$playlist", "/$streamPicture", "/$nowPlaying", nowPlayingURL)
        return ResponseEntity.ok(channelMeta)
    }

    @GetMapping("/streamPicture")
    fun getStreamPicture(): ResponseEntity<ByteArray> {
        val path = "stream-picture.png"
        val res = ClassPathResource(path)
        val bytes = res.inputStream.readBytes()

        val headers = HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return ResponseEntity(bytes, headers, HttpStatus.OK)
    }

}