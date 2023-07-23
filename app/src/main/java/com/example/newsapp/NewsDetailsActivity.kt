package com.example.newsapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class NewsDetailsActivity : AppCompatActivity() {
var title:String=""
    var content:String=""
    var url_to_image:String=""
    var url:String=""
    var description:String=""
    var published:String=""
    lateinit var titleTV:TextView
    lateinit var subDescriptionTV:TextView
    lateinit var contentTV:TextView
    lateinit var publishTV:TextView
    lateinit var imageView: ImageView
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        title=intent.getStringExtra("title").toString()
        content=intent.getStringExtra("content").toString()
        url_to_image=intent.getStringExtra("url_to_image").toString()
        url=intent.getStringExtra("url").toString()
        description=intent.getStringExtra("description").toString()
        published=intent.getStringExtra("published").toString()
        titleTV=findViewById(R.id.title)
        publishTV=findViewById(R.id.publish)
        subDescriptionTV=findViewById(R.id.description)
        contentTV=findViewById(R.id.content)
        imageView=findViewById(R.id.newsimage)
        button=findViewById(R.id.article_btn)
        titleTV.text=title
        publishTV.text=published
        subDescriptionTV.text=description
        contentTV.text=content
        Picasso.get().load(url_to_image).into(imageView)
        button.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)

        }
    }
}