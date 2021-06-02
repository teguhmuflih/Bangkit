package capstone.myapplication.bottomNav.Home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceActivity
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import capstone.myapplication.BuildConfig
import capstone.myapplication.R
import capstone.myapplication.databinding.FragmentHomeBinding
import capstone.myapplication.databinding.ItemWeatherBinding
import capstone.myapplication.view.ArticleActivity
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*

class HomeFragment : Fragment(), AdapterView.OnItemClickListener {

    private lateinit var locationManager: LocationManager
    private lateinit var binding: ItemWeatherBinding
    private lateinit var homeBinding: FragmentHomeBinding
    private var mContext: Context? = null
    private lateinit var cityName: String
    private lateinit var aqi: String
    private lateinit var adapter: SimpleAdapter
    private lateinit var map: HashMap<String, String>
    private lateinit var mylist: ArrayList<HashMap<String, String>>
    private lateinit var title: Array<String>
    private lateinit var caption: Array<String>
    private lateinit var image: Array<String>
    private lateinit var link: Array<String>


    companion object{
        private var city = " "
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if (context is Activity) {
            this.listener = context as HomeFragment
        }*/
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding = FragmentHomeBinding.bind(view)
        binding = homeBinding.itemWeather

        binding.location.visibility = View.INVISIBLE
        binding.weather.visibility = View.INVISIBLE

        addArticle()

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 101
            )
        }

        //locationEnable()
        //getLocation()
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        locationEnable(locationManager)
        homeBinding.listView.setOnItemClickListener(this)
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if (mContext != null){
                val geocoder = Geocoder(mContext, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                //cityName = addresses[0].adminArea
                cityName = addresses[0].subLocality
                //Toast.makeText(context, cityName, Toast.LENGTH_SHORT).show()
                //Log.d("HomeFragment", cityName)
                getParams(cityName)
            }
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun addArticle() {
        title = arrayOf(
            "Pests and their Negative Effects",
            "Importance of Humidity for Plant Growth",
            "8 Ways to Control Common Garden Pests"
        )
        caption = arrayOf(
            "Pests are defined as any harmful, noxious or troublesome organism. Common pests include insects, mites, rodents, fungi and weeds that wreak havoc on people, plants, animals or the ecology in general.",
            "Humidity is important to make photosynthesis possible. In the case of Anthurium, good humidity around the plant is even more important than for most other crops, because the plant can only absorb a reduced amount of humidity and hence has less water evaporation than most plants.",
            "Summer means the garden is bursting with flowers, fruits and vegetables, but it also means the unwelcome arrival of the garden pest. Here are our techniques for controlling common garden pests including snails, slugs, aphides and caterpillars."
        )
        image = arrayOf(
            Integer.toString(R.drawable.satu),
            Integer.toString(R.drawable.dua),
            Integer.toString(R.drawable.tiga)
        )
        link = arrayOf(
            "https://onetwotree.com/pests-and-their-negative-effects/",
            "https://www.anthura.nl/growing-advise/82466/?lang=en&cookies=not_ok",
            "https://www.topline.ie/blog/project-ideas/8-ways-to-control-common-garden-pests"
        )
        mylist = ArrayList()

        for (i in 0 until title.size) {
            map = HashMap()
            map["Title"] = title[i]
            map["Caption"] = caption[i]
            map["Photo"] = image[i]
            map["Link"] = link[i]
            mylist.add(map)
        }
        adapter = SimpleAdapter(activity, mylist, R.layout.item_list, arrayOf("Title", "Caption", "Photo"), intArrayOf(R.id.title_article, R.id.date_article, R.id.image_article))
        homeBinding.listView.setAdapter(adapter)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val getPosition = link.get(position)
        val intent = Intent(activity, ArticleActivity::class.java)
        intent.putExtra(ArticleActivity.link, getPosition)
        startActivity(intent)
    }

    private fun locationEnable(lm: LocationManager) {
        //val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled = false
        var network_enabled = false
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (!gps_enabled && !network_enabled) {
            AlertDialog.Builder(requireActivity())
                .setTitle("Enable GPS Service")
                .setMessage("We need your GPS location to show Near Places around you.")
                .setCancelable(false)
                .setPositiveButton("Enable") { _, _ -> startActivity(
                    Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    )
                ) }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    /*fun getLocation(){
        try {
            locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                500,
                5f,
                (this as LocationListener)
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }*/

    /*override fun onLocationChanged(location: Location) {
        try {
            val geocoder = Geocoder(requireActivity(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            //cityName = addresses[0].adminArea
            cityName = addresses[0].subLocality
            //Toast.makeText(context, cityName, Toast.LENGTH_SHORT).show()
            //Log.d("HomeFragment", cityName)
            getParams(cityName)
        } catch (e: java.lang.Exception) {
        }
    }*/

    override fun onResume() {
        super.onResume()
        if(city != null){
            getParams(city)
        } else {
            //getLocation()
            locationListener
        }
    }

    override fun onPause() {
        super.onPause()
        city = binding.location.text.toString()
        Log.d("HomeFragment", binding.location.text.toString())
    }
    /*override fun onResume() {
        super.onResume()
        if(cityName != null){
            getParams(cityName)
        }
    }*/

    private fun getParams(cityName: String) {
        val params = RequestParams()
        params.put("q", cityName)
        params.put("appid", BuildConfig.API_KEY)
        getWeatherData(params)
    }

    private fun getWeatherData(params: RequestParams) {
        val client = AsyncHttpClient()
        client.get(BuildConfig.URL_WEATHER, params, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val responseObject = JSONObject(result)
                val weatherD = DataWeather.fromJson(responseObject)
                showData(weatherD)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {}
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showData(weatherD: DataWeather) {

        binding.apply {
            temperature.text = weatherD.getTemperature()
            location.text = weatherD.getCity()
            weather.text = ", " + weatherD.getWeather()
            humidity.text = weatherD.getHumidity()
        }

        binding.location.visibility = View.VISIBLE
        binding.weather.visibility = View.VISIBLE

        Glide.with(this)
            .load(BuildConfig.URL_ICON + weatherD.getIcon())
            .into(binding.imgWeather)

        city = weatherD.getCity().toString()
        getAirQuality(weatherD.getLon(), weatherD.getLat())
    }

    //http://api.openweathermap.org/data/2.5/air_pollution?lat=-6.8&lon=106.8186&appid=6cf30dc39d0d8020e4350dc2d8098ca4
    private fun getAirQuality(lon: String?, lat: String?) {
        val params = RequestParams()
        params.put("lat", lat)
        params.put("lon", lon)
        params.put("appid", BuildConfig.API_KEY)
        val client = AsyncHttpClient()
        client.get(BuildConfig.URL_POLLUTION, params, object : AsyncHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val responseObject = JSONObject(result)
                aqi = responseObject.getJSONArray("list").getJSONObject(0).getJSONObject("main").getInt("aqi").toString()
                //val aqiResult = aqi.getJSONArray("main").getInt(0).toString()
                binding.airQuality.text = updateAqi(aqi)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {}

        })
    }

    private fun updateAqi(aqi: String): String {
        if(aqi == "1") {
            return "Good"
        }
        else if (aqi == "2"){
            return "Fair"
        }
        else if (aqi == "3"){
            return "Moderate"
        }
        else if (aqi == "4"){
            return "Poor"
        }
        else if (aqi == "5"){
            return "Very Poor"
        }
        return "null"
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }



    /*override fun onResume() {
        super.onResume()
        if (cityName != null){
            onLocationChanged()
        } else {
            getLocation()
        }
    }*/

    /*companion object {
        @JvmStatic
        fun newInstance() =
                HomeFragment().apply {
                    arguments = Bundle().apply {}
                }
    }*/
}