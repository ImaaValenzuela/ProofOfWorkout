package imaavalenzuela.proofofworkout.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import imaavalenzuela.proofofworkout.model.Exercise
import imaavalenzuela.proofofworkout.model.WorkoutSession
import imaavalenzuela.proofofworkout.model.Achievement
import imaavalenzuela.proofofworkout.view.repositories.WorkoutRepository
import imaavalenzuela.proofofworkout.view.repositories.AchievementRepository
import java.text.SimpleDateFormat
import java.util.*

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    // --- Repositorios ---
    private val workoutRepository = WorkoutRepository(application)
    private val achievementRepository = AchievementRepository(application)

    // --- LiveData observables ---
    private val _workouts = MutableLiveData<MutableList<WorkoutSession>>()
    val workouts: LiveData<MutableList<WorkoutSession>> get() = _workouts

    private val _currentExercises = MutableLiveData<MutableList<Exercise>>(mutableListOf())
    val currentExercises: LiveData<MutableList<Exercise>> get() = _currentExercises

    private val _selectedSession = MutableLiveData<WorkoutSession?>()
    val selectedSession: LiveData<WorkoutSession?> get() = _selectedSession

    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> get() = _achievements

    // --- Init ---
    init {
        loadWorkouts()
        loadAchievements()
    }

    // --- Workouts ---
    fun loadWorkouts() {
        _workouts.value = workoutRepository.getAllWorkouts()
    }

    fun selectWorkout(id: Long) {
        _selectedSession.value = workoutRepository.getWorkoutById(id)
        _currentExercises.value =
            _selectedSession.value?.exercises?.toMutableList() ?: mutableListOf()
    }

    fun addExercise(exercise: Exercise) {
        val updatedList = _currentExercises.value ?: mutableListOf()
        updatedList.add(exercise)
        _currentExercises.value = updatedList
    }

    fun updateExercise(index: Int, updated: Exercise) {
        val updatedList = _currentExercises.value ?: return
        updatedList[index] = updated
        _currentExercises.value = updatedList
    }

    fun deleteExercise(exercise: Exercise) {
        val updatedList = _currentExercises.value ?: return
        updatedList.remove(exercise)
        _currentExercises.value = updatedList
    }

    fun saveWorkout(name: String, existingId: Long? = null) {
        val exercises = _currentExercises.value ?: mutableListOf()
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

        if (existingId != null) {
            val updated = WorkoutSession(existingId, name, date, exercises)
            workoutRepository.updateWorkout(updated)
        } else {
            val newSession = WorkoutSession(System.currentTimeMillis(), name, date, exercises)
            workoutRepository.addWorkout(newSession)
        }

        loadWorkouts()
        _currentExercises.value = mutableListOf()

        // 🔥 Verificar logros al guardar
        checkAchievements()
    }

    fun deleteWorkout(id: Long) {
        workoutRepository.deleteWorkout(id)
        loadWorkouts()
    }

    fun clearExercises() {
        _currentExercises.value = mutableListOf()
    }

    // --- Achievements ---
    private fun loadAchievements() {
        _achievements.value = achievementRepository.loadAchievements()
    }

    private fun checkAchievements() {
        val workoutsList = _workouts.value ?: return
        val total = workoutsList.size

        // --- Ejemplos de condiciones básicas ---
        if (total >= 1) achievementRepository.unlockAchievement("Primer paso")
        if (total >= 3) achievementRepository.unlockAchievement("Rutina establecida")
        if (total >= 7) achievementRepository.unlockAchievement("Hábito formado")
        if (total >= 10) achievementRepository.unlockAchievement("Imparable")
        if (total >= 100) achievementRepository.unlockAchievement("Constancia legendaria")

        // 🔹 Ejemplo: desbloquear si hay una rutina full body
        workoutsList.lastOrNull()?.let { session ->
            if (session.exercises.size >= 5)
                achievementRepository.unlockAchievement("Full body")
        }

        // 🔹 Ejemplo: desbloquear si se vuelve a entrenar después de una pausa larga
        if (workoutsList.size >= 2) {
            val lastTwo = workoutsList.takeLast(2)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val diff = kotlin.runCatching {
                val d1 = dateFormat.parse(lastTwo[0].date)
                val d2 = dateFormat.parse(lastTwo[1].date)
                (d2.time - d1.time) / (1000 * 60 * 60 * 24)
            }.getOrDefault(0L)
            if (diff >= 7) achievementRepository.unlockAchievement("Renacimiento")
        }

        // 🔹 Refrescar lista de logros actualizada
        _achievements.value = achievementRepository.loadAchievements()
    }
}
