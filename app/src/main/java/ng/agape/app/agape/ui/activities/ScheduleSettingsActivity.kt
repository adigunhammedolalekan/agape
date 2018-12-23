package ng.agape.app.agape.ui.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.OnClick
import com.google.gson.Gson
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.layout_activity_schedule_messaging.*
import ng.agape.app.agape.R
import ng.agape.app.agape.models.Schedule
import ng.agape.app.agape.ui.adapters.SetRepeatingScheduleAdapter
import ng.agape.app.agape.ui.adapters.SetScheduleListAdapter
import ng.agape.app.agape.ui.base.BaseActivity
import ng.agape.app.agape.utils.L

class ScheduleSettingsActivity : BaseActivity() {

    private val mSchedules: ArrayList<Schedule> = arrayListOf()
    private val mRepeatingSchedules: ArrayList<Schedule> = arrayListOf()
    private var mSetScheduleListAdapter = SetScheduleListAdapter(mSchedules)
    private var mSetRepeatingScheduleAdapter = SetRepeatingScheduleAdapter(mRepeatingSchedules)
    private var mSelectedDays: ArrayList<String> = arrayListOf()
    private var mCurrentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_schedule_messaging)

        initRecyclerViews()
        btn_add_more_schedule.setOnClickListener {
            val s = Schedule.createEmptySchedule()
            mSchedules.add(s)
            mSetScheduleListAdapter.notifyItemChanged((mSchedules.size - 1))
        }

        layout_repeating_schedule_setup.subscribeForClicks { day, view ->

            var removeIndex = -1;
            if (mSelectedDays.contains(day)) {
                view.setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary))
                mSelectedDays.remove(day)

                mRepeatingSchedules.forEach {
                    if (it.day == day) {
                        removeIndex = mRepeatingSchedules.indexOf(it)
                    }
                }

                if (removeIndex != -1) {
                    mRepeatingSchedules.removeAt(removeIndex)
                }

            }else {

                view.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                mSelectedDays.add(day)
                val repeatingSchedule = Schedule.createEmptySchedule()
                repeatingSchedule.day = day
                mRepeatingSchedules.add(repeatingSchedule)
            }

            mSetRepeatingScheduleAdapter.notifyDataSetChanged()
            if (mRepeatingSchedules.size > 0) {
                rv_repeating_schedule_setup.scrollToPosition((mRepeatingSchedules.size - 1))
            }
        }
    }

    private fun initRecyclerViews() {

        rv_add_schedules.isNestedScrollingEnabled = false
        rv_add_schedules.layoutManager = LinearLayoutManager(this)
        rv_add_schedules.adapter = mSetScheduleListAdapter
        rv_add_schedules.itemAnimator = DefaultItemAnimator()

        rv_repeating_schedule_setup.isNestedScrollingEnabled = false
        rv_repeating_schedule_setup.layoutManager = LinearLayoutManager(this)
        rv_repeating_schedule_setup.adapter = mSetRepeatingScheduleAdapter
        rv_repeating_schedule_setup.itemAnimator = DefaultItemAnimator()

        //add an empty schedule for
        //user to edit
        mSchedules.add(Schedule.createEmptySchedule())
        mSetScheduleListAdapter.notifyDataSetChanged()

        mSetScheduleListAdapter.mListenerFunc = {_, action, position ->

            mCurrentPosition = position
            when(action) {

                SetScheduleListAdapter.ClickAction.ACTION_SET_DATE -> {
                    showDatePicker()
                }

                SetScheduleListAdapter.ClickAction.ACTION_SET_TIME -> {
                    showTimePicker()
                }

                SetScheduleListAdapter.ClickAction.ACTION_SET_MESSAGE_TYPE -> {
                    showTextTypeDialog()
                }

                SetScheduleListAdapter.ClickAction.ACTION_SET_MESSAGE_TEXT -> {
                    showTextInputDialog()
                } else -> {

                }
            }
        }

        mSetRepeatingScheduleAdapter.mClickCallbackFunc = {positon ->

            showTimePickerForRepeatingSchedule(positon)
        }
    }

    private fun showTimePickerForRepeatingSchedule(position: Int) {

        TimePickerDialog.newInstance({view, hourOfDay, minute, second ->

            var min = minute.toString()
            if (minute.toString().trim().length <= 1) {
                //1 digit
                min = "0" + minute
            }
            val timeString = hourOfDay.toString() + ":" + min
            mRepeatingSchedules[position].time = timeString
            mSetRepeatingScheduleAdapter.notifyItemChanged(position)
        }, true).show(fragmentManager, "TimePicker2")
    }

    private fun showDatePicker() {


        DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->

            val dateString = year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth
            mSchedules[mCurrentPosition].date = dateString
            mSetScheduleListAdapter.notifyItemChanged(mCurrentPosition)

            if (mSchedules.size > 0) {
                rv_add_schedules.scrollToPosition((mSchedules.size - 1))
            }
        }.show(fragmentManager, "DatePicker")
    }


    private fun showTimePicker() {

        TimePickerDialog.newInstance({ view, hourOfDay, minute, second ->

            val timeString = hourOfDay.toString() + ":" + minute
            mSchedules[mCurrentPosition].time = timeString
            mSetScheduleListAdapter.notifyItemChanged(mCurrentPosition)

            if (mSchedules.size > 0) {
                rv_add_schedules.scrollToPosition((mSchedules.size - 1))
            }
        }, true).show(fragmentManager, "TimePicker")
    }

    private fun showTextInputDialog() {

        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.layout_text_input_dialog,
                null, false)
        builder.setView(view)
        builder.setTitle("Enter Text")

        val counterTextView = view.findViewById<TextView>(R.id.tv_text_counter_text_input_dialog)
        val inputEditText = view.findViewById<EditText>(R.id.edt_text_input_dialog)

        //show previously written text
        if (mSchedules[mCurrentPosition].text.isNotEmpty() && mSchedules[mCurrentPosition].text != "Set message text") {
            inputEditText.setText(mSchedules[mCurrentPosition].text)
        }

        inputEditText.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val count = inputEditText.text.toString().trim().length

                counterTextView.text = count.toString() + "/240"
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })
        builder.setPositiveButton("SUBMIT") { dialog, which ->

            val submittedText = inputEditText.text.toString().trim()
            mSchedules[mCurrentPosition].text = submittedText
            mSetScheduleListAdapter.notifyItemChanged(mCurrentPosition)

            if (mSchedules.size > 0) {
                rv_add_schedules.scrollToPosition((mSchedules.size - 1))
            }
        }
        builder.setNegativeButton("CANCEL") {dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun showTextTypeDialog() {

        val types = arrayOf("Romantic", "Motivational", "Healing", "Greeting")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Message Type")
        builder.setItems(types) { dialog, index ->

            mSchedules[mCurrentPosition].messageType = types[index]
            mSetScheduleListAdapter.notifyItemChanged(mCurrentPosition)

            if (mSchedules.size > 0) {
                rv_add_schedules.scrollToPosition((mSchedules.size - 1))
            }
        }
        builder.create().show()
    }

    @OnClick(R.id.btn_submit_data) public fun onSubmitClick() {

        L.fine("Heyy!")
        val senderName = edt_name_schedule_messaging.text.trim()
        if (senderName.isEmpty()) {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_LONG).show()
            edt_name_schedule_messaging.requestFocus()
            return
        }

        val data = mSchedules.filter { !it.isEmptySchedule() }
        mRepeatingSchedules.filter { !it.isEmptySchedule() }.map {
            data + it
        }

        L.fine("Size => " + data.size)
        val json = Gson().toJson(data)
        L.fine("Data => $json")
    }
}