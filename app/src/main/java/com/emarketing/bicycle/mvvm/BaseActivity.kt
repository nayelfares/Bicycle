package com.emarketing.bicycle.mvvm

import android.app.Dialog
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emarketing.bicycle.R

abstract class BaseActivity: AppCompatActivity() {
    companion object{
        var id: Int = 0
        var token=""
    }
    var dialog: Dialog?=null
    fun showMessage(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    fun loading(){
        dialog= Dialog(this)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog?.setCancelable(false);
        dialog?.setContentView(R.layout.loading);
        dialog?.show()
    }

    fun stopLoading(){
        dialog?.dismiss()
    }

}