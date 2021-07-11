package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    lateinit var txtLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtLabel = findViewById(R.id.txt_label)

        // suspend function call from another suspend or coroutines builder
//        runBlocking {
//            printMyTextAfterDelay("Osama Abu Yahya")
//        }

        GlobalScope.launch {
            val time = measureTimeMillis {

                val dataUser  = async { getUserFromNetwork() }
                val localUser = async { getUserFromDatabase() }

                if (dataUser.await() == localUser.await()) {
                    Log.d("Here", "Equals")
                } else {
                    Log.d("Here", "Not Equals")
                }
            }
            Log.d("Here", time.toString())
        }
    }

    suspend fun printMyTextAfterDelay(myText: String) {
        GlobalScope.launch(Dispatchers.Unconfined) {
            delay(2000)
            // to switch background thread
            withContext(Dispatchers.Main) {
                txtLabel.text = myText
            }
        }
    }

    private suspend fun getUserFromNetwork(): String {
        delay(3500)
        return "Osama Abu Yahya"
    }

    private suspend fun getUserFromDatabase(): String {
        delay(2000)
        return "Osama Abu Yahya"
    }
}