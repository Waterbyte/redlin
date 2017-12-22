package sagar.mehar.rcarpet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sagar.mehar.rcarpet.ImageWork.APIService
import sagar.mehar.rcarpet.ImageWork.ImageData
import sagar.mehar.rcarpet.ImageWork.RecyclerAdapter
import sagar.mehar.rcarpet.ImageWork.WorldPopulation
import java.util.*

class ImageActivity : AppCompatActivity() {
    private lateinit var list: MutableList<WorldPopulation>
    private var recyclerView: RecyclerView? = null
    private val baseurl = "http://www.androidbegin.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        getData()
    }

    private fun getData() {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val apiService = retrofit.create<APIService>(APIService::class.java!!)
        val call = apiService.json

        call.enqueue(object : Callback<ImageData> {
            override fun onResponse(call: Call<ImageData>, response: Response<ImageData>) {
                val imageData = response.body()
                list = ArrayList(imageData!!.worldpopulation!!)
                setupView()
            }

            override fun onFailure(call: Call<ImageData>, t: Throwable) {
                Toast.makeText(this@ImageActivity, "Couldn't load Images", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun setupView() {
        recyclerView!!.layoutManager = GridLayoutManager(recyclerView!!.context, 2, GridLayoutManager.VERTICAL, false)

        val adapter = RecyclerAdapter(list, this@ImageActivity)
        recyclerView!!.adapter = adapter
    }


}
