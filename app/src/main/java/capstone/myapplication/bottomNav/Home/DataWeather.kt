package capstone.myapplication.bottomNav.Home

import org.json.JSONException
import org.json.JSONObject

object DataWeather {

    private var city: String? = null
    private var weather: String? = null
    private var lon: String? = null
    private var lat: String? = null
    private var icon: String? = null
    private var temperature: String? = null
    private var humidity: String? = null

    fun fromJson(dataObject: JSONObject): DataWeather {
        /*try {
            val weatherD = DataWeather
            weatherD.city = dataObject.getString("name")
            weatherD.lon = dataObject.getDouble("lon").toString()
            weatherD.lat = dataObject.getDouble("lat").toString()
            weatherD.weather = dataObject.getJSONArray("weather").getJSONObject(0).getString("main")
            weatherD.icon = dataObject.getJSONArray("weather").getJSONObject(0).getString("icon")
            val temp = dataObject.getJSONObject("main").getDouble("temp") - 273.15
            val roundedValue = Math.rint(temp).toInt()
            weatherD.temperature = Integer.toString(roundedValue)
            weatherD.humidity = dataObject.getJSONObject("main").getInt("humidity").toString()
            return weatherD
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return DataWeather*/
        val weatherD = DataWeather
        weatherD.city = dataObject.getString("name")
        weatherD.lon = dataObject.getJSONObject("coord").getDouble("lon").toString()
        weatherD.lat = dataObject.getJSONObject("coord").getDouble("lat").toString()
        weatherD.weather = dataObject.getJSONArray("weather").getJSONObject(0).getString("main")
        weatherD.icon = dataObject.getJSONArray("weather").getJSONObject(0).getString("icon")
        val temp = dataObject.getJSONObject("main").getDouble("temp") - 273.15
        val roundedValue = Math.rint(temp).toInt()
        weatherD.temperature = Integer.toString(roundedValue)
        weatherD.humidity = dataObject.getJSONObject("main").getInt("humidity").toString()
        return weatherD
    }

    fun getCity(): String? = city

    fun getWeather(): String? = weather

    fun getLon(): String? = lon

    fun getLat(): String? = lat

    fun getIcon(): String = icon  + "@2x.png"

    fun getTemperature(): String? = temperature

    fun getHumidity(): String = humidity + "%"
}