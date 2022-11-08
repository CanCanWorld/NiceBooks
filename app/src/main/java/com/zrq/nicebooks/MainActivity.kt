package com.zrq.nicebooks

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.zrq.nicebooks.Constants.SEARCH_URL
import com.zrq.nicebooks.adapter.SearchBooksAdapter
import com.zrq.nicebooks.bean.Book
import com.zrq.nicebooks.bean.DataItem
import com.zrq.nicebooks.databinding.ActivityMainBinding
import com.zrq.nicebooks.interfaces.OnItemClickListener
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(), OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initData()
        initEvent()
    }

    private val list = mutableListOf<DataItem>()
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var adapter: SearchBooksAdapter
    private var offset = 1
    private var keyword = ""

    private fun initData() {
        adapter = SearchBooksAdapter(this, list, this)
        mBinding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun initEvent() {

        mBinding.apply {
            refreshLayout.setOnRefreshListener {
                offset = 1
                loadBooks()
            }
            refreshLayout.setOnLoadMoreListener {
                offset++
                loadBooks()
            }
        }

        mBinding.btnSearch.setOnClickListener {
            keyword = mBinding.etSearch.text.toString()
            loadBooks()
        }
    }

    private fun loadBooks() {
        val request = Request.Builder().url("$SEARCH_URL$keyword/$offset/20").get().build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                finishRefreshOrLoad()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {
                if (response.body != null) {
                    val json = response.body!!.string()
                    val book = Gson().fromJson(json, Book::class.java)
                    if (book?.data != null) {
                        if (offset == 1)
                            list.clear()
                        list.addAll(book.data)
                        runOnUiThread {
                            adapter.notifyDataSetChanged()
                        }
                        finishRefreshOrLoad()
                    } else {
                        finishRefreshOrLoad()
                    }
                } else {
                    finishRefreshOrLoad()
                }
            }
        })
    }

    private fun finishRefreshOrLoad() {
        runOnUiThread {
            mBinding.refreshLayout.finishRefresh()
            mBinding.refreshLayout.finishLoadMore()
        }
    }

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent(this, ChapterActivity::class.java).apply {
            putExtra("fictionId", list[position].fictionId)
        }
        startActivity(intent)
    }
}