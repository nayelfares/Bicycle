package com.emarketing.bicycle

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.TextPaint
import android.view.Menu
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.ShopingActivity
import com.emarketing.bicycle.ui.AddMaterialActivity
import com.emarketing.bicycle.ui.ProfileDetails
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.profile_options.*


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
            startActivity(intent)
        }
        chilrenBehavior.setOnClickListener {
            val intent=Intent(this,ShopingActivity::class.java)
            intent.putExtra("catName",resources.getString(R.string.children_behavior))
            intent.putExtra("catId",1)
            startActivity(intent)
        }
        consultants.setOnClickListener {
            val intent=Intent(this,AddMaterialActivity::class.java)
            intent.putExtra("catName",resources.getString(R.string.consultants))
            startActivity(intent)
         }
        profile.setOnClickListener {
            val dialog = Dialog(this, R.style.Theme_Design_BottomSheetDialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.profile_options)
            dialog.editeProfile.setOnClickListener {
                startActivity(Intent(this,ProfileDetails::class.java))
                dialog.dismiss()
            }
            dialog.cancelOptions.setOnClickListener { dialog.dismiss() }
            dialog.materials.setOnClickListener {
                val intent=Intent(this,ShopingActivity::class.java)
                intent.putExtra("catName",resources.getString(R.string.shoping))
                intent.putExtra("user_id",BaseActivity.id.toString())
                startActivity(intent)
                dialog.dismiss()
            }
            dialog.events.setOnClickListener {

            }
            val window: Window = dialog.window!!
            window.setLayout(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            )

            dialog.show()
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