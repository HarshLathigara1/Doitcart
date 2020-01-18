package lathigara.harsh.doitcart.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.movie_item_layout.view.*
import kotlinx.android.synthetic.main.progress_layout.view.*
import lathigara.harsh.doitcart.PaymentActivity
import lathigara.harsh.doitcart.R
import lathigara.harsh.doitcart.ui.home.retrofit.data.NetWorkState
import lathigara.harsh.doitcart.ui.home.retrofit.data.getalldata.Result
import java.util.*


class GetAllMoviesPagedListAdapter(val context: Context) :
    PagedListAdapter<Result, RecyclerView.ViewHolder>(MovieDiffCallback()) ,PaymentResultListener{

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetWorkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_item_layout, parent, false)
            return MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.progress_layout, parent, false)
            return NetWorkstateItemViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetWorkstateItemViewHolder).bind(networkState!!)
        }

    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem

        }

    }

    class


    private

    fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetWorkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }


    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }


    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(result: Result?, context: Context) {
            itemView.getAllTitle.text = "Movie Name:${result?.title}"
            itemView.getAllrealeseDate.text = "Release Date ${result?.releaseDate}"

            val moviePoster = POSTER_BASE_URL + result?.posterPath
            val voteaverage = result?.voteAverage
            val originallanguage = result?.originalLanguage
            val overView = result?.overview



            itemView.txtPrice.text = "100.12"
            val price = itemView.txtPrice.text.toString()




                Glide.with(itemView.context).load(moviePoster).into(itemView.getAllImage)

            itemView.movei_card.setOnClickListener {
                val intent = Intent(context.applicationContext, PaymentActivity::class.java)
                intent.putExtra("result_title", result!!.title)
                intent.putExtra("result_Image",moviePoster)
                intent.putExtra("txt_price",price)
                intent.putExtra("txt_voteaverage",voteaverage)
                intent.putExtra("txt_originallanguage",originallanguage)
                intent.putExtra("txt_overView",overView)



                itemView.context.startActivity(intent)


                Toast.makeText(itemView.context, "data added To Cart", Toast.LENGTH_LONG).show()







            }



        }


    }

    class NetWorkstateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(netWorkState: NetWorkState) {
            if (netWorkState != null && netWorkState == NetWorkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE

            } else {
                itemView.progress_bar_item.visibility = View.GONE

            }

            if (netWorkState != null && netWorkState == NetWorkState.ERROR) {
                itemView.progress_bar_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = netWorkState.msg

            } else if (netWorkState != null && netWorkState == NetWorkState.END_OF_LIST) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = netWorkState.msg

            } else {
                itemView.error_msg_item.visibility = View.GONE
            }
        }
    }


    fun setNetworkState(newNetWorkState: NetWorkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetWorkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != networkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPaymentSuccess(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}