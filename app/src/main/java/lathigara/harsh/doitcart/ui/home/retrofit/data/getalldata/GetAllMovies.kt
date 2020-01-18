package lathigara.harsh.doitcart.ui.home.retrofit.data.getalldata


import com.google.gson.annotations.SerializedName
// creating pojo class
// then creating interface TheMovieDBintnterFace
// movieDataSource
//movieDartaSourceFactory
//MoviePagedListRepository



data class GetAllMovies(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)