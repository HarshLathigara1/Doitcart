package lathigara.harsh.doitcart.ui.home.retrofit


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lathigara.harsh.doitcart.ui.home.retrofit.data.NetWorkState
import lathigara.harsh.doitcart.ui.home.retrofit.data.getalldata.Result

// get all component
// result = movie
// movieresponse = getAllmovies


class MovieDataSource(
    private val apiService: MovieDbinterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Result>() {
    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetWorkState> = MutableLiveData()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {

        networkState.postValue(NetWorkState.LOADING)
        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.results, null, page + 1)
                        networkState.postValue(NetWorkState.LOADED)
                    },
                    {
                        networkState.postValue(NetWorkState.ERROR)
                        Log.d("movie data source", it.message)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {

        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPages >= params.key ){
                        callback.onResult(it.results,params.key+1)
                        networkState.postValue(NetWorkState.LOADED)

                    }else {
                        networkState.postValue(NetWorkState.END_OF_LIST)
                    }

                }, {
                    networkState.postValue(NetWorkState.ERROR)

                })
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {

    }

}