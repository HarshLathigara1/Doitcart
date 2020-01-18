package lathigara.harsh.doitcart.ui.home.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import lathigara.harsh.doitcart.ui.home.retrofit.data.getalldata.Result                                                                        // key //pojo

class MovieDataSourceFactory(private val apiService:MovieDbinterface,private val compositeDisposable: CompositeDisposable):DataSource.Factory<Int,Result>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Result> {

       val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        movieLiveDataSource.postValue(movieDataSource)


        return movieDataSource


    }

}