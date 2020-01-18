package lathigara.harsh.doitcart.ui.home.retrofit.data

import android.app.usage.NetworkStats
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lathigara.harsh.doitcart.ui.home.retrofit.MovieDbinterface
import lathigara.harsh.doitcart.ui.home.retrofit.data.classes.Avengers
import java.lang.Exception

class MovieDetailsNetworkDataSource(
    private val apiService: MovieDbinterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _netWorkState = MutableLiveData<NetWorkState>()
    val networkState: LiveData<NetWorkState>
        get() = _netWorkState

    private val _downloadMovieDetailsResponse = MutableLiveData<Avengers>()
    val downloadMovieDetailsResponse: LiveData<Avengers>
        get() = _downloadMovieDetailsResponse


    fun fetchMoveiDetailsByRxJava(movieId: Int) {
      _netWorkState.postValue(NetWorkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMoveiDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadMovieDetailsResponse.postValue(it)
                           _netWorkState.postValue(NetWorkState.LOADED)
                        }, {
                           _netWorkState.postValue(NetWorkState.ERROR)

                        }
                    )
            )
        } catch (e: Exception) {
            _netWorkState.postValue(NetWorkState.ERROR)
        }

    }


}