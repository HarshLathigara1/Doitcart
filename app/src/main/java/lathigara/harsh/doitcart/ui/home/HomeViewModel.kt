package lathigara.harsh.doitcart.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import lathigara.harsh.doitcart.ui.home.retrofit.data.MoviePagedListRepository
import lathigara.harsh.doitcart.ui.home.retrofit.data.NetWorkState
import lathigara.harsh.doitcart.ui.home.retrofit.data.classes.Avengers
import lathigara.harsh.doitcart.ui.home.retrofit.data.classes.MovieDbRepository
import lathigara.harsh.doitcart.ui.home.retrofit.data.getalldata.GetAllMovies
import lathigara.harsh.doitcart.ui.home.retrofit.data.getalldata.Result

class HomeViewModel(private val moviePagedDbRepository: MoviePagedListRepository) : ViewModel() {


    private val compositeDisposable  = CompositeDisposable()

   /* val movieDetails:LiveData<Avengers> by lazy {
        movieDbRepository.fetchingMovieDetails(compositeDisposable,movieId)
    }*/
    //
   val moviePagedList:LiveData<PagedList<Result>> by lazy {
       moviePagedDbRepository.fetchLiveMoviePaggedList(compositeDisposable)
   }



    val netWorkState:LiveData<NetWorkState> by lazy {
        moviePagedDbRepository.getnetWorkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun listIsemptyORNot():Boolean{
        return moviePagedList.value?.isEmpty()?:true
    }


}

