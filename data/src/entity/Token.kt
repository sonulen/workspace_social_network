import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
    @field:Json(name = "access_token") val access_token: String,
    @field:Json(name = "refresh_token") val refresh_token: String
)
