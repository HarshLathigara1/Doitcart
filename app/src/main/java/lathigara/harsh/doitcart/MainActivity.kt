package lathigara.harsh.doitcart

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_layout.*
import kotlinx.android.synthetic.main.dialog_layout.view.*
import lathigara.harsh.doitcart.viewmodels.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityViewmodel: MainViewModel
    private lateinit var progrssBar: ProgressBar
    private lateinit var googleSignInClient: GoogleSignInClient



    lateinit var txtFinalAddress: TextView
    var locaiton: String? = null

    companion object {
        var firebaseAuth: FirebaseAuth? = null
        val ERROE_DIALOG_REQUEST = 9001
        var GOOGLE_SIGN = 123
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // val txtLocate = findViewById<TextView>(R.id.txtLocate)
        txtFinalAddress = findViewById(R.id.txtFinalAddress)



        if (intent.hasExtra("location")) {

            locaiton = intent.getStringExtra("location")
            txtFinalAddress.text = locaiton


        }

        mainActivityViewmodel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)

        progrssBar = findViewById(R.id.signUpProgress)
        progrssBar.visibility = View.INVISIBLE

        txtLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        imgLocateUser.setOnClickListener {
            progrssBar.visibility = View.VISIBLE
            if (mainActivityViewmodel.isServiceOk(this)) {
                mainActivityViewmodel.inItMap(this)

            }
        }

        btnGoogleSignin.setOnClickListener{

            val googleSignin = GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this,googleSignin)
            if (!TextUtils.isEmpty(txtFinalAddress.text.toString())){
                progrssBar.visibility = View.VISIBLE
                googleSignIn()
            }else {
                Toast.makeText(this, "Please add Your Location", Toast.LENGTH_LONG).show()
            }




        }



       /* txtAdmin.setOnClickListener {

            val builder = AlertDialog.Builder(
                this
            )
            val view1: View =
                LayoutInflater.from(applicationContext).inflate(R.layout.dialog_layout, null)
            builder.setCancelable(true)

            // TextView builderText = builder.f
            // builder.setTitle("Opss!!");
            // TextView builderText = builder.f

            var adminiEmail = view1.edtAdminName.text.toString()
            var adminiPass = view1.edtAdminPass.text.toString()

            var btnAdminLog = view1.findViewById<Button>(R.id.btnAdminLog)
            btnAdminLog.setOnClickListener {
                progrssBar.visibility = View.VISIBLE
                val databaseRef = FirebaseDatabase.getInstance().reference.child("Admin")
                    databaseRef.addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onDataChange(p0: DataSnapshot) {

                           if(p0.child("password").value == edtAdminPass.text && p0.child("name").value == edtAdminName.text  ){
                               Toast.makeText(applicationContext,"Done",Toast.LENGTH_LONG).show()
                               val intent = Intent(applicationContext,Admin::class.java)
                               startActivity(intent)
                           }
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                    })
                val databaseRefAdmin = FirebaseDatabase.getInstance().reference.child("Admin").child("password")







            }

            builder.setView(view1)

            builder.create().show()

        }*/





        btnSignUp.setOnClickListener {



            if (!TextUtils.isEmpty(edtEmail.text.toString()) && !TextUtils.isEmpty(edtPassword.text.toString()) && !TextUtils.isEmpty(
                    edtEmail.text.toString()
                ) && !TextUtils.isEmpty(txtFinalAddress.text.toString())
            ) {
                progrssBar.visibility = View.VISIBLE
                mainActivityViewmodel.signup_user(
                    edtEmail.text.toString(),
                    edtPassword.text.toString(),
                    applicationContext, locaiton!!
                )
            } else {
                Toast.makeText(this, "Please add Details in all fields", Toast.LENGTH_LONG).show()
            }
        }


    }

    fun googleSignIn(){
        val intent =googleSignInClient.signInIntent
        startActivityForResult(intent,GOOGLE_SIGN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN){
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.result
                if (account != null){
                    val credential  = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                       if (it.isSuccessful){
                           val intent = Intent(this, ListActivity::class.java)
                           startActivity(intent)
                       }


                    }

                }
            }catch (e :Exception){

            }

        }
    }


}
