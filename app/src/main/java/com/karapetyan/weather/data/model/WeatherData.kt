package com.karapetyan.weather.data.network.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.karapetyan.weather.data.db.Converters
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "weatherData")
@TypeConverters(Converters::class)
data class WeatherData(
    @Expose
    var base: String,
    @Embedded
    var clouds: Clouds,
    var cod: Int,
    @Embedded
    var coord: Coord,
    @Expose
    var dt: Int,
    @Expose
    var id: Int,
    @Embedded
    var main: Main,
    @PrimaryKey(autoGenerate = false)
    @Expose
    var name: String,
    @Embedded
    var sys: Sys,
    @Expose
    var timezone: Int,
    @Expose
    var visibility: Int,
    @Expose
    var weather: List<Weather>?,
    @Embedded
    var wind: Wind
) : Serializable {
    constructor() : this(
        "",
        Clouds(),
        0,
        Coord(),
        0,
        0,
        Main(),
        "",
        Sys(),
        0,
        0,
        emptyList(),
        Wind()
    )
}

data class Clouds(
    @Expose
    var all: Int
) : Serializable {
    constructor() : this(0)
}

data class Coord(
    @Expose
    var lat: Double,
    @Expose
    var lon: Double
) : Serializable {
    constructor() : this(0.0, 0.0)
}

data class Main(
    @Expose
    var feels_like: Double,
    @Expose
    var humidity: Int,
    @Expose
    var pressure: Int,
    @Expose
    var temp: Double,
    @Expose
    var temp_max: Double,
    @Expose
    var temp_min: Double
) : Serializable {
    constructor() : this(0.0, 0, 0, 0.0, 0.0, 0.0)
}

data class Sys(
    @Expose
    var country: String,
    @Expose
    @ColumnInfo(name = "id_sys")
    var id: Int,
    @Expose
    var sunrise: Int,
    @Expose
    var sunset: Int,
    @Expose
    var type: Int
) : Serializable {
    constructor() : this("", 0, 0, 0, 0)
}

data class Weather(
    @Expose
    var description: String,
    @Expose
    var icon: String,
    @Expose
    @ColumnInfo(name = "id_weather")
    var id: Int,
    @Expose
    var main: String
) : Serializable {
    constructor() : this("", "", 0, "")
}

data class Wind(
    @Expose
    var deg: Int,
    @Expose
    var gust: Double,
    @Expose
    var speed: Double
) : Serializable {
    constructor() : this(0, 0.0, 0.0)
}
