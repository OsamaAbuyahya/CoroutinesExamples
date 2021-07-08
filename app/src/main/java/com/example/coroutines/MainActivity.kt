package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var txtLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtLabel = findViewById(R.id.txt_label)

        // suspend function call from another suspend or coroutines builder
        runBlocking {
            printMyTextAfterDelay("Osama Abu Yahya")
        }

    }

    suspend fun printMyTextAfterDelay(myText: String){
        GlobalScope.launch(Dispatchers.Unconfined) {
            delay(2000)
            // to switch background thread
            withContext(Dispatchers.Main){
                txtLabel.text = myText
            }
        }
    }
}