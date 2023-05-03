package com.example.xtendtvwebview

import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.xtendtvwebview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
            binding.webView.loadUrl("https://www.xtendplex.com")

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webViewClient  = object : WebViewClient(){


            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                Toast.makeText(this@MainActivity, "URL changed to $url", Toast.LENGTH_SHORT).show()
                super.doUpdateVisitedHistory(view, url, isReload)
            }


            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                if (request != null && request.requestHeaders.containsKey("X-Requested-With")) {
                    Log.d("XHR", request.url.toString())
                    sendPost(view, request)
                }
                return super.shouldInterceptRequest(view, request)
            }
        }
    }

    private fun sendPost(
        view: WebView?,
        request: WebResourceRequest?
    ){
            val postData = ""
            view?.postUrl(request?.url.toString(), postData.toByteArray(Charsets.UTF_8))
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}