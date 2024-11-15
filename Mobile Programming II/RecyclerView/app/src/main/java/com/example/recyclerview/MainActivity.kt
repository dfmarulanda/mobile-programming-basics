package com.example.recyclerview

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.ui.theme.RecyclerViewTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.messaging

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var firebaseMessaging: FirebaseMessaging


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        Firebase.messaging.isAutoInitEnabled = true

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("TOKEN", token)
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })


        val items = listOf(
            Item(1, "Título 1", "Descripción 4"),
            Item(2, "Título 2", "Descripción 2"),
            Item(3, "Título 3", "Descripción 3"),
            Item(1, "Título 1", "Descripción 1"),
            Item(2, "Título 2", "Descripción 2"),
            Item(3, "Título 3", "Descripción 3"),      Item(1, "Título 1", "Descripción 1"),
            Item(2, "Título 2", "Descripción 2"),
            Item(3, "Título 3", "Descripción 3"),      Item(1, "Título 1", "Descripción 1"),
            Item(2, "Título 2", "Descripción 2"),
            Item(3, "Título 3", "Descripción 3"),      Item(1, "Título 1", "Descripción 1"),
            Item(2, "Título 2", "Descripción 2"),
            Item(3, "Título 3", "Descripción 4")
        )

        adapter = ItemAdapter(items, { item ->
            Toast.makeText(this, "Clic en: ${item.title}", Toast.LENGTH_LONG).show()
        })
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = FadeInItemAnimator()

    }
}
