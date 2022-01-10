package net.basicmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_themepreview.*

class ThemePreviewActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themepreview)
        val i  = intent
        val id1 = i.getIntExtra("id1",-1)
        val id2 = i.getIntExtra("id2",-1)
        themepreview1.displayResourceImage(id1)
        themepreview2.displayResourceImage(id2)
    }
}