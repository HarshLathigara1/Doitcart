package lathigara.harsh.doitcart.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import lathigara.harsh.doitcart.ListActivity
import lathigara.harsh.doitcart.MainActivity.Companion.ERROE_DIALOG_REQUEST
import lathigara.harsh.doitcart.user_location.MapActivity
import lathigara.harsh.doitcart.user_location.MapsActivityy
import java.util.*


class MainViewModel : ViewModel() {
    companion object {
        val REQUEST_CODE = 1
    }

    fun signup_user(
        email: String,
        password: String,
        context: Context,
        location: String

    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                val userData: MutableMap<String, String> =
                    HashMap()
                userData["Email"] = email

                userData["location"] = location



                val databaseReference = FirebaseDatabase.getInstance().reference.child("USERSSS")
                databaseReference.setValue(userData).addOnCompleteListener {
                    if (!it.isSuccessful)return@addOnCompleteListener

                    val user_id = FirebaseAuth.getInstance().currentUser!!.uid
                    val intent = Intent(context.applicationContext, ListActivity::class.java)
                    intent.putExtra("USER_ID", user_id)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.applicationContext.startActivity(intent)

                }



            }


    }

    fun locateUsersLocation() {

    }

    fun isServiceOk(context: Activity): Boolean {
        // Log.d(MainActivity.TAG, "isServiceOk: google Play Service version ")
        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        if (available == ConnectionResult.SUCCESS) {
            // Log.d(MainActivity.TAG, "isServiceOk: googlePlay Services Is Working")
            return true
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //Log.d(MainActivity.TAG, "isServiceOk: we can fix it")
            val dialog = GoogleApiAvailability.getInstance()
                .getErrorDialog(context, available, ERROE_DIALOG_REQUEST)
            dialog.show()
        } else {
            Toast.makeText(context, "we will try again", Toast.LENGTH_LONG).show()
        }
        return false
    }

    fun inItMap(context: Activity) {
        val intent = Intent(context, MapsActivityy::class.java)
        context.startActivity(intent)
        context.finish()
    }


}