package com.zrq.nicebooks

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.zrq.nicebooks.Constants.CHAPTER_URL
import com.zrq.nicebooks.adapter.ChapterAdapter
import com.zrq.nicebooks.bean.Chapter
import com.zrq.nicebooks.bean.ChapterListItem
import com.zrq.nicebooks.bean.Data
import com.zrq.nicebooks.databinding.ActivityChapterBinding
import com.zrq.nicebooks.interfaces.OnItemClickListener
import okhttp3.*
import java.io.IOException

class ChapterActivity : AppCompatActivity(), OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityChapterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initData()
        initEvent()
    }

    private lateinit var mBinding: ActivityChapterBinding
    private var fiction = ""
    private var data: Data = Data("", "", "", ArrayList<ChapterListItem>(), "", "", "", "")
    private lateinit var adapter: ChapterAdapter

    private fun initData() {
        adapter = ChapterAdapter(this, data.chapterList!!, this)
        mBinding.rvChapter.adapter = adapter
        mBinding.rvChapter.layoutManager = LinearLayoutManager(this)

        fiction = intent.getStringExtra("fictionId").toString()
        val request = Request.Builder().url("$CHAPTER_URL$fiction").get().build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.body != null) {
                    val json = response.body!!.string()
                    val chapter = Gson().fromJson(json, Chapter::class.java)
                    if (chapter?.data != null) {
                        data.title = chapter.data.title
                        data.cover = chapter.data.cover
                        data.chapterList?.apply {
                            clear()
                            chapter.data.chapterList?.let { addAll(it) }
                        }
                        runOnUiThread {
                            refreshUI()
                        }
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshUI() {
        mBinding.apply {
            if (data.cover != "") {
                Glide.with(this@ChapterActivity).load(data.cover).into(ivHead)
            }
            tvTitle.title = data.title
            adapter.notifyDataSetChanged()
        }
    }

    private fun initEvent() {

    }

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent(this, ContentActivity::class.java).apply {
            putExtra("chapterId", this@ChapterActivity.data.chapterList?.get(position)?.chapterId)
        }
        startActivity(intent)
    }

}