package com.example.gekata_mobile
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.gekata_mobile.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.DrivingRouterType
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.places.panorama.PanoramaService.SearchListener
import com.yandex.mapkit.places.panorama.PanoramaService.SearchSession
import com.yandex.mapkit.search.BusinessObjectMetadata
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session


class MapKitActivity : Activity() {
    lateinit var mapView: MapView

//    private var startLocation: Point? = null//Point(48.015884, 37.802850)
//    private var endLocation: Point? = null//Point(48.008614, 37.831371)
    private var zoomValue: Float = 16.5f

    private lateinit var mapObjectCollection: MapObjectCollection
    private lateinit var placemarkMapObject: PlacemarkMapObject
    private lateinit var placemarksCollection: MapObjectCollection

    private var drivingSession: DrivingSession? = null
    private lateinit var drivingRouter: DrivingRouter
    private lateinit var drivingOptions: DrivingOptions
    private lateinit var routesCollection: MapObjectCollection

//    private lateinit var searchManager: SearchManager
//    private lateinit var searchSession: SearchSession

    private var startPointResponse: Response = Response()
        set(value) {
            field = value
            onStartPointResponseUpdated()
        }

    private var endPointResponse: Response = Response()
        set(value) {
            field = value
            onEndPointResponseUpdated()
        }

    private var routePoints = emptyList<Point>()
        set(value) {
            field = value
            onRoutePointsUpdated()
        }

    private var routes = emptyList<DrivingRoute>()
        set(value) {
            field = value
            onRoutesUpdated()
        }



    private val drivingRouteListener = object : DrivingSession.DrivingRouteListener {
        override fun onDrivingRoutes(drivingRoutes: MutableList<DrivingRoute>) {
            Log.d("routes", "success")
            routes = drivingRoutes
        }
        override fun onDrivingRoutesError(p0: com.yandex.runtime.Error) {
            when (p0) {
                is NetworkError -> Log.d("routes", "Routes request error due network issues")
                else -> Log.d("routes", "Routes request unknown error")
            }
        }

    }

    private val startPointSearchListener = object: Session.SearchListener {
        override fun onSearchResponse(p0: Response) {
                Log.d("SL", "start resp")
                startPointResponse = p0
        }
        override fun onSearchError(p0: com.yandex.runtime.Error) {
            Log.d("onSearchErr", p0.toString())
        }
    }

    private val endPointSearchListener = object: Session.SearchListener {
        override fun onSearchResponse(p0: Response) {
            Log.d("SL", "end resp")
            endPointResponse = p0
        }
        override fun onSearchError(p0: com.yandex.runtime.Error) {
            Log.d("onSearchErr", p0.toString())
        }
    }



