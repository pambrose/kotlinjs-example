import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class KeystrokeMessage(val msg: String) {
  fun toJson() = Json.encodeToString(serializer(), this)

  companion object {
    fun fromJson(json: String) =
      Json.decodeFromString<KeystrokeMessage>(kotlinx.serialization.serializer(), json)
  }
}

