package com.emarketing.bicycle

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.TextPaint
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.emarketing.bicycle.ui.ShopingActivity
import com.emarketing.bicycle.ui.ConsultantsActivity
import com.emarketing.bicycle.ui.ProfileDetails
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val paint: TextPaint = appName.paint
        val width = paint.measureText(appName.text.toString())

        val textShader: Shader = LinearGradient(
            0f, 0f, width, appName.textSize, intArrayOf(
                Color.parseColor("#c2e59c"),
                Color.parseColor("#64b3f4")
            ), null, Shader.TileMode.CLAMP
        )
        appName.paint.shader = textShader
        shoping.setOnClickListener {
            val intent=Intent(this,ShopingActivity::class.java)
            intent.putExtra("catName",resources.getString(R.string.shoping))
            intent.putExtra("catId",2)
            startActivity(intent)
        }
        chilrenBehavior.setOnClickListener {
            val intent=Intent(this,ShopingActivity::class.java)
            intent.putExtra("catName",resources.getString(R.string.children_behavior))
            intent.putExtra("catId",1)
            startActivity(intent)
        }
        consultants.setOnClickListener {
            val intent=Intent(this,ConsultantsActivity::class.java)
            intent.putExtra("catName",resources.getString(R.string.consultants))
            startActivity(intent)
         }
        editProfile.setOnClickListener {
            startActivity(Intent(this,ProfileDetails::class.java))
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.app_name))
        builder.setMessage(resources.getString(R.string.do_you_want_to_close_app))
        builder.setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
            finish()
        }
        builder.setNegativeButton(resources.getString(R.string.no)) { _, _ ->

        }
        builder.show()
    }
}