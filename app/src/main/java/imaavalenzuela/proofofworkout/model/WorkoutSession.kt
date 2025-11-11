package imaavalenzuela.proofofworkout.model

data class WorkoutSession(
    val id : Long,
    val date : String,
    val exercises : MutableList<Exercise>
)
