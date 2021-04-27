import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Author(
    @field:Json(name = "avatar_url") val avatar_url: String,
    @field:Json(name = "birth_day") val birth_day: String,
    @field:Json(name = "first_name") val first_name: String,
    @field:Json(name = "id") val id: String,
    @field:Json(name = "last_name") val last_name: String,
    @field:Json(name = "nickname") val nickname: String
)
