import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class ClockMessage(val msg: String) {
  fun toJson() = Json.encodeToString(serializer(), this)

  companion object {
    fun fromJson(json: String) =
      Json.decodeFromString<ClockMessage>(kotlinx.serialization.serializer(), json)
  }
}

