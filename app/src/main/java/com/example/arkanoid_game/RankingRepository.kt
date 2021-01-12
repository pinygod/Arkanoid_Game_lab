package com.example.arkanoid_game

import android.app.Activity
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

class RankingRepository {

    private val db = FirebaseFirestore.getInstance()
    private var ratingList: ArrayList<RankingItem> = ArrayList()

    fun getRatingList(callback: (adapter: RankingRecyclerAdapter) -> Unit) {
        db.collection("leaderboard")
            .get()
            .addOnSuccessListener { querySnapshot ->
                ratingList =
                    querySnapshot.toObjects(RankingItem::class.java) as ArrayList<RankingItem>
                ratingList.sortByDescending { it.score }
                callback.invoke(RankingRecyclerAdapter(ratingList))
            }
            .addOnFailureListener {
                ratingList = ArrayList()
                callback.invoke(RankingRecyclerAdapter(ratingList))
            }
    }

    fun pushScore(activity: Activity, score: Int) {
        val taskData = HashMap<String, Any>()
        taskData["name"] = activity.getPreferences(Context.MODE_PRIVATE)
            .getString(activity.getString(R.string.username_key), null)!!
        taskData["score"] = score

        db.collection("leaderboard").add(taskData)
    }
}