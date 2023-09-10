package example.lizardo.sklocationex.data.model


data class Location(
    var latitude: Double? = null,
    var longitude: Double? = null,
    var direction: Float?= null,
    var date: String?= null,
    var battery: Float?= null
)
