package com.example.ios_registr

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.ios_registr.model.test
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Test : AppCompatActivity(), OnClickListener {

    private lateinit var question: TextView
    private lateinit var group: RadioGroup
    private lateinit var answer1: RadioButton
    private lateinit var answer2: RadioButton
    private lateinit var answer3: RadioButton
    private lateinit var answer4: RadioButton
    private lateinit var finish: Button
    private lateinit var next: Button
    private lateinit var prev: Button
    private lateinit var card: CardView
    private lateinit var again: ImageView
    private lateinit var correcans: TextView
    private lateinit var linear: LinearLayout
    private var status = true
    private var status_1 = false
    private var list = mutableListOf<test>()
    private var list_btn = mutableListOf<Button>()
    private var list_2 = arrayListOf<User>()
    private var index = 0

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        question = findViewById(R.id.question)
        answer1 = findViewById(R.id.answer1)
        answer2 = findViewById(R.id.answer2)
        answer3 = findViewById(R.id.answer3)
        answer4 = findViewById(R.id.answer4)
        next = findViewById(R.id.next)
        prev = findViewById(R.id.previus)
        group = findViewById(R.id.radioGroup)
        finish = findViewById(R.id.finish)
        card = findViewById(R.id.card2)
        again = findViewById(R.id.restart)
        correcans = findViewById(R.id.correctans)
        linear = findViewById(R.id.liner)

        list.add(test("vulnerable", "weak", "strong", "brave", "power", "", "weak"))
        list.add(test("target", "dish", "quary", "absorb", "civil", "", "quary"))
        list.add(test("comfort", "convinient", "blaze", "ease", "fault", "", "convinient"))
        list.add(test("gain", "gesture", "grave", "innocent", "acquired", "", "acquired"))
        createButton(list.size)
        createTest(index)

        finish.setOnClickListener {
            var a = AnimationUtils.loadAnimation(this, R.anim.anim)
            card.startAnimation(a)
            card.visibility = View.VISIBLE
            gameover()
            correcans.text = correcans.text.toString() + finishing().toString()
            var str_1 = intent.getStringExtra("str_1").toString()
            val cache = getSharedPreferences("CASHE", MODE_PRIVATE)
            val edit = cache.edit()
            val type = object : TypeToken<List<User>>() {}.type
            val gson = Gson()
            var str = cache.getString("users", "")
            list_2 = if (str == "") {
                arrayListOf<User>()
            } else {
                gson.fromJson(str, type)
            }
            for (i in list_2) {
                if (i.log == str_1) {
                    i.score += finishing()
                }
            }
            var s = gson.toJson(list_2)
            edit.putString("users", s).apply()
        }
        again.setOnClickListener {
            color_btn()
            finish()
        }
        answer1.setOnClickListener {
            var a = findViewById<RadioButton>(group.checkedRadioButtonId)
            onclick(a, index)
            Log.d("TAG", "onCreate: a1")
        }
        answer2.setOnClickListener {
            var a = findViewById<RadioButton>(group.checkedRadioButtonId)
            onclick(a, index)
            Log.d("TAG", "onCreate: a2")
        }
        answer3.setOnClickListener {
            var a = findViewById<RadioButton>(group.checkedRadioButtonId)
            onclick(a, index)
            Log.d("TAG", "onCreate: a3")
        }
        answer4.setOnClickListener {
            var a = findViewById<RadioButton>(group.checkedRadioButtonId)
            onclick(a, index)
            Log.d("TAG", "onCreate: a4")
        }
        next.setOnClickListener {
            group.clearCheck()
            if (index < list.size - 1) {
                index++
                createTest(index)
                if (index == list.size - 1) {
                    finish.visibility = View.VISIBLE
                    next.visibility = View.INVISIBLE
                }
            }
            prev.visibility = View.VISIBLE
            check(index)


        }
        prev.setOnClickListener {
            finish.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE
            group.clearCheck()
            if (index < list.size && index != 0) {
                index--
                createTest(index)
            }
            check(index)
            if (index == 0) {
                prev.visibility = View.INVISIBLE
            }

        }
    }


    fun createTest(i: Int) {
        question.text = list[i].qustion
        answer1.text = list[i].answer1
        answer2.text = list[i].answer2
        answer3.text = list[i].answer3
        answer4.text = list[i].answer4
    }


    fun check(i: Int) {
        if (!list[i].choosen.isEmpty()) {
            if (answer1.text == list[i].choosen) {
                answer1.isChecked = true
            } else if (answer2.text == list[i].choosen) {
                answer2.isChecked = true
            } else if (answer3.text == list[i].choosen) {
                answer3.isChecked = true
            } else {
                answer4.isChecked = true
            }
            Log.d("TAG", "check: not empyt" + " ${list[i].choosen}")
        } else Log.d("TAG", "check: empyt")
        color_btn()
    }

    fun onclick(view: View?, i: Int) {
        var view = findViewById<RadioButton>(view!!.id)
        list[i].choosen = view.text.toString()
        Log.d("TAG", "TARGET : ${list[i].choosen}")
    }

    fun finishing(): Int {
        var correctans = 0
        for (i in list) {
            if (i.choosen == i.correct) {
                correctans++
            }

        }
        return correctans
    }

    fun gameover() {
        if (status) {
            finish.isEnabled = false
            prev.isEnabled = false
            group.isEnabled = false
            status = false
        } else {
            finish.isEnabled = true
            prev.isEnabled = true
            group.isEnabled = true
            status = true
        }
    }

    fun createButton(view: Int) {
        for (i in 1..view) {
            var btn = Button(this)
            btn.id = i
            btn.text = i.toString()
            linear.addView(btn)
            btn.setOnClickListener(this)
            btn.setBackgroundResource(R.drawable.stroke)
            list_btn.add(btn)
        }
    }


    override fun onClick(p0: View?) {
        var btn = findViewById<Button>(p0!!.id)
        index = btn.text.toString().toInt() - 1
        group.clearCheck()
        createTest(index)
        check(index)
        if (index == 0) {
            prev.visibility = View.INVISIBLE
        } else prev.visibility = View.VISIBLE
        if (index != list.size - 1) {
            finish.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE
        } else {
            finish.visibility = View.VISIBLE
            next.visibility = View.INVISIBLE
        }
    }

    fun color_btn() {
        for (i in list.indices) {
            if (list[i].choosen.isNotEmpty()) {
                if (!status_1) list_btn[i].setBackgroundResource(R.drawable.blue_btn)
            } else list_btn[i].setBackgroundResource(R.drawable.stroke)
        }
    }
}