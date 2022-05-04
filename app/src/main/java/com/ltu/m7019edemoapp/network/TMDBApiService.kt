package com.ltu.m7019edemoapp.network

import com.ltu.m7019edemoapp.network.MovieResponse
import com.ltu.m7019edemoapp.network.*
import com.ltu.m7019edemoapp.utils.Constants

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.net.URI.create
import java.util.concurrent.TimeUnit

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/**
 * Add a httpclient logger for debugging purpose
 * object.
 */
fun getLoggerIntercepter(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */

private val movieListRetrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(getLoggerIntercepter())
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.MOVIE_LIST_BASE_URL)
    .build()

interface MovieResponseDAO {
    //@GET("devbytes")
    //suspend fun getPlaylist(): NetworkVideoContainer

//    @GET("popular")
//    suspend fun getAllMovieRepositories(
//        @Query("api_key")
//        apiKey: String = Constants.API_KEY
//    ): MovieResponse

    @GET
    suspend fun getAllMovieRepositories(
        @Url
        movieId: String,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieDetailResponse
}
object RemoteDatabase {

    //Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
//        .client(
//            OkHttpClient.Builder()
//                .addInterceptor(getLoggerIntercepter())
//                .connectTimeout(3, TimeUnit.SECONDS)
//                .readTimeout(3, TimeUnit.SECONDS)
//                .build()
//        )
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.MOVIE_LIST_BASE_URL)
        .build()

    val provideMovieResponse = retrofit.create(MovieResponseDAO::class.java)
}


interface TMDBApiService {
    @GET
    suspend fun getMovieReviews(
        @Url
        movieId: String,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieReviewResponse //pass to

    @GET
    suspend fun getMovieTrailers(
        @Url
        movieId: String,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieTrailerResponse

    @GET
    suspend fun getMovies(
        @Url
        movieId: String,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse

    @GET
    suspend fun getMovieDetails(
        @Url
        movieId: String,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieDetailResponse
}

object TMDBApi {
    val movieListRetrofitService: TMDBApiService by lazy {
        movieListRetrofit.create(TMDBApiService::class.java)
    }
}