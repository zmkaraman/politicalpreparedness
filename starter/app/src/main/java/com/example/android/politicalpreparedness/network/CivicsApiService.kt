package com.example.android.politicalpreparedness.network

import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"
private const val API_KEY = "AIzaSyCv7qJbUF87wxet4psRA_P3hKqLEDvsFVg"

// TODO: Add adapters for Java Date and custom adapter ElectionAdapter (included in project)
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(CivicsHttpClient.getClient())
        .baseUrl(BASE_URL)
        .build()

/**
 *  Documentation for the Google Civics API Service can be found at https://developers.google.com/civic-information/docs/v2
 */

interface CivicsApiService {

    //Add elections API Call
    //https://www.googleapis.com/civicinfo/v2/elections?key=AIzaSyCv7qJbUF87wxet4psRA_P3hKqLEDvsFVg
    @GET("elections")
    suspend fun getElections(@Query("key") apiKey: String = API_KEY): ResponseBody

    //https://www.googleapis.com/civicinfo/v2/voterinfo?key=AIzaSyCv7qJbUF87wxet4psRA_P3hKqLEDvsFVg&voter_key=us,%20la&electionId=7022
    @GET("voterinfo")
    suspend fun getVoterInfo(@Query("key") apiKey: String = API_KEY, @Query("voter_key") voterKey: String?, @Query("electionId") electionId: String?): ResponseBody


    //https://www.googleapis.com/civicinfo/v2/representatives  byadress
    @GET("representatives")
    suspend fun getRepresentativesByAdress(@Query("key") apiKey: String = API_KEY, @Query("address") address: String): ResponseBody

    //TODO MERVE burasinin cagrimi farkli duzeltilmeli
    //https://civicinfo.googleapis.com/civicinfo/v2/representatives/ocd-division%2Fcountry%3Aus?key=AIzaSyCv7qJbUF87wxet4psRA_P3hKqLEDvsFVg
    @GET("representatives/")
    suspend fun getRepresentativesByOcdId(@Query("ocdId") ocdId: String?, @Query("key") apiKey: String = API_KEY): ResponseBody

}

object CivicsApi {
    val retrofitService: CivicsApiService by lazy {
        retrofit.create(CivicsApiService::class.java)
    }
}