package imaavalenzuela.proofofworkout.view.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import imaavalenzuela.proofofworkout.data.AchievementsData
import imaavalenzuela.proofofworkout.model.Achievement
import java.text.SimpleDateFormat
import java.util.*

class AchievementRepository(private val context: Context) {
    private val prefs = context.getSharedPreferences("achievements_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun loadAchievements(): MutableList<Achievement> {
        val json = prefs.getString("achievements_list", null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Achievement>>() {}.type
            gson.fromJson(json, type)
        } else {
            AchievementsData.achievements.toMutableList()
        }
    }

    fun saveAchievements(list: List<Achievement>) {
        val json = gson.toJson(list)
        prefs.edit().putString("achievements_list", json).apply()
    }

    fun unlockAchievement(title: String) {
        val all = loadAchievements()
        val achievement = all.firstOrNull { it.title == title }
        achievement?.let {
            if (!it.unlocked) {
                it.unlocked = true
                it.unlockedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                saveAchievements(all)
            }
        }
    }
}
