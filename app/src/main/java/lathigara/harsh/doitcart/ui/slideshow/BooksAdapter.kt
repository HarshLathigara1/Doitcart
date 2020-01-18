package lathigara.harsh.doitcart.ui.slideshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.book_item.view.*
import lathigara.harsh.doitcart.R
import lathigara.harsh.doitcart.ui.gallery.Books

/*
class BooksAdapter(val context: Context,val book:Books):Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.book_item

    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.txtBookName.text = book.name
        viewHolder.itemView.txtBookPrice.text = book.price
        viewHolder.itemView.txtBookratings.text = book.ratings
      Glide.with(context).load(book.image).into(viewHolder.itemView.imgBookCover)


    }
}*/


class BooksAdapter(bookList: ArrayList<Books>) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

     var bookList: ArrayList<Books>

    init {
        this.bookList = bookList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name:String = bookList[position].name
        val price:String =  bookList[position].price
        val ratings:String =  bookList[position].ratings

        holder.setBooks(name,price,ratings)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       // val imageView = itemView.imgBookCover
        var bookName = itemView.txtBookName.text
        var ratingss = itemView.txtBookratings.text
        var prices = itemView.txtBookName.text


        fun setBooks(name:String,ratings:String,price:String){
            bookName = name
            ratingss  =ratings
            prices = price


        }
    }


}