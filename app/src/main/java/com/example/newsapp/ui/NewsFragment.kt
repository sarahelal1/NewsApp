package com.example.newsapp.ui

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.api.ApiManager
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query
import android.app.SearchManager;
import android.content.Intent
import android.provider.ContactsContract
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.example.newsapp.*
import com.example.newsapp.model.*
import org.intellij.lang.annotations.Identifier


class NewsFragment : Fragment() {

    lateinit var selectedCategory: Category

  lateinit var source:SourcesItem



    companion object{
        fun getInstance(category: Category):NewsFragment{
val newsFragment=NewsFragment()
            newsFragment.selectedCategory=category
            return newsFragment
        }
    }
    lateinit var tabLayout:TabLayout
    lateinit var sourcesList:List<SourcesItem?>
    lateinit var progressBar: ProgressBar
    lateinit var searchView: SearchView
    lateinit var adapter: ArticlesAdapter
    lateinit var articlesRecyclerView: RecyclerView



    //lateinit var articlesList:List<ArticlesItem?>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
       getSources()
    }
    private fun initViews() {
        tabLayout = requireView().findViewById(R.id.tab_layout)
        progressBar = requireView().findViewById(R.id.progress_bar)
        searchView=(this.activity as HomeActivity?)!!.findViewById(R.id.searchView)
        adapter= ArticlesAdapter()

        articlesRecyclerView = requireView().findViewById(R.id.articles_recycler_view)
        articlesRecyclerView.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            val tabs = tabLayout.getChildAt(0) as ViewGroup


            override fun onTabSelected(tab: TabLayout.Tab?) {
                // val source=sourcesList!!.get(tab!!.position)
                source = tab!!.tag as SourcesItem
                loadArticles(source!!.id!!)
                articlesRecyclerView.layoutManager!!.scrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {

            override fun onQueryTextSubmit(p0: String?): Boolean
            {


              // var tab: TabLayout.Tab?=null
               // val source: SourcesItem = tab!!.tag as SourcesItem
                ApiManager.getApis().getSearchedTotalNews(
                    p0!!, source.id!!,Constants.API_KEY
                ).enqueue(object : Callback<ArticleResponse> {
                    override fun onResponse(
                        call: Call<ArticleResponse>,
                        response: Response<ArticleResponse>
                    ) {
                        adapter.items = response.body()!!.articles
                        adapter.notifyDataSetChanged()

                    }

                    override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                        Log.e("search article", "Error" + t.localizedMessage)
                    }

                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var tab: TabLayout.Tab?=null
                ApiManager.getApis().getSearchedTotalNews(
                    newText!!, source.id!!,Constants.API_KEY
                ).enqueue(object : Callback<ArticleResponse> {
                    override fun onResponse(
                        call: Call<ArticleResponse>,
                        response: Response<ArticleResponse>
                    ) {
                        adapter.items = response.body()!!.articles
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                        Log.e("search article", "Error" + t.localizedMessage)
                    }

                })
                return true
            }


        })
adapter.onItemClickListener=object :ArticlesAdapter.OnItemClickListener{
    override fun onItemClickListener(article: ArticlesItem) {
        val intent: Intent = Intent(requireContext(), NewsDetailsActivity::class.java)
        intent.putExtra("title", article.title)
        intent.putExtra("content",article.content)
        intent.putExtra("url_to_image",article.urlToImage)
        intent.putExtra("url",article.url)
        intent.putExtra("description",article.description)
        intent.putExtra("published",article.publishedAt)
        startActivity(intent)
    }

}
    }


    private fun loadArticles(sourceId: String) {
        progressBar.isVisible=true
        ApiManager.getApis().getArticles(
            apiKey = Constants.API_KEY,
            sourceId=sourceId

        ).enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                progressBar.isVisible=false
                adapter.items=response.body()!!.articles
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                progressBar.isVisible=false
                Log.e("onFailure","${t.localizedMessage}")
                Toast.makeText(context,"failed to load news", Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun getSources() {
        ApiManager.getApis().getSources(Constants.API_KEY, category = selectedCategory.id).enqueue(object :
            Callback<SourcesResponse> {
            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                progressBar.isVisible=false
                Log.e("onResponse",response.body()!!.status?:"unknown status")
                response.body()!!.sources?.forEach{source->
                    Log.e("onResponse","source name->${source!!.name}")

                }
                sourcesList=response.body()!!.sources!!
                showTabs()

            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                progressBar.isVisible=false
                Toast.makeText(context,"failed to load sources", Toast.LENGTH_LONG).show()
                Log.e("onFailure","${t.localizedMessage}")
            }
        })
    }

    private fun showTabs() {

        for (i in 0 until sourcesList!!.size){
            val tab=tabLayout.newTab()
            tab.text=sourcesList!!.get(i)!!.name
            tab.tag=sourcesList!!.get(i)
            tabLayout.addTab(tab)
            val tabs = tabLayout.getChildAt(0) as ViewGroup

            for (i in 0 until tabs.childCount ) {
                val tab = tabs.getChildAt(i)
                val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
                layoutParams.weight = 0f
                layoutParams.marginEnd = 12
                layoutParams.marginStart = 12
                layoutParams.width = 500
                tab.layoutParams = layoutParams
                tabLayout.requestLayout()
            }
        }

    }



    }

