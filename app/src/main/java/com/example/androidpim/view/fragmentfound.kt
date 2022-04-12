package com.example.androidpim.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.androidpim.R
import com.example.androidpim.models.Post

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [fragmentfound.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentfound : Fragment() {


    private var listView: ListView? = null

    private var postList: List<Post>? = null

  /*  fun refreshList(search: String) {
        val parentActivity = view!!.context as FragmentActivity
        val mFinalList: MutableList<Post> = ArrayList<Post>()
        for (p in postList) {
            if (p.getTitle().toLowerCase().contains(search.toLowerCase())) {
                mFinalList.add(p)
            }
        }
        val postAdapter = PostAdapter(parentActivity, mFinalList)
        //PostAdapter postAdapter = new PostAdapter(parentActivity,postList);
        //postAdapter.getFilter().filter(search);
        listView.setAdapter(postAdapter)
    }*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      val  view = inflater.inflate(R.layout.fragment_lost, container, false)

        listView = view.findViewById<View>(R.id.listView) as ListView

        postList = ArrayList<Post>()
        // Inflate the layout for this fragment
        return view
    }


}