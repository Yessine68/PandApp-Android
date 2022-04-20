package com.example.androidpim.adapters

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.models.Post

class PostAdapter(// Declare variables
    private val context: FragmentActivity, postList: List<Post>
) :
    ArrayAdapter<Post?>(context, R.layout.card_post, postList) {
    private val postList: List<Post>
    private var textViewTitle: TextView? = null
    private var textViewDescription: TextView? = null
    lateinit var profile_icon: ImageView

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val view: View = inflater.inflate(R.layout.card_post, null, true)

        // Initialize
        textViewTitle = view.findViewById<View>(R.id.textViewTitle) as TextView
        profile_icon = view.findViewById(R.id.profile_icon)
        textViewDescription = view.findViewById<View>(R.id.textViewDescription) as TextView
        textViewDescription!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val post: Post = postList[position]
        val ppp = "http://10.0.2.2:3000/upload/download/"+post.image

        Glide.with(view).load(Uri.parse(ppp)).into(profile_icon)
        // Set the title and description to textView
        textViewTitle!!.setText(post.objet)
        textViewDescription!!.setText(post.place)
        return view
    }

    init {
        this.postList = postList
    }
}