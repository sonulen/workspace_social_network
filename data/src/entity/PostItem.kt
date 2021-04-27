import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostItem(
    @field:Json(name = "author") val author: Author,
    @field:Json(name = "id") val id: String,
    @field:Json(name = "image_url") val image_url: String,
    @field:Json(name = "lat") val lat: Double,
    @field:Json(name = "likes") val likes: Int,
    @field:Json(name = "lon") val lon: Double,
    @field:Json(name = "text") val text: String
)
