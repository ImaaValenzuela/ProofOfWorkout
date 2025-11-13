package imaavalenzuela.proofofworkout.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import imaavalenzuela.proofofworkout.R
import imaavalenzuela.proofofworkout.model.WorkoutSession

class WorkoutHistoryAdapter(
    private val workouts: MutableList<WorkoutSession>,
    private val onWorkoutClick: (WorkoutSession) -> Unit
) : RecyclerView.Adapter<WorkoutHistoryAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateText: TextView = itemView.findViewById(R.id.tvDate)
        val detailsText: TextView = itemView.findViewById(R.id.tvDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout_session, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.dateText.text = workout.date
        holder.detailsText.text = "Exercises: ${workout.exercises.size}"

        holder.itemView.setOnClickListener {
            onWorkoutClick(workout)
        }
    }

    override fun getItemCount() = workouts.size

    fun updateData(newWorkouts: List<WorkoutSession>) {
        workouts.clear()
        workouts.addAll(newWorkouts)
        notifyDataSetChanged()
    }
}
