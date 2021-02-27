package com.example.workreview_kotlin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestService : Service() {
    companion object{
        const val ACTION_DONE = "action_done"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("service","onstartcommand")

        val ename = intent?.getStringExtra("ename")
        CoroutineScope(Dispatchers.IO).launch {
            val product = ename?.let { RetrofitHelper.instance.getProduct(it).await() }
            delay(5000)
            val intent = Intent(ACTION_DONE)
            intent.putExtra("data",product)
            sendBroadcast(intent)
        }

        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("service","oncreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("service","ondestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}