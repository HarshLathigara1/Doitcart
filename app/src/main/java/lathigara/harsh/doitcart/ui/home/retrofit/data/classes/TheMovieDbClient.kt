package lathigara.harsh.doitcart.ui.home.retrofit.data.classes

import lathigara.harsh.doitcart.ui.home.retrofit.MovieDbinterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
  // api_key 310971620fa1b226f24d66cb7ab0a02c
 const val API_KEY = "310971620fa1b226f24d66cb7ab0a02c"
const val BASE_URL  = "https://api.themoviedb.org/3/"
// poster url  https://image.tmdb.org/t/p/w342//or06FN3Dka5tukK1e9sl16pB3iy.jpg
object TheMovieDbClient {
    // we will add interceptor put our api key
    fun getClient():MovieDbinterface{

        val requestInterceptor = Interceptor{
            val url = it.request()

                .url()
                .newBuilder()
                .addQueryParameter("api_key",API_KEY)
                .build()

            val request = it.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor it.proceed(request)


        }


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60,TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDbinterface::class.java)


    }



}