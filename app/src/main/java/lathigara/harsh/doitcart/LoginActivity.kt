package lathigara.harsh.doitcart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(edtLoginEmail.text.toString(),edtLoginPass.text.toString()).addOnCompleteListener {
                if (!it.isSuccessful)return@addOnCompleteListener

                val intent = Intent(this,ListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
