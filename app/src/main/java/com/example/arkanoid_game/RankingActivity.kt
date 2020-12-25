package com.example.arkanoid_game

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_ranking.*


class RankingActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    var ratingList: ArrayList<RankingItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val taskData = HashMap<String, Any>()
        taskData["name"] = "kekovnik"
        taskData["score"] = (0..1000).random()

        db.collection("leaderboard").add(taskData)

        db.collection("leaderboard")
            .get()
            .addOnSuccessListener { querySnapshot ->
                ratingList = querySnapshot.toObjects(RankingItem::class.java) as ArrayList<RankingItem>
                ratingList.sortByDescending {it.score }
                val adapter = RankingRecyclerAdapter(ratingList)
                rankingRecycler.setHasFixedSize(true)
                rankingRecycler.layoutManager = LinearLayoutManager(this)
                rankingRecycler.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_LONG).show()
            }


    }

}