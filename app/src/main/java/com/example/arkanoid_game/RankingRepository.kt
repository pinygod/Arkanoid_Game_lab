package com.example.arkanoid_game

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.arkanoid_game.data.RankingItem
import com.example.arkanoid_game.data.SessionResult
import com.example.arkanoid_game.ui.menu.MenuModel
import com.google.firebase.firestore.FirebaseFirestore
import io.nlopez.smartlocation.SmartLocation

class RankingRepository(private val activity: Activity) {

    private val db = FirebaseFirestore.getInstance()
    private var ratingList: ArrayList<RankingItem> = ArrayList()
    private val result = MutableLiveData<ArrayList<RankingItem>>()

    init {
        result.value = ArrayList()
    }

    fun getRatingList(): MutableLiveData<ArrayList<RankingItem>> {
        db.collection(Constants.LEADERBOARD_KEY)
            .get()
            .addOnSuccessListener { querySnapshot ->

                ratingList =
                    querySnapshot.toObjects(RankingItem::class.java) as ArrayList<RankingItem>
                ratingList.sortByDescending { it.result?.score }
                result.value = ratingList
            }
        return result
    }

    fun pushScore(score: Int) {
        SmartLocation.with(activity).location().start { location ->
            val taskData = HashMap<String, Any>()
            val username = MenuModel().getUsername(activity)!!
            taskData["name"] = username
            taskData["result"] = SessionResult(
                score,
                location?.latitude,
                location?.longitude
            )
            db.collection(Constants.LEADERBOARD_KEY).add(taskData)
        }
    }
}