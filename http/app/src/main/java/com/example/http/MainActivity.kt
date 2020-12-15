package com.example.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var thread = NetworkThread()
            thread.start()
        }

    }


    inner class NetworkThread : Thread(){
        override fun run() {
            var site = "https://zizqnx33mi.execute-api.us-east-2.amazonaws.com/dev/categories"
            var url = URL(site)
            var conn = url.openConnection()

            var input = conn.getInputStream()
            var isr = InputStreamReader(input, "UTF-8")
            var br = BufferedReader(isr)

            var str : String? = null
            var buf = StringBuffer()

            do{
                str = br.readLine()
                if(str !=null){
                    buf.append(str)
                }

            }
                while(str !=null)
            var data = buf.toString()

            runOnUiThread {
                textView.text =data
            }
            }
        }
    }






