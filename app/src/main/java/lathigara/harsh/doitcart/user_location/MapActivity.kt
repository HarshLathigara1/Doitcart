package lathigara.harsh.doitcart.user_location

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import lathigara.harsh.doitcart.R

class MapActivity : AppCompatActivity(){
    companion object {
         val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
         val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
         val LOCATION_PERMISSION_REQUEST_CODE = 1234
        private val ZOOM = 15f

    }



    // var
    private val fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val mLocationPermissionsGranted = false
    var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)



      //  mapActivityViewModel.getLocationPermission(this)
    }


}
