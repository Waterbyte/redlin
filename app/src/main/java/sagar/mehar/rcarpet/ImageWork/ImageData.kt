package sagar.mehar.rcarpet.ImageWork

/**
 * Created by Mountain on 20-12-2017.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageData {

    @SerializedName("worldpopulation")
    @Expose
    var worldpopulation: List<WorldPopulation>? = null

}
