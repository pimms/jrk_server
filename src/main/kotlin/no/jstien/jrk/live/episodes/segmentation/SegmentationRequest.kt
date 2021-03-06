package no.jstien.jrk.live.episodes.segmentation

class SegmentationRequest(desiredSegmentLength: Int, filePath: String, s3Key: String) {
    val desiredSegmentLength: Int = desiredSegmentLength
    val filePath: String = filePath
    val s3Key: String = s3Key
}
