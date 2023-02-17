package com.example.ios_registr

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var usern: EditText
    lateinit var email: EditText
    lateinit var passw: EditText
    lateinit var create: Button
    lateinit var text_1: TextInputLayout
    lateinit var text_2: TextInputLayout
    lateinit var text_3: TextInputLayout
    var isok = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usern = findViewById(R.id.username)
        email = findViewById(R.id.email)
        passw = findViewById(R.id.password)
        text_1 = findViewById(R.id.textInputLayout)
        text_2 = findViewById(R.id.textInputLayout3)
        text_3 = findViewById(R.id.textInputLayout4)
        create = findViewById(R.id.create)

        var list = mutableListOf<User>()

        val cache = getSharedPreferences("CASHE", MODE_PRIVATE)

        val edit = cache.edit()


        val type = object : TypeToken<List<User>>() {}.type
        val gson = Gson()
        var fa = email.text.toString()
        create.setOnClickListener {
            if (usern.text.isEmpty() || email.text.isEmpty() || passw.text.isEmpty()) {
                Toast.makeText(this, "Ma'lumotlarni to'ldiring!!!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (check_email(fa)) {
                    text_2.helperText = "Emailni to'g'ri kiriting"
                } else {
                    var usern = usern.text.toString()
                    var userp = passw.text.toString()
                    var email = email.text.toString()
                    text_2.helperText = "Ma'lumotlarni to'ldiring!!!"
                    var str = cache.getString("users", "")
                    list = if (str == "") {
                        mutableListOf<User>()
                    } else {
                        gson.fromJson(str, type)
                    }
                    for (i in list.indices) {
                        if (usern == list[i].log) isok = true
                    }
                    if (!isok) {
                        list.add(User(usern, email, userp, 0))
                        var s = gson.toJson(list)
                        edit.putString("users", s).apply()
                        var intent = Intent(this, LogIn::class.java)
                        startActivity(intent)
                        intent.putExtra("str", str)
                    } else {
                        text_1.helperText = "Bu username oldin ishlatilgan,boshqasini kiriting"
                        isok = false
                    }
                }
            }
        }
    }

    fun check_email(s: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
            return true
        }
        return false
    }
}