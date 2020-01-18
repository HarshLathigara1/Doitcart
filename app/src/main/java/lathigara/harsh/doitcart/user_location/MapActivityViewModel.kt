package lathigara.harsh.doitcart.user_location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import lathigara.harsh.doitcart.MainActivity
import lathigara.harsh.doitcart.user_location.MapActivity.Companion.COARSE_LOCATION
import lathigara.harsh.doitcart.user_location.MapActivity.Companion.FINE_LOCATION
import lathigara.harsh.doitcart.user_location.MapActivity.Companion.LOCATION_PERMISSION_REQUEST_CODE
import lathigara.harsh.doitcart.user_location.MapsActivityy.Companion.ZOOM
import lathigara.harsh.doitcart.user_location.MapsActivityy.Companion.mMap
import java.io.IOException
import java.util.*

class MapActivityViewModel() : ViewModel() {


    private var mLocationPermissionsGranted = false
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var locationn: Location? = null
    var address:Any? = null


    fun getLocationPermission(context: Activity) {

        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        // here we are checking if we have all permissions then we are initializing our map
        if (ContextCompat.checkSelfPermission(
                context,  // permission granted
                FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    context, COARSE_LOCATION

                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mLocationPermissionsGranted =
                    true //we are making this true means everything is okay
                // and here we are getting map
            } else { // if its not than we are asking for  permissions
                ActivityCompat.requestPermissions(
                    context,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } else { // it means we are unable to add location permissions
            ActivityCompat.requestPermissions(
                context,
                permissions,
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getDeviceLocation(context: Activity,textLocation:TextView,btnGetLocation:Button) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        try {

            val task: Task<Location?> =
                fusedLocationProviderClient.lastLocation
            task.addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                locationn =
                    task.result


                mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            locationn!!.latitude,
                            locationn!!.longitude
                        ), ZOOM
                    )
                )
                val options = MarkerOptions()
                options.position(LatLng(locationn!!.latitude,locationn!!.longitude))
                val geocoder = Geocoder(context)
                var list: List<Address> =
                    ArrayList()
                list = geocoder.getFromLocation(locationn!!.latitude,locationn!!.longitude, 1)
                if (list.size > 0){
                 val address = list[0]
                    val options = MarkerOptions()
                    options.position(LatLng(address.latitude,address.longitude))
                    options.title(address.getAddressLine(0))
                    mMap.addMarker(options)
                     textLocation.text = address.getAddressLine(0)
                    val addnew = textLocation.text.toString()
                    btnGetLocation.setOnClickListener{
                        if (!TextUtils.isEmpty(addnew)){
                            Toast.makeText(context,"Address Added",Toast.LENGTH_LONG).show()
                            val intent = Intent(context.baseContext,MainActivity::class.java)
                            intent.putExtra("location",addnew)
                            context.startActivity(intent)
                            context.finish()
                        }else {
                            Toast.makeText(context,"Address not added",Toast.LENGTH_LONG).show()
                        }
                    }



                }



                Toast.makeText(
                    context,
                    "loc${locationn!!.latitude}",
                    Toast.LENGTH_LONG
                ).show()


            }

        } catch (e: SecurityException) {
            e.printStackTrace()
        }


    }

     fun geoLocate(serchText:EditText,context: Activity) {

        val searchString: String = serchText.getText().toString()
        val geocoder = Geocoder(context)
        var list: List<Address> =
            ArrayList()
        try {
            list = geocoder.getFromLocationName(searchString, 1)
        } catch (e: IOException) {
             // Log.e(context, "geoLocate: IOException: " + e.message)
        }
        if (list.size > 0) {
            val address = list[0]
            /*Log.d(
               *//* MapActivity.TAG,
                "geoLocate: found a location: $address"*//*
            )*/
            Toast.makeText(context, address.toString(), Toast.LENGTH_SHORT).show();

           /* mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        locationn!!.latitude,
                        locationn!!.longitude
                    ), ZOOM
                )
            )
            address.getAddressLine(0)*/
            moveCamera( LatLng(
                address.latitude,
                address.longitude
            ), ZOOM,address.getAddressLine(0))

            val options = MarkerOptions()
            options.position(LatLng(address.latitude,address.longitude))
            options.title(address.getAddressLine(0))
            mMap.addMarker(options)
        }
    }

     fun moveCamera(
        latLng: LatLng,
        zoom: Float,
        title: String
    ) {
        /*Log.d(
           *//* MapActivity.TAG,
            "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude*//*
        )*/
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        if (title != "My Location") {
            val options = MarkerOptions()
                .position(latLng)
                .title(title)
            mMap.addMarker(options)
        }
      //  hideSoftKeyboard()
    }




/*
    fun getDeviceLocation(context: Activity):Location {
        var t1 = Thread()
        var newLocation: Location?  = null



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


        val task: Task<Location?> =
            fusedLocationProviderClient.lastLocation
        task.addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener

            newLocation = it.result!!


            Toast.makeText(
                context,
                "loc${newLocation!!.latitude}",
                Toast.LENGTH_LONG
            ).show()


        }


        Thread.sleep(5000)
        t1.start()

        return newLocation!!


    }*/


}