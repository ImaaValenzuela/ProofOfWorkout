package imaavalenzuela.proofofworkout.model

data class Achievement(
    val title : String,
    val description : String,
    val icon: String? = "🏆",
    var unlocked : Boolean = false,
    var unlockedDate: String? = null
)
