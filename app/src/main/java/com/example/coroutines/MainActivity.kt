package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    lateinit var txtLabel: TextView
    private val parentJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtLabel = findViewById(R.id.txt_label)

        // suspend function call from another suspend or coroutines builder
//        runBlocking {
//            printMyTextAfterDelay("Osama Abu Yahya")
//        }

        // Async / Await
//        GlobalScope.launch {
//            val time = measureTimeMillis {
//
//                val dataUser  = async { getUserFromNetwork() }
//                val localUser = async { getUserFromDatabase() }
//
//                if (dataUser.await() == localUser.await()) {
//                    Log.d("Here", "Equals")
//                } else {
//                    Log.d("Here", "Not Equals")
//                }
//            }
//            Log.d("Here", time.toString())
//        }

        // Jop & structured coroutines
//        val coroutinesScope = CoroutineScope(Dispatchers.IO + parentJob)
//        coroutinesScope.launch {
//
//        }
//
//        val job: Job = GlobalScope.launch(parentJob) {
//            val child1 = launch { getUserFromNetwork() }
//            val child2 = launch { getUserFromDatabase() }
//
//            // join make suspend to thread until the child1 and child2 ending
//            child1.join()
//            child2.join()
////            joinAll(child1, child2)
//            // cancel after join ( Ending )
//            child1.cancelAndJoin()
//            launch { delay(2000) }
//        }
//        // Cancel the job and all child of job
//        job.cancel()

        // Channel
        val kotlinChannel = Channel<String>()
        val charList = arrayOf("A", "B", "C", "D")

        runBlocking {
            launch {
                for (char in charList){
                    kotlinChannel.send(char)
                }
            }

            launch {
                for (char in kotlinChannel){
                    Log.d("Here", char)
                    delay(2000)
                }
            }
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

    override fun onStop() {
        super.onStop()
        parentJob.cancel()
    }
}