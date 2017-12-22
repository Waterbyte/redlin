package sagar.mehar.rcarpet.ImageWork

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.squareup.picasso.Picasso

import sagar.mehar.rcarpet.ImageActivity
import sagar.mehar.rcarpet.R

/**
 * Created by Mountain on 20-12-2017.
 */

class RecyclerAdapter(private val list: List<WorldPopulation>, private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.MyHolder>() {
    var mClickListener: View.OnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_show, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val worldPopulation = list[position]
        val imageUrl = worldPopulation.flag
        Picasso.with(context).load(imageUrl).placeholder(R.drawable.placeholder_image).into(holder.image)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var image: ImageView

        init {
            image = itemView.findViewById(R.id.imageView) as ImageView

            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val fullScreenDialog = FullScreenDialog()
                    val args = Bundle()
                    args.putString("imageUrl", list[pos].flag)
                    fullScreenDialog.arguments = args
                    fullScreenDialog.show((context as ImageActivity).fragmentManager, "FullScreen")
                }
            }
        }
    }


}
