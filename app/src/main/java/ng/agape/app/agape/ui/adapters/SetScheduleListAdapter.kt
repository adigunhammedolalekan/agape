package ng.agape.app.agape.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import ng.agape.app.agape.R
import ng.agape.app.agape.models.Schedule

class SetScheduleListAdapter(val data: List<Schedule>) : RecyclerView.Adapter<SetScheduleListAdapter.SetScheduleViewHolder>() {

    private var mSchedules: List<Schedule> = data
    public var mListenerFunc: (Schedule, Int, Int) -> Unit? = {_, _, _ ->}

    public object ClickAction {

        const val ACTION_SET_DATE = 1
        const val ACTION_SET_TIME = 2
        const val ACTION_SET_MESSAGE_TEXT = 3
        const val ACTION_SET_MESSAGE_TYPE = 4
    }
    override fun getItemCount(): Int {
        return mSchedules.size
    }

    override fun onBindViewHolder(holder: SetScheduleViewHolder, position: Int) {

        val schedule = mSchedules[position]
        holder.dateTextView.text = schedule.date
        holder.timeTextView.text = schedule.time
        holder.messageTypeTextView.text = schedule.messageType
        holder.messageTextView.text = schedule.text

        bindListeners(holder, position)

    }

    private fun bindListeners(holder: SetScheduleViewHolder, position: Int) {

        val clickedSchedule = mSchedules[position]
        holder.btnSetDate.setOnClickListener {

            mListenerFunc.invoke(clickedSchedule, ClickAction.ACTION_SET_DATE, position)
        }

        holder.btnSetTime.setOnClickListener {
            mListenerFunc.invoke(clickedSchedule, ClickAction.ACTION_SET_TIME, position)
        }

        holder.btnSetMessageText.setOnClickListener {
            mListenerFunc.invoke(clickedSchedule, ClickAction.ACTION_SET_MESSAGE_TEXT, position)
        }

        holder.btnSetMessageType.setOnClickListener {
            mListenerFunc.invoke(clickedSchedule, ClickAction.ACTION_SET_MESSAGE_TYPE, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetScheduleViewHolder {
        return SetScheduleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_set_schedule,
                parent, false))
    }

    class SetScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val dateTextView = view.findViewById<TextView>(R.id.tv_set_date)
        val timeTextView = view.findViewById<TextView>(R.id.tv_set_time)
        val messageTextView = view.findViewById<TextView>(R.id.tv_set_message_text)
        val messageTypeTextView = view.findViewById<TextView>(R.id.tv_set_message_type)

        val btnSetDate = view.findViewById<LinearLayout>(R.id.btn_set_date_layout_set_schedule)
        val btnSetTime = view.findViewById<LinearLayout>(R.id.btn_set_time_layout_set_schedule)
        val btnSetMessageText = view.findViewById<LinearLayout>(R.id.btn_set_text_layout_set_schedule)
        val btnSetMessageType = view.findViewById<LinearLayout>(R.id.btn_set_message_type_layout_set_schedule)
    }
}