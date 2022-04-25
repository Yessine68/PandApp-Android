package com.example.androidpim.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidpim.R
import com.example.androidpim.adapters.DocumentAdapter
import com.example.androidpim.adapters.EventAdapter
import com.example.androidpim.models.ClubLoggedIn
import com.example.androidpim.models.Document
import com.example.androidpim.models.Event
import com.example.androidpim.service.RetrofitApi
import com.example.androidpim.view.LkolPro
import com.example.androidpim.view.ProfileUser
import com.example.androidpim.view.UserEdit
import com.marcoscg.dialogsheet.DialogSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DocumentsFragment : Fragment(R.layout.document_fragment) {

    lateinit var mSharedPref: SharedPreferences
    lateinit var imageProfileDoc: ImageView
    lateinit var usernameProfileDoc: TextView
    lateinit var documentsrequestDoc:Button
    lateinit var editprofilebuttonDoc:Button
    lateinit var submitdocbutton:Button
    lateinit var profDoc:Button
    var mContext: Context? = null




    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.document_fragment, parent, false)
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (parent != null) {
            this.mContext = parent.getContext()
        };
        mSharedPref = requireActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE)

        imageProfileDoc = view.findViewById(R.id.imageProfileDoc)
        usernameProfileDoc = view.findViewById(R.id.usernameProfileDoc)
        documentsrequestDoc = view.findViewById(R.id.documentsrequestDoc)
        editprofilebuttonDoc = view.findViewById(R.id.editprofilebuttonDoc)
        submitdocbutton = view.findViewById(R.id.submitdocbutton)
        profDoc = view.findViewById(R.id.profDoc)
        profDoc.setOnClickListener {
            var lkolPro = (activity as LkolPro)
            lkolPro.supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileUser()).commit()
        }


        editprofilebuttonDoc.setOnClickListener {
            var lkolPro = (activity as LkolPro)
            lkolPro.supportFragmentManager.beginTransaction().replace(R.id.frame, UserEdit()).commit()
        }

        //-----------------------------------------------------
        val firstName: String = mSharedPref.getString("FirstName", "zwayten").toString()
        val lastName: String = mSharedPref.getString("LastName", "zwayten").toString()
        val id: String = mSharedPref.getString("id", "zwayten").toString()

        println("ellll iddddddddddddddddddddddddddddddddddd: "+id)
        usernameProfileDoc.text = firstName +" "+lastName;
        val picStr: String = mSharedPref.getString("profilePicture", "email").toString()
        println("###############################################"+picStr)
        val ppp = "http://10.0.2.2:3000/upload/download/"+picStr
        Glide.with(this).load(Uri.parse(ppp)).into(imageProfileDoc)

        //-----------------------------------------------------
        val dialogDocument = this.mContext?.let { it1 -> DialogSheet(it1, true) }
        dialogDocument?.setView(R.layout.document_add)
        val inflatedView2 = dialogDocument?.inflatedView


        var doc = Document()
        val typebox = inflatedView2?.findViewById<Spinner>(R.id.typebox)
        val livetype = inflatedView2?.findViewById<TextView>(R.id.livetype)
        val languagebox = inflatedView2?.findViewById<Spinner>(R.id.languagebox)
        val subbb = inflatedView2?.findViewById<Button>(R.id.subbb)
        val copiesnum = inflatedView2?.findViewById<NumberPicker>(R.id.copiesnum)
        val paragraph = inflatedView2?.findViewById<TextView>(R.id.paragraph)
        copiesnum?.minValue = 0
        copiesnum?.maxValue = 3
        copiesnum?.wrapSelectorWheel = true
        copiesnum?.setOnValueChangedListener { picker, oldVal, newVal ->
           doc.numcopies = newVal

        }

        paragraph?.setText(firstName +" "+lastName+ " identified by "+ mSharedPref.getString("identifiant", "zwayten").toString() +  " Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")

    typebox?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                livetype?.setText(typebox?.getSelectedItem().toString())
                livetype?.setTypeface(null, Typeface.BOLD)
                doc.documentType = typebox?.getSelectedItem().toString()
            }

        }

        languagebox?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }



            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                doc.docLanguage = languagebox?.getSelectedItem().toString()
                if(doc.docLanguage == "French"){
                    paragraph?.setText(firstName +" "+lastName+ " identifié par "+ mSharedPref.getString("identifiant", "zwayten").toString() +  " Lorem Ipsum est simplement un faux texte de l'industrie de l'impression et de la composition. Le Lorem Ipsum est le texte factice standard de l'industrie depuis les années 1500, lorsqu'un imprimeur inconnu a pris une galère de caractères et l'a brouillé pour en faire un livre de spécimens de caractères. Il a survécu non seulement à cinq siècles, mais aussi au saut dans la composition électronique, restant essentiellement inchangé. Il a été popularisé dans les années 1960 avec la sortie de feuilles Letraset contenant des passages de Lorem Ipsum, et plus récemment avec des logiciels de publication assistée par ordinateur comme Aldus PageMaker comprenant des versions de Lorem Ipsum.")
                }
                if(doc.docLanguage == "English"){
                    paragraph?.setText(firstName +" "+lastName+ " identified by "+ mSharedPref.getString("identifiant", "zwayten").toString() +  " Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                }
                if(doc.docLanguage == "Arabic"){
                    paragraph?.setText(firstName +" "+lastName+ " التي حددتها "+ mSharedPref.getString("identifiant", "zwayten").toString() +  " لوريم إيبسوم هو ببساطة نص شكلي يستخدم في صناعة الطباعة والتنضيد. كان Lorem Ipsum هو النص الوهمي القياسي في الصناعة منذ القرن الخامس عشر الميلادي ، عندما أخذت طابعة غير معروفة لوحًا من النوع وتدافعت عليه لعمل كتاب عينة. لقد نجت ليس فقط خمسة قرون ، ولكن أيضًا القفزة في التنضيد الإلكتروني ، وظلت دون تغيير جوهري. تم نشره في الستينيات من القرن الماضي بإصدار أوراق Letraset التي تحتوي على مقاطع Lorem Ipsum ، ومؤخرًا مع برامج النشر المكتبي مثل Aldus PageMaker بما في ذلك إصدارات ")
                }
            }

        }


        parent?.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.doc_lang,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                languagebox?.adapter = adapter
            }
        }
        parent?.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.type_doc,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                typebox?.adapter = adapter
            }
        }

        subbb?.setOnClickListener{
            doc.claimedId = id
            val apiuser = RetrofitApi.create().requestDoc(doc)
            apiuser.enqueue(object : Callback<Document> {
                override fun onResponse(call: Call<Document>, response: Response<Document>) {
                    println("behy")
                }

                override fun onFailure(call: Call<Document>, t: Throwable) {
                    println("mahouch behy")
                }

            })
        }

        submitdocbutton.setOnClickListener {

            dialogDocument?.show()
        }


        val activity = activity as Context

        val eventRecycler = view.findViewById<RecyclerView>(R.id.recycledocument)
        eventRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val apiuser = RetrofitApi.create().GetDocuments()

        var eventList = ArrayList<Document>()

        apiuser.enqueue( object : Callback<List<Document>> {
            override fun onResponse(call: Call<List<Document>>, response: Response<List<Document>>) {

                for (i in 0 until response.body()!!.size)

                    eventList.add(response.body()!![i])
                val eventAdapter = DocumentAdapter(eventList)
                eventRecycler.adapter = eventAdapter
            }

            override fun onFailure(call: Call<List<Document>>, t: Throwable) {
                println("arja3")
            }

        })


        return view
    }

}