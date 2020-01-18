package lathigara.harsh.doitcart.ui.home.retrofit.data.classes

import androidx.lifecycle.LiveData
import io.reactivex.disposables.CompositeDisposable
import lathigara.harsh.doitcart.ui.home.retrofit.MovieDbinterface
import lathigara.harsh.doitcart.ui.home.retrofit.data.MovieDetailsNetworkDataSource
import lathigara.harsh.doitcart.ui.home.retrofit.data.NetWorkState

class MovieDbRepository(private val apiService:MovieDbinterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchingMovieDetails(compositeDisposable: CompositeDisposable,movieId:Int):LiveData<Avengers>{


        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMoveiDetailsByRxJava(movieId)
        return movieDetailsNetworkDataSource.downloadMovieDetailsResponse

    }

    fun getMoviedetailNetworkState():LiveData<NetWorkState>{

        return movieDetailsNetworkDataSource.networkState
    }
}