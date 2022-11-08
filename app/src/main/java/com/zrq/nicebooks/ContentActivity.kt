package com.zrq.nicebooks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.zrq.nicebooks.Constants.CONTENT_URL
import com.zrq.nicebooks.adapter.ContentAdapter
import com.zrq.nicebooks.bean.Chapter
import com.zrq.nicebooks.bean.Content
import com.zrq.nicebooks.databinding.ActivityContentBinding
import okhttp3.*
import java.io.IOException

class ContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initData()
        initEvent()
    }

    private lateinit var mBinding: ActivityContentBinding
    private var content = ArrayList<String>()
    private var chapterId = ""
    private lateinit var adapter: ContentAdapter

    private fun initData() {
        adapter = ContentAdapter(this, content)
        mBinding.rvContent.adapter = adapter
        mBinding.rvContent.layoutManager = LinearLayoutManager(this)
        chapterId = intent.getStringExtra("chapterId").toString()
        val request = Request.Builder().url("$CONTENT_URL$chapterId").get().build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {
                if (response.body != null) {
                    val json = response.body!!.string()
                    val c = Gson().fromJson(json, Content::class.java)
                    if (c?.data != null) {
                        Log.d("TAG", "onResponse: ${c.data}")
                        content.clear()
                        content.addAll(c.data)
                        runOnUiThread {
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }

    private fun initEvent() {

    }

}