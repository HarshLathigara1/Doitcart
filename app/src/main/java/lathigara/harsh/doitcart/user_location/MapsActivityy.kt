package lathigara.harsh.doitcart.user_location

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import lathigara.harsh.doitcart.R

class MapsActivityy : AppCompatActivity(), OnMapReadyCallback {
    companion object{
         lateinit var mMap: GoogleMap
         val ZOOM = 15f
    }


    lateinit var mapActivityViewModel: MapActivityViewModel
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_activityy)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
       // var edtSearch = findViewById<EditText>(R.id.edtSearch)
        mapActivityViewModel =
            ViewModelProviders.of(this).get(MapActivityViewModel::class.java)
        mapActivityViewModel.getLocationPermission(this)
        val txtLocation = findViewById<TextView>(R.id.txtAdress)
        val btnLocation = findViewById<Button>(R.id.btngetLocation)
       mapActivityViewModel.getDeviceLocation(this,txtLocation,btnLocation)

/*
        edtSearch.setOnEditorActionListener(OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN || keyEvent.action == KeyEvent.KEYCODE_ENTER
            ) { //execute our method for searching
                mapActivityViewModel.geoLocate(edtSearch,this)
            }
            false
        })*/





    }





    override fun onMapReady(googleMap: GoogleMap) {


        mMap = googleMap
       mMap.isMyLocationEnabled = true
    }
}
