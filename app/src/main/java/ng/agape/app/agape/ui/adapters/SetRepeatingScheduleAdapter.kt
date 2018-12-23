package ng.agape.app.agape.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ng.agape.app.agape.R
import ng.agape.app.agape.models.Schedule

class SetRepeatingScheduleAdapter(val data: List<Schedule>) :
        RecyclerView.Adapter<SetRepeatingScheduleAdapter.RepeatingScheduleViewHolder>() {

    private val mRepeatingSchedules: List<Schedule> = data
    public var mClickCallbackFunc: (Int) -> Unit = {_ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepeatingScheduleViewHolder {
        return RepeatingScheduleViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_set_repeating_schedule, parent, false))
    }

    override fun getItemCount() = mRepeatingSchedules.size

    override fun onBindViewHolder(holder: RepeatingScheduleViewHolder, position: Int) {

        val schedule = mRepeatingSchedules[position]
        holder.titleTextView.text = "Every " + schedule.day
        if (schedule.time != "Set time") {
            schedule.time = "At " + schedule.time
        }
        holder.timeTextView.text = schedule.time

        holder.buttonSetTime.setOnClickListener {
            mClickCallbackFunc.invoke(position)
        }
    }

    class RepeatingScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titleTextView = view.findViewById<TextView>(R.id.tv_date_set_repeating_schedule)
        val timeTextView = view.findViewById<TextView>(R.id.tv_set_time_set_repeating_schedule)
        val buttonSetTime = view.findViewById<Button>(R.id.btn_dots_vertical)
    }
}