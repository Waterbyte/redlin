package sagar.mehar.rcarpet.ImageWork


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WorldPopulation {
    @SerializedName("rank")
    @Expose
    private val rank: Int? = null
    @SerializedName("country")
    @Expose
    private val country: String? = null
    @SerializedName("population")
    @Expose
    private val population: String? = null

    @SerializedName("flag")
    @Expose
    var flag: String? = null

}