package ng.agape.app.agape.ui.views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import ng.agape.app.agape.R

class ScheduleSettingView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null,
                          style: Int = 0) : LinearLayout(context, attributeSet, style) {

    private var mCallBack: (day: String, view: TextView) -> Unit? = {_, _ -> }

    init {

        View.inflate(context, R.layout.layout_repeating_schedule_picker, this)

        val mondayButton = findViewById<FrameLayout>(R.id.btn_monday)
        mondayButton.setOnClickListener {
            mCallBack("Monday", findViewById(R.id.tv_monday))
        }

        val tuesdayButton = findViewById<FrameLayout>(R.id.btn_tuesday)
        tuesdayButton.setOnClickListener {
            mCallBack("Tuesday", findViewById(R.id.tv_tuesday))
        }

        val wednesdayButton = findViewById<FrameLayout>(R.id.btn_wednesday)
        wednesdayButton.setOnClickListener {
            mCallBack("Wednesday", findViewById(R.id.tv_wednesday))
        }

        val thursdayButton = findViewById<FrameLayout>(R.id.btn_thursday)
        thursdayButton.setOnClickListener {
            mCallBack("Thursday", findViewById(R.id.tv_thursday))
        }

        val friday = findViewById<FrameLayout>(R.id.btn_friday)
        friday.setOnClickListener {
            mCallBack("Friday", findViewById(R.id.tv_friday))
        }

        val saturdayButton = findViewById<FrameLayout>(R.id.btn_saturday)
        saturdayButton.setOnClickListener {
            mCallBack("Saturday", findViewById(R.id.tv_saturday))
        }

        val sundayButton = findViewById<FrameLayout>(R.id.btn_sunday)
        sundayButton.setOnClickListener {
            mCallBack("Sunday", findViewById(R.id.tv_sunday))
        }
    }

    public fun subscribeForClicks(f : (dayClicked: String, view: TextView) -> Unit) {
        mCallBack = f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}