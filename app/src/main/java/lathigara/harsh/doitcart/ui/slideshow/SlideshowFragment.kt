package lathigara.harsh.doitcart.ui.slideshow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_slideshow.*
import kotlinx.android.synthetic.main.fragment_slideshow.view.*
import lathigara.harsh.doitcart.R
import lathigara.harsh.doitcart.ui.gallery.Books

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private  var booksList = ArrayList<Books>()
    lateinit var adapter:BooksAdapter

    // var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        var progressBar = root.findViewById<ProgressBar>(R.id.progressrec)
        adapter = BooksAdapter(booksList)
        root.book_recycle.adapter = adapter

        FirebaseFirestore.getInstance().collection("Bookss").orderBy("index").get().addOnCompleteListener(object :OnCompleteListener<QuerySnapshot>{
            override fun onComplete(p0: Task<QuerySnapshot>) {
               if (p0.isSuccessful){
                for (document in p0.result!!){
                    booksList.add(Books(document.get("name").toString(),document.get("price").toString(),document.get("ratings").toString()))
                    adapter.notifyDataSetChanged()
                }
               }
            }

        })




      /* if (adapter == null){
           progressBar.visibility =View.VISIBLE
       }else {
           progressBar.visibility =View.GONE
           book_recycle.layoutManager = LinearLayoutManager(context)
           book_recycle.setHasFixedSize(true)
           book_recycle.adapter = adapter
           adapter!!.notifyDataSetChanged()
       }*/




        return root
    }



}