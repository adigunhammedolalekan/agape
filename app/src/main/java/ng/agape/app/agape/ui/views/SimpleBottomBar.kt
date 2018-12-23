package ng.agape.app.agape.ui.views

import android.content.Context
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_bottom_bar.view.*
import net.steamcrafted.materialiconlib.MaterialIconView
import ng.agape.app.agape.R

class SimpleBottomBar @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null,
                                                style: Int = 0) : LinearLayout(context, attributeSet, style) {

    public var mCallback: (Int) -> Unit = {_ -> }

    public object Action {

        const val SCHEDULE_ = 1
        const val HISTORY_ = 2
        const val ACCOUNT_ = 3
    }

    init {

        View.inflate(context, R.layout.layout_bottom_bar, this)
        val scheduleButton = findViewById<MaterialIconView>(R.id.iv_schedule)
        val historyButton = findViewById<MaterialIconView>(R.id.iv_history)
        val accountButton = findViewById<MaterialIconView>(R.id.iv_account_)

        val scheduleTextView = findViewById<TextView>(R.id.tv_schedule_)
        val historyTextView = findViewById<TextView>(R.id.tv_history)
        val accountTextView = findViewById<TextView>(R.id.tv_account_)
        selectTab(scheduleButton, scheduleTextView, historyButton, accountButton, historyTextView, accountTextView)

        btn_schedule.setOnClickListener {

            selectTab(scheduleButton, scheduleTextView, historyButton, accountButton, historyTextView, accountTextView)
            mCallback.invoke(Action.SCHEDULE_)
        }

        btn_history_.setOnClickListener {
            selectTab(historyButton, historyTextView, scheduleButton, accountButton, scheduleTextView, accountTextView)
            mCallback.invoke(Action.HISTORY_)
        }

        btn_account_.setOnClickListener {
            selectTab(accountButton, accountTextView, historyButton, scheduleButton, historyTextView, scheduleTextView)
            mCallback.invoke(Action.ACCOUNT_)
        }
    }

    private fun selectTab(selectedIcon: MaterialIconView, selectedTextView: TextView, vararg others: View) {

        selectedIcon.setColor(ContextCompat.getColor(context, R.color.colorAccent))
        selectedTextView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))

        others.map {

            when(it) {
                is MaterialIconView -> {

                    it.setColor(ContextCompat.getColor(context, R.color.dark))
                }
                is TextView -> {

                    it.setTextColor(ContextCompat.getColor(context, R.color.dark))
                } else -> {}
            }
        }
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}