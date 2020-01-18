package lathigara.harsh.doitcart.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_click.*
import lathigara.harsh.doitcart.ListActivity
import lathigara.harsh.doitcart.R


class ClickActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click)

        btnSend.setOnClickListener {

            val bundle = Bundle()
            bundle.putInt("id", 299534)

            val fragobj = HomeFragment()
            fragobj.setArguments(bundle)

            val intent = Intent(this, ListActivity::class.java)
          //  intent.putExtra("id", 299534)
            startActivity(intent)


        }
    }
}
