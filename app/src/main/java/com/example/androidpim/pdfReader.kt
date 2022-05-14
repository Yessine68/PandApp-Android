package com.example.androidpim

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.androidpim.service.BASE_URL
import com.github.barteksc.pdfviewer.PDFView
import java.io.File


class pdfReader : AppCompatActivity() {
     var  progressBar: ProgressBar? = null
    lateinit var imageViewBack: ImageView

    @SuppressLint("SetJavaScriptEnabled")
    lateinit var pdfReader: pdfReader;
    lateinit var pdfView: PDFView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)
        var pdfName=intent.getStringExtra("pdfName")
        PRDownloader.initialize(applicationContext)
        pdfView=findViewById(R.id.PDFView)
        downloadPdfFromInternet(
            BASE_URL +"upload/download/"+pdfName,
            getRootDirPath(this),
            "courses"
        )
        imageViewBack=findViewById(R.id.imageViewBack)
        imageViewBack.setOnClickListener {
            finish()
        }

    }

    private fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }

    private fun showPdfFromFile(file: File) {
        pdfView.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, _ ->
                Toast.makeText(
                    this@pdfReader,
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }
    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String) {
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Toast.makeText(this@pdfReader, "downloadComplete", Toast.LENGTH_LONG)
                        .show()
                    val downloadedFile = File(dirPath, fileName)
                    progressBar?.visibility = View.GONE
                    showPdfFromFile(downloadedFile)
                }

                override fun onError(error: com.downloader.Error?) {
                    Toast.makeText(
                        this@pdfReader,
                        "Error in downloading file : $error",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })


    }
}