    private lateinit var searchSession: com.yandex.mapkit.search.Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maplayout)
        mapView = findViewById(R.id.mapview)

        placemarksCollection = mapView.map.mapObjects.addCollection()
        routesCollection = mapView.map.mapObjects.addCollection()

        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter(DrivingRouterType.COMBINED)

        ////////////////////////////////////

        val startAddress = intent.getStringExtra("start")
        val endAddress = intent.getStringExtra("end")

        findPointFromAddress(startAddress!!, startPointSearchListener)
        findPointFromAddress(endAddress!!, endPointSearchListener)

        ///////////////////////////////////
    }


    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun mainMapkitProc(){
        Log.d("", "main start")
        if (startLocation!= null && endLocation != null) {
            moveCameraTo(startLocation!!)
            setMarker(startLocation!!, "start point")
            setMarker(endLocation!!, "end point")
            buildWay(startLocation!!, endLocation!!)
        }
        Log.d("", "main end")
    }


    private fun onRoutePointsUpdated() {
        placemarksCollection.clear()

        if (routePoints.isEmpty()) {
            drivingSession?.cancel()
            routes = emptyList()
            return
        }

        val imageProvider = ImageProvider.fromResource(this, R.drawable.ic_pin_black_png)
        routePoints.forEach {
            placemarksCollection.addPlacemark(
                it,
                imageProvider,
                IconStyle().apply {
                    scale = 0.5f
                    zIndex = 20f
                }
            )
        }

        val requestPoints = buildList {
            add(RequestPoint(routePoints.first(), RequestPointType.WAYPOINT, null, null))
            addAll(
                routePoints.subList(1, routePoints.size - 1)
                    .map { RequestPoint(it, RequestPointType.VIAPOINT, null, null) })
            add(RequestPoint(routePoints.last(), RequestPointType.WAYPOINT, null, null))
        }

        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()

        drivingSession = drivingRouter.requestRoutes(
            requestPoints,
            drivingOptions,
            vehicleOptions,
            drivingRouteListener,
        )
    }
    private fun onRoutesUpdated() {
        routesCollection.clear()
        if (routes.isEmpty()) return

        routes.forEachIndexed { index, route ->
            routesCollection.addPolyline(route.geometry).apply {
                when (index) {
                    0 -> styleMainRoute()
                    1 -> styleSecondRoute()
                    else -> styleThirdRoute()
                }
            }
        }
    }

    private fun onStartPointResponseUpdated(){
        val results = startPointResponse.collection.children.firstOrNull()?.obj?.geometry
        startLocation = results?.first()?.point!!
        Log.d("", "startResponse")
        mainMapkitProc()
    }

    private fun onEndPointResponseUpdated(){
        val results = endPointResponse.collection.children.firstOrNull()?.obj?.geometry
        endLocation = results?.first()?.point!!
        Log.d("", "endResponse")
        mainMapkitProc()
    }

    private fun findPointFromAddress(address: String, listener: Session.SearchListener) {
        Log.d("SSL", "$listener")
        val searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE)
        val point = Geometry.fromPoint(Point(55.755793, 37.617134))
        searchSession = searchManager.submit(address, point, SearchOptions(), listener)
    }

    private fun buildWay(start: Point, end: Point) {
        val points = buildList {
            add(RequestPoint(start, RequestPointType.WAYPOINT, null, null))
            add(RequestPoint(end, RequestPointType.WAYPOINT, null, null))
        }

        drivingRouter =
            DirectionsFactory.getInstance().createDrivingRouter(DrivingRouterType.COMBINED)
        drivingOptions = DrivingOptions().apply { routesCount = 3 }
        routesCollection = mapView.map.mapObjects.addCollection()

        drivingSession = drivingRouter.requestRoutes(
            points,
            drivingOptions,
            VehicleOptions(),
            drivingRouteListener
        )
    }

    private fun setMarker(point: Point, text: String) {
        mapObjectCollection = mapView.map.mapObjects
        placemarkMapObject = mapObjectCollection.addPlacemark(
            point,
            ImageProvider.fromResource(this, R.drawable.ic_pin_black_png)
        )
        placemarkMapObject.setText(text)
    }

    private fun moveCameraTo(point: Point) {
        mapView.map.move(CameraPosition(point, zoomValue, 0.0f, 0.0f))
    }

    private fun PolylineMapObject.styleMainRoute() {
        zIndex = 10f
        setStrokeColor(ContextCompat.getColor(this@MapKitActivity, R.color.light_blue))
        strokeWidth = 1f
        outlineColor = ContextCompat.getColor(this@MapKitActivity, R.color.light_blue)
        outlineWidth = 2f
    }

    private fun PolylineMapObject.styleSecondRoute() {
        zIndex = 9f
        setStrokeColor(ContextCompat.getColor(this@MapKitActivity, R.color.light_green))
        strokeWidth = 3f
        outlineColor = ContextCompat.getColor(this@MapKitActivity, R.color.light_green)
        outlineWidth = 2f
    }

    private fun PolylineMapObject.styleThirdRoute() {
        zIndex = 8f
        setStrokeColor(ContextCompat.getColor(this@MapKitActivity, R.color.light_magenta))
        strokeWidth = 5f
        outlineColor = ContextCompat.getColor(this@MapKitActivity, R.color.light_magenta)
        outlineWidth = 2f
    }

    companion object {
        private var startLocation: Point? = null//Point(48.015884, 37.802850)
        private var endLocation: Point? = null//Point(48.008614, 37.831371)

    }

}


