package imaavalenzuela.proofofworkout.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import imaavalenzuela.proofofworkout.data.AchievementsData
import imaavalenzuela.proofofworkout.databinding.ActivityAchievementsBinding
import imaavalenzuela.proofofworkout.view.adapters.AchievementsAdapter

class AchievementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAchievementsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvAchievements.layoutManager = LinearLayoutManager(this)
        binding.rvAchievements.adapter = AchievementsAdapter(AchievementsData.achievements)
    }
}
