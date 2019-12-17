package com.github.john.tsputildemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.john.tsputil.TSPUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {

        refreshApp()
        refreshMore()
        refreshUser()

        ic_app.setOnClickListener {
            val s = user_input.text.toString()
            if (s.isNotEmpty()) {
                TSPUtil.get().putInApp(
                    TSPUtil.app().all.size.toString(), s)
                refreshApp()
                Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()
            }
        }
        ic_user.setOnClickListener {
            val s = user_input.text.toString()
            if (s.isNotEmpty()) {
                // 存储到用户sp
                TSPUtil.get().putInUser(
                    TSPUtil.user().all.size.toString(), s)
                refreshUser()
                Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()
            }
        }
        ic_more.setOnClickListener {
            val s = user_input.text.toString()
            if (s.isNotEmpty()) {
                // 随机存储
                val index = Random.nextInt(4)
                TSPUtil.get().putInMore(index, TSPUtil.more()[index].all.size.toString(), s)
                refreshMore()
                Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refreshApp() {
        val s = StringBuilder()
        TSPUtil.app().all.forEach {
            s.append(it).append("\n")
        }
        app.text = s.toString()
    }

    private fun refreshUser() {
        val s = StringBuilder()
        TSPUtil.user().all.forEach {
            s.append(it).append("\n")
        }
        user.text = s.toString()
    }

    private fun refreshMore() {
        val s = StringBuilder()
        TSPUtil.more().forEach {
            it.all.forEach { value ->
                s.append(value).append("\n")
            }
        }
        more.text = s.toString()
    }
}
