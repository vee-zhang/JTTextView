package com.william.jttextviewdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.william.jttextview.JTTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jt1.setOnDrawableClickListener { switchState, view, actionId, currentText ->

            val str = when (actionId) {
                JTTextView.ACTION_START -> "left drawable"
                JTTextView.ACTION_TOP -> "top drawable"
                JTTextView.ACTION_END -> "right drawable"
                else -> "bottom drawable"
            }

            val test = if (switchState) "selected $str" else "give up $str"

            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }

        jt2.setOnDrawableClickListener { switchState, view, actionId, currentText ->

            if (switchState) {
                //down is true
                view.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                //up is false
                view.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        jt5.validator = object : JTTextView.Validator {
            override fun isValid(text: CharSequence?): Boolean {
                return when {
                    text == null -> false
                    text.isEmpty() -> {
                        Toast.makeText(this@MainActivity, "please input your account !", Toast.LENGTH_SHORT).show()
                        false
                    }
                    text.length > 6 -> {
                        Toast.makeText(this@MainActivity, "your account's length is longer then 6 !", Toast.LENGTH_SHORT).show()
                        false
                    }
                    else -> true
                }
            }

            override fun fixText(invalidText: CharSequence?) = invalidText

        }
    }
}
