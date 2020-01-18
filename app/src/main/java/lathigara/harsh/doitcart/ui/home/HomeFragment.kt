package lathigara.harsh.doitcart.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.progress_layout.*
import lathigara.harsh.doitcart.R
import lathigara.harsh.doitcart.ui.home.retrofit.MovieDbinterface
import lathigara.harsh.doitcart.ui.home.retrofit.data.MoviePagedListRepository
import lathigara.harsh.doitcart.ui.home.retrofit.data.NetWorkState
import lathigara.harsh.doitcart.ui.home.retrofit.data.classes.Avengers
import lathigara.harsh.doitcart.ui.home.retrofit.data.classes.MovieDbRepository
import lathigara.harsh.doitcart.ui.home.retrofit.data.classes.TheMovieDbClient
import java.text.NumberFormat
import java.util.*

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342/"

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    // private lateinit var movieDbRepository: MovieDbRepository
    // first take variables for required classes to get in here
    private lateinit var moveRepository: MoviePagedListRepository



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val apiService: MovieDbinterface = TheMovieDbClient.getClient()
        moveRepository = MoviePagedListRepository(apiService)

        homeViewModel = getViewModel()
        val adapter = GetAllMoviesPagedListAdapter(context!!.applicationContext)
        val gridLayoutManager = GridLayoutManager(context!!.applicationContext,3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                val viewType = adapter.getItemViewType(position)
                if (viewType == adapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }

        }

        root.recycler_MovieList.layoutManager = gridLayoutManager
        root.recycler_MovieList.setHasFixedSize(true)
        root.recycler_MovieList.adapter = adapter



        homeViewModel.moviePagedList.observe(this, Observer {
            adapter.submitList(it)

        })

        homeViewModel.netWorkState.observe(this, Observer {
           progressMain.visibility  =if (homeViewModel.listIsemptyORNot() && it == NetWorkState.LOADING) View.VISIBLE else   View.GONE

            txtMain.visibility =  if (homeViewModel.listIsemptyORNot() && it == NetWorkState.ERROR) View.VISIBLE else   View.GONE

            if (!homeViewModel.listIsemptyORNot()){
                adapter.setNetworkState(it)
            }
        })



        return root
    }




   /* fun bindUI(it:Avengers){

        txtMovie.text = it.title
        txtReleaseDate.text = it.releaseDate
        txtRatings.text = it.rating.toString()
        txtReleaseData.text = it.runtime.toString()
        txtOveerView.text = it.overview
        val formateCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        txtBudget.text = formateCurrency.format(it.budget)
        txtRevenue.text = formateCurrency.format(it.revenue)
        val posterUrl = POSTER_BASE_URL + it.posterPath
        Glide.with(this).load(posterUrl).into(imageView3)


        }*/
    // setting up for viewmodel to get in
    private fun getViewModel(): HomeViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(moveRepository) as T
            }

        })[HomeViewModel::class.java]
    }
}