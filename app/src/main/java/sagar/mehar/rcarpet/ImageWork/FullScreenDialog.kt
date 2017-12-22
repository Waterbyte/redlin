package sagar.mehar.rcarpet.ImageWork

import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.squareup.picasso.Picasso

import sagar.mehar.rcarpet.R

/**
 * Created by Mountain on 20-12-2017.
 */

class FullScreenDialog : DialogFragment() {

    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.full_screen, container, false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = Bundle()
        imageUrl = arguments.getString("imageUrl")
        val imageView = view?.findViewById(R.id.fullscreen) as ImageView
        Log.v("Flag ", imageUrl)
        Picasso.with(activity)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .resize(300, 200) //original images are very small
                .into(imageView)
    }
}
