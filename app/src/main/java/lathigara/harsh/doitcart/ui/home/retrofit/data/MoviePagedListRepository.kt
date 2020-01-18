package lathigara.harsh.doitcart.ui.home.retrofit.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import lathigara.harsh.doitcart.ui.home.retrofit.MovieDataSource
import lathigara.harsh.doitcart.ui.home.retrofit.MovieDataSourceFactory
import lathigara.harsh.doitcart.ui.home.retrofit.MovieDbinterface
import lathigara.harsh.doitcart.ui.home.retrofit.POST_PAGE
import lathigara.harsh.doitcart.ui.home.retrofit.data.getalldata.Result

class MoviePagedListRepository(private val apiService: MovieDbinterface) {

    lateinit var moviePagedList:LiveData<PagedList<Result>>
    lateinit var  moviedDataSourceFactory: MovieDataSourceFactory
    fun fetchLiveMoviePaggedList(compositeDisposable: CompositeDisposable):LiveData<PagedList<Result>>{

        moviedDataSourceFactory = MovieDataSourceFactory(apiService,compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviedDataSourceFactory,config).build()


        return moviePagedList

    }


    fun getnetWorkState():LiveData<NetWorkState>{
        return Transformations.switchMap<MovieDataSource,NetWorkState>(
            moviedDataSourceFactory.movieLiveDataSource,MovieDataSource::networkState
        )
    }
}