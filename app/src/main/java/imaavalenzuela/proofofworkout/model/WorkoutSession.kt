package imaavalenzuela.proofofworkout.model

data class WorkoutSession(
    val id : Long,
    val name : String,
    val date : String,
    var exercises : MutableList<Exercise>
)
