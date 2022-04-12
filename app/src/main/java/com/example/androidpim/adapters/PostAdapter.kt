package com.example.androidpim.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.androidpim.R
import com.example.androidpim.models.Post

class PostAdapter(// Declare variables
    private val context: FragmentActivity, postList: List<Post>
) :
    ArrayAdapter<Post?>(context, R.layout.card_post, postList) {
    private val postList: List<Post>
    private var textViewTitle: TextView? = null
    private var textViewDescription: TextView? = null
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val view: View = inflater.inflate(R.layout.card_post, null, true)

        // Initialize
        textViewTitle = view.findViewById<View>(R.id.textViewTitle) as TextView
        textViewDescription = view.findViewById<View>(R.id.textViewDescription) as TextView
        textViewDescription!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val post: Post = postList[position]

        // Set the title and description to textView
        textViewTitle!!.setText(post.objet)
        textViewDescription!!.setText(post.type)
        return view
    }

    init {
        this.postList = postList
    }
}