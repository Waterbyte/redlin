package sagar.mehar.rcarpet

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var part1: Button? = null
    private var part2: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        part1 = findViewById(R.id.Part1) as Button
        part2 = findViewById(R.id.Part2) as Button

        part1!!.setOnClickListener(this)
        part2!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        val intent: Intent
        when (v.id) {
            R.id.Part1 -> {
                intent = Intent(this@MainActivity, ImageActivity::class.java)
                startActivity(intent)
            }
            R.id.Part2 -> {
                intent = Intent(this@MainActivity, ContactActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
