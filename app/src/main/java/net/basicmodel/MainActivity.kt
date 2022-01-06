package net.basicmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        nv.setNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.video -> {
                    startActivity(Intent(this,VideoActivity::class.java))
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}