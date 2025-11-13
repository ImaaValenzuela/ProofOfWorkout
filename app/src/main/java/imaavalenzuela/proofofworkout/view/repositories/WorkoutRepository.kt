package imaavalenzuela.proofofworkout.view.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import imaavalenzuela.proofofworkout.model.WorkoutSession

class WorkoutRepository(context: Context) {

    private val prefs = context.getSharedPreferences("workouts_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val type = object : TypeToken<MutableList<WorkoutSession>>() {}.type

    fun getAllWorkouts(): MutableList<WorkoutSession> {
        val json = prefs.getString("workout_list", null)
        return if (json != null) gson.fromJson(json, type) else mutableListOf()
    }

    fun getWorkoutById(id: Long): WorkoutSession? {
        return getAllWorkouts().firstOrNull { it.id == id }
    }

    fun saveWorkouts(workouts: MutableList<WorkoutSession>) {
        val json = gson.toJson(workouts)
        prefs.edit().putString("workout_list", json).apply()
    }

    fun addWorkout(session: WorkoutSession) {
        val workouts = getAllWorkouts()
        workouts.add(session)
        saveWorkouts(workouts)
    }

    fun updateWorkout(updated: WorkoutSession) {
        val workouts = getAllWorkouts()
        val index = workouts.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            workouts[index] = updated
            saveWorkouts(workouts)
        }
    }

    fun deleteWorkout(id: Long) {
        val workouts = getAllWorkouts().filter { it.id != id }.toMutableList()
        saveWorkouts(workouts)
    }
}
