package com.example.androidpim.adapters

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.androidpim.ChatRoomActivity
import com.example.androidpim.R
import com.example.androidpim.models.Post
import com.example.androidpim.models.chatList
import com.example.androidpim.service.BASE_URL
import com.example.androidpim.service.ElearningApi
import com.example.androidpim.service.LostPostApi
import com.example.androidpim.service.RetrofitApi
import kotlinx.android.synthetic.main.activity_entrance.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostAdapter(// Declare variables
    private val context: FragmentActivity, postList: List<Post>
) :
    ArrayAdapter<Post?>(context, R.layout.card_post, postList) {
    lateinit var mSharedPref: SharedPreferences
    var mContext: Context? = null

    private val postList: List<Post>
    private var textViewTitle: TextView? = null
    private var textViewDescription: TextView? = null
    lateinit var profile_icon: ImageView
    lateinit var chat: Button
    lateinit var found: Button
    lateinit var notFound: Button


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        mSharedPref = context.getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val email=mSharedPref.getString("email", "zwayten").toString()
        val name=mSharedPref.getString("FirstName", "zwayten").toString()
        val pic=mSharedPref.getString("profilePicture", "zwayten").toString()


        println("hello"+email)
        val inflater = context.layoutInflater
        val post: Post = postList[position]
        // Set the title and description to textView


        if(post.email==email ){
            println("email")
            val view: View = inflater.inflate(R.layout.mycardpost, null, true)
            // Initialize
            textViewTitle = view.findViewById<View>(R.id.eventTitle) as TextView
            profile_icon = view.findViewById(R.id.profile_icon)
            textViewDescription = view.findViewById<View>(R.id.textViewDescription) as TextView
            found =view.findViewById(R.id.found)
            notFound=view.findViewById(R.id.NotFound)

            if(post.state){
                notFound.visibility=VISIBLE
                found.visibility=INVISIBLE

            }else{
                notFound.visibility=INVISIBLE
                found.visibility=VISIBLE

            }

            found.setOnClickListener {
                post.state=true
                val apiuser = LostPostApi.create()
                apiuser.changeState(post._id,post).enqueue(object: Callback<Boolean> {
                    override fun onResponse(
                        call: Call<Boolean>,
                        response: Response<Boolean>
                    ) {
                        if(response.isSuccessful){
                            Log.i("onResponse goooood", response.body().toString())

                        } else {
                            Log.i("OnResponse not good", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {

                        println("noooooooooooooooooo")
                    }

                })

                notFound.visibility=VISIBLE
                found.visibility=INVISIBLE

            }
            notFound.setOnClickListener {
                post.state=false
                val apiuser = LostPostApi.create()
                apiuser.changeState(post._id,post).enqueue(object: Callback<Boolean> {
                    override fun onResponse(
                        call: Call<Boolean>,
                        response: Response<Boolean>
                    ) {
                        if(response.isSuccessful){
                            Log.i("onResponse goooood", response.body().toString())

                        } else {
                            Log.i("OnResponse not good", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {

                        println("noooooooooooooooooo")
                    }

                })

                notFound.visibility=INVISIBLE
                found.visibility=VISIBLE
            }

            textViewDescription!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            val ppp = BASE_URL+"upload/download/"+post.image
            textViewTitle!!.setText(post.objet)
            textViewDescription!!.setText(post.place)
            Glide.with(view).load(Uri.parse(ppp)).into(profile_icon)
            return view
        }else{
            print("not email")
            val view:View= inflater.inflate(R.layout.card_post, null, true)
            // Initialize
            textViewTitle = view.findViewById<View>(R.id.textViewTitle) as TextView
            profile_icon = view.findViewById(R.id.profile_icon)
            textViewDescription = view.findViewById<View>(R.id.textViewDescription) as TextView
            textViewDescription!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            chat=view.findViewById(R.id.chat)
           chat.setOnClickListener {
                val userName = name
                val roomName = post.email+email
                if(!roomName.isNullOrBlank()&&!userName.isNullOrBlank()) {
                    val apiuser = ElearningApi.create()
                    context.startActivity<ChatRoomActivity>(
                        "userName" to userName,
                        "roomName" to roomName,
                    "userPost" to post.publisheId
                        )
                    val data: LinkedHashMap<String, String> = LinkedHashMap()
                    data["userName"] = post.publisheId
                    data["userNameReciver"]= name
                    data["chatRoom"] = roomName
                    data["emailPost"]= post.email
                    data["emailUser"]= email
                    data["senderPic"]= pic



                    apiuser.addRoom(data).enqueue(object: Callback<chatList> {
                        override fun onResponse(
                            call: Call<chatList>,
                            response: Response<chatList>
                        ) {
                            if(response.isSuccessful){
                                Log.i("onResponse goooood", response.body().toString())

                            } else {
                                Log.i("OnResponse not good", response.body().toString())
                            }
                        }

                        override fun onFailure(call: Call<chatList>, t: Throwable) {

                            println("noooooooooooooooooo")
                        }

                    })
                }else{
                    Toast.makeText(context,"Nickname and Roomname should be filled!", Toast.LENGTH_SHORT)
                }
            }
            val ppp = BASE_URL+"upload/download/"+post.image
            textViewTitle!!.setText(post.objet)
            textViewDescription!!.setText(post.place)
            Glide.with(view).load(Uri.parse(ppp)).into(profile_icon)
            return view
        }

    }

    init {
        this.postList = postList
    }
}