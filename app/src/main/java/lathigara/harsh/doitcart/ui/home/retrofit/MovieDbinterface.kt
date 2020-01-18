package lathigara.harsh.doitcart.ui.home.retrofit

import io.reactivex.Single
import lathigara.harsh.doitcart.ui.home.retrofit.data.classes.Avengers
import lathigara.harsh.doitcart.ui.home.retrofit.data.getalldata.GetAllMovies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://api.themoviedb.org/3/movie/popular?api_key=310971620fa1b226f24d66cb7ab0a02c&page=1
//https://api.themoviedb.org/3/movie/299534?api_key=310971620fa1b226f24d66cb7ab0a02c
//https://api.themoviedb.org/3/


const val FIRST_PAGE = 1
const val POST_PAGE = 20
interface MovieDbinterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page:Int):Single<GetAllMovies>

    // we are passing movie id here
    @GET("movie/{movie_id}")                // it will make our class emitable means it will emit data
    fun getMoveiDetails(@Path("movie_id")id:Int):Single<Avengers>







}