package com.example.ios_registr

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarItemView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Menu : AppCompatActivity() {
    private lateinit var logout: NavigationBarItemView
    private lateinit var rank: NavigationBarItemView
    private lateinit var test_btn: NavigationBarItemView
    private lateinit var start_test: Button
    private lateinit var person_name: TextView
    private lateinit var scroll: ScrollView
    private lateinit var linear: LinearLayout
    private var list = arrayListOf<User>()
    private var str_1 :String = ""

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        logout = findViewById(R.id.log_out)
        rank = findViewById(R.id.rank)
        test_btn = findViewById(R.id.test_btn)
        start_test = findViewById(R.id.start_test)
        person_name = findViewById(R.id.person_name)
        scroll = findViewById(R.id.scroll_view)
        linear = findViewById(R.id.linearr)

        val cache = getSharedPreferences("CASHE", MODE_PRIVATE)
        val edit = cache.edit()
        val type = object : TypeToken<List<User>>() {}.type
        val gson = Gson()
        var str = cache.getString("users", "")
        list = if (str == "") {
            arrayListOf<User>()
        } else {
            gson.fromJson(str, type)
        }


        create_rank()
        str_1 = intent.getStringExtra("str").toString()
        person_name.text = person_name.text.toString()+str_1
        logout.setOnClickListener {
            person_name = findViewById(R.id.person_name)
            finish()
        }
        test_btn.setOnClickListener {
            start_test.visibility = View.VISIBLE
        }
        start_test.setOnClickListener {
            var intent = Intent(this, Test::class.java)
            intent.putExtra("str_1", str_1)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    fun create_rank() {
        for (i in list.indices) {
            Log.d("bb", "${i + 1}.${list[i].log} : ${list[i].score}")
            var user = TextView(this)
            user.text = "${i + 1}.${list[i].log} : ${list[i].score}"
            linear.addView(user)
        }
    }
}