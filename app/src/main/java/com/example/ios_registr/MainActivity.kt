package com.example.ios_registr

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var usern: EditText
    lateinit var email: EditText
    lateinit var passw: EditText
    lateinit var create: Button
    var isok = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usern = findViewById(R.id.username)
        email = findViewById(R.id.email)
        passw = findViewById(R.id.password)
        create = findViewById(R.id.create)

        var list = mutableListOf<User>()

        val cache = getSharedPreferences("CASHE", MODE_PRIVATE)

        val edit = cache.edit()


        val type = object : TypeToken<List<User>>() {}.type
        val gson = Gson()
        create.setOnClickListener {
            if (usern.text.isEmpty() || email.text.isEmpty() || passw.text.isEmpty()) {
                Toast.makeText(this, "Ma'lumotlarni to'ldiring!!!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var usern = usern.text.toString()
                var userp = passw.text.toString()
                var email = email.text.toString()
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
                    list.add(User(usern, email, userp,0))
                    var s = gson.toJson(list)
                    edit.putString("users", s).apply()
                    var intent = Intent(this, LogIn::class.java)
                    startActivity(intent)
                    intent.putExtra("str", str)
                } else {
                    Toast.makeText(
                        this,
                        "Bu username oldin ishlatilgan,boshqasini kiriting",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}