package imaavalenzuela.proofofworkout.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import imaavalenzuela.proofofworkout.R
import imaavalenzuela.proofofworkout.model.Achievement

class AchievementsAdapter(
    private var achievements: List<Achievement>
) : RecyclerView.Adapter<AchievementsAdapter.AchievementViewHolder>() {

    inner class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView as CardView
        val tvBadge: TextView = itemView.findViewById(R.id.tvBadge)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_achievement, parent, false)
        return AchievementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = achievements[position]
        val context = holder.itemView.context

        holder.tvTitle.text = achievement.title
        holder.tvDescription.text = achievement.description
        holder.tvBadge.text = if (achievement.unlocked) "🏆" else "🔒"


        if (achievement.unlocked) {
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.tvDescription.setTextColor(ContextCompat.getColor(context, R.color.light_gray))
            holder.tvBadge.alpha = 1f
            holder.card.alpha = 1f
            holder.card.cardElevation = 4f
        } else {
            val gray = ContextCompat.getColor(context, R.color.light_gray)
            holder.tvTitle.setTextColor(gray)
            holder.tvDescription.setTextColor(gray)
            holder.tvBadge.alpha = 0.4f
            holder.card.alpha = 0.7f
            holder.card.cardElevation = 0f
        }
    }

    override fun getItemCount() = achievements.size

    fun updateData(newList: List<Achievement>) {
        achievements = newList
        notifyDataSetChanged()
    }
}
