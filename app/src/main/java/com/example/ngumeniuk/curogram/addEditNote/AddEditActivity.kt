package com.example.ngumeniuk.curogram.addEditNote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ngumeniuk.curogram.R
import kotlinx.android.synthetic.main.activity_add_edit.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class AddEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        setOnClick()
        initEditTexts(intent.extras)
    }

    private fun initEditTexts(extras: Bundle?) {
        textET.setText(extras?.getString("text"))
        titleET.setText(extras?.getString("title"))
    }

    private fun setOnClick() {
        closeButton.onClick { finishWithSave() }
    }

    private fun finishWithSave() {
        val intent = Intent()
        intent.putExtra("id", this.intent?.extras?.getInt("id", -1))
        intent.putExtra("text", textET.text.toString())
        intent.putExtra("title", titleET.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        finishWithSave()
        super.onBackPressed()
    }
}
