package sagar.mehar.rcarpet.ImageWork

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Mountain on 20-12-2017.
 */

interface APIService {
    @get:GET("tutorial/jsonparsetutorial.txt")
    val json: Call<ImageData>
}
