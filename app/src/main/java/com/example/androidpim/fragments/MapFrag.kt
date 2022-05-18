package com.example.androidpim.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.androidpim.R
import com.example.androidpim.databinding.FragmentMapBinding
import com.example.androidpim.models.EventInt
import com.example.androidpim.models.Parking
import com.example.androidpim.service.LostPostApi
import com.example.androidpim.service.ParkingApi
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFrag : Fragment(), OnMapClickListener {
    // TODO: Rename and change types of parameters
    var mapView: MapView? = null
    private lateinit var viewAnnotationManager1: ViewAnnotationManager
    lateinit var mSharedPref: SharedPreferences
lateinit var id:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mSharedPref =  requireActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        this.id = mSharedPref.getString("email", "zwayten").toString()

        val position = CameraPosition.Builder() // Sets the new camera position
            .zoom(17.0) // Sets the zoom
            .bearing(180.0) // Rotate the camera
            .tilt(30.0) // Set the camera tilt
            .build() // Creates a CameraPosition from the builder


        val binding = FragmentMapBinding.inflate(layoutInflater)

        var view= inflater.inflate(R.layout.fragment_map, container, false)

        viewAnnotationManager1 = binding.mapView.viewAnnotationManager
        mapView = view.findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.apply {

            loadStyleUri(Style.MAPBOX_STREETS){

                addOnMapClickListener(
                    this@MapFrag
                )
            }


        }
        val apiuser = ParkingApi.create()

        apiuser.getAllParking().enqueue(object : Callback<List<Parking>>{
            override fun onResponse(call: Call<List<Parking>>, response: Response<List<Parking>>) {
                if (response.isSuccessful){
                    //println(response.body()!![position].userEmail.toString())
for(parking in response.body()!!){

    addAnnotationToMap(parking.latatitude,parking.longatitude)

}

                }
            }
            override fun onFailure(call: Call<List<Parking>>, t: Throwable) {
                println("failed")
            }

        })






        // Inflate the layout for this fragment
        return view
    }
    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    fun basicAlert(point: Point ){
        val builder = AlertDialog.Builder(requireContext())

        with(builder)
        {

            val positiveButtonClick = { dialog: DialogInterface, which: Int ->

                val apiuser = ParkingApi.create()
                val data: LinkedHashMap<String, Any> = LinkedHashMap()
                data["longatitude"] = point.longitude()
                data["latatitude"]= point.latitude()
                data["userId"]= id

                apiuser.postParking(data).enqueue(object: Callback<Any> {
                    override fun onResponse(
                        call: Call<Any>,
                        response: Response<Any>
                    ) {
                        if(response.isSuccessful){
                            Log.i("onResponse goooood", response.body().toString())
                            addViewAnnotation(point)


                        } else {
                            Log.i("OnResponse not good", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {

                        println("noooooooooooooooooo")
                    }

                })








            }
            val negativeButtonClick = { dialog: DialogInterface, which: Int ->
                Toast.makeText(context,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            setTitle( "wanna add a new parking sport here" )
            setMessage("lat="+point.latitude()+"\nlon="+point.longitude())
            setPositiveButton("Oui", DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton(android.R.string.no, negativeButtonClick)
            //setNeutralButton("Maybe", neutralButtonClick)
            show()
        }



    }

    @SuppressLint("SetTextI18n")
    private fun addViewAnnotation(point:Point) {
        val viewAnnotation = viewAnnotationManager1.addViewAnnotation(
            resId = R.layout.viewannotations,
            options = viewAnnotationOptions {
                geometry(point)
                allowOverlap(true)
            }

        )
        addAnnotationToMap(point.latitude(),point.longitude())
    }


    override fun onMapClick(point: com.mapbox.geojson.Point): Boolean {
        basicAlert(point)

        return true
    }
    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
// copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }


    private fun addAnnotationToMap(lat: Double,long : Double) {
// Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            requireContext(),
            R.drawable.ic_baseline_local_parking_24
        )?.let {

            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)
// Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
// Define a geographic coordinate.

                .withPoint(com.mapbox.geojson.Point.fromLngLat(long,lat ))
// Specify the bitmap you assigned to the point annotation
// The bitmap will be added to map style automatically.
                .withIconImage(it).withIconSize(0.2)
            //.withTextField(args.name)
            //.withTextColor("@Color/dgreen_2")
            //.withDraggable(true)

// Add the resulting pointAnnotation to the map.
            pointAnnotationManager?.create(pointAnnotationOptions)
            //basicAlert(lat,long)

        }
    }


}