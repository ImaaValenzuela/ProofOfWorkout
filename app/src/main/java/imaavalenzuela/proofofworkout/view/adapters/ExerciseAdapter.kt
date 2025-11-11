package imaavalenzuela.proofofworkout.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import imaavalenzuela.proofofworkout.R
import imaavalenzuela.proofofworkout.model.Exercise

class ExerciseAdapter(
    private val exercises: MutableList<Exercise>,
    private val onEdit: (Exercise) -> Unit,
    private val onDelete: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvExerciseName)
        val tvWeight: TextView = itemView.findViewById(R.id.tvWeight)
        val tvDetails: TextView = itemView.findViewById(R.id.tvDetails)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEditExercise)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteExercise)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]

        // Mostrar la información
        holder.tvName.text = exercise.name
        holder.tvWeight.text = "${exercise.weight} kg"
        holder.tvDetails.text = "Series: ${exercise.sets}  •  Reps: ${exercise.reps}"

        // Botón editar
        holder.btnEdit.setOnClickListener { onEdit(exercise) }

        // Botón eliminar
        holder.btnDelete.setOnClickListener { onDelete(exercise) }
    }

    override fun getItemCount(): Int = exercises.size

    fun updateList(newList: List<Exercise>) {
        exercises.clear()
        exercises.addAll(newList)
        notifyDataSetChanged()
    }
}
