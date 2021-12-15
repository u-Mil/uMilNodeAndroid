package com.example.node

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.json.JSONObject

import io.socket.client.Socket
import io.socket.client.IO
import io.socket.emitter.Emitter
import java.net.URISyntaxException


class MainActivity : AppCompatActivity() {
    lateinit var sendbtn : Button
    lateinit var senddataedit : EditText
    lateinit var recvtextview : TextView

    lateinit var mSocket : Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        socketIO()
    }

    private fun initView(){
        sendbtn = findViewById(R.id.sendbutton)
        sendbtn.setOnClickListener(btnClickListener)

        senddataedit = findViewById(R.id.textdata)
        recvtextview = findViewById(R.id.recvdata)
    }

    // v-> onClick(View v)
    private val btnClickListener = View.OnClickListener { v ->
        if(v.id == R.id.sendbutton){
            var datatext = senddataedit.text
            println(datatext)
            var jsonObj = JSONObject()
            jsonObj.put("data", datatext)
            mSocket.emit("hello", jsonObj)
        }
    }

    private fun socketIO(){
        //192.168.0.6
        try{
            mSocket = IO.socket("http://192.168.0.10:5050")
        } catch (e: URISyntaxException){
            Log.e("MainActivity", e.reason)
        }
        //server - io.on('connection', () ->{})
        //client - mSocket.emit('connection', socket)
        try {
            mSocket.connect()
        } catch (e:Exception){
            e.printStackTrace()
        }
        // server -> client
        // connect
        mSocket.on(Socket.EVENT_CONNECT, onConnect)

    }

    private val onConnect: Emitter.Listener = Emitter.Listener {
        println("4")
    }
}