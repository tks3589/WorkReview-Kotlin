package com.example.workreview_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.workreview_kotlin.model.Member
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.json.JSONObject
import kotlin.Exception

class MainActivity : AppCompatActivity() {
    private var membersArrayList: ArrayList<Member> = ArrayList()
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = CoroutineScope(Dispatchers.IO).launch{
            try {
                val members = RetrofitHelper.instance.getMembers()
                commonProcessJson(members)
                val product = RetrofitHelper.instance.getProduct(membersArrayList[0].ename).await()
                withContext(Dispatchers.Main){
                    textView.text = product
                }
            }catch (e:Exception){
                Log.d("ee",e.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun commonProcessJson(data:String){
        try {
            val jsonObject = JSONObject(data)
            val jsonArray = jsonObject.getJSONArray("members")
            for (i in 0 until  jsonArray.length()){
                var obj = jsonArray.getJSONObject(i)
                var cname = obj.getString("cname")
                var ename = obj.getString("ename")
                var type = obj.getInt("type")
                var mgroup = obj.getInt("mgroup")
                var imgurl = obj.getString("imgurl")
                var model = Member()
                model.cname = cname
                model.ename = ename
                model.type = type
                model.mgroup = mgroup
                model.imgurl = imgurl
                membersArrayList.add(model)
            }
        }catch (e:Exception){
            Log.d("ee",e.toString())
        }
    }
}