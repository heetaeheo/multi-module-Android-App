package com.example.news_presentation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.common_utils.Navigator
import com.example.news_presentation.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(activity: Activity){
            val intent = Intent(activity, NewsActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val binding: ActivityNewsBinding by lazy {
        ActivityNewsBinding.inflate(layoutInflater)
    }

    private val newsViewModel: NewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()

        setObservers()
    }

    private fun initView(){
        binding.rvArticles.adapter = newsAdapter
    }

    private fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                newsViewModel.newsArticle.collectLatest { article ->
                    if (article.isLoading) {
                        binding.progressBar.visibility= View.VISIBLE
                    }
                    if (article.error.isNotBlank()) {
                        binding.progressBar.visibility= View.GONE
                        Toast.makeText(this@NewsActivity, article.error, Toast.LENGTH_LONG).show()
                    }
                    article.data?.let {
                        binding.progressBar.visibility= View.GONE
                        newsAdapter.setData(it)
                    }
                }
            }
        }
    }
}

object GoToNewsActivity : Navigator {
    override fun navigate(activity: Activity) {
        NewsActivity.launchActivity(activity)
    }

}