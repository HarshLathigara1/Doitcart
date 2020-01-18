package lathigara.harsh.doitcart.ui.slideshow

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import lathigara.harsh.doitcart.ui.gallery.Books

class SlideshowViewModel : ViewModel() {

  /*  private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragme"
    }
    val text: LiveData<String> = _text*/

    fun getData(context: Context, booksList:ArrayList<Books>,adapter: GroupAdapter<GroupieViewHolder>){
        val booksList = ArrayList<Books>()

    }

}