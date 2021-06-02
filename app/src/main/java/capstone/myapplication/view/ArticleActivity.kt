package capstone.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import capstone.myapplication.R

class ArticleActivity : AppCompatActivity() {

    companion object{
        const val link = "link"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true

        val dataLink = intent.getStringExtra(link)

        webView.loadUrl(dataLink.toString())
    }
}