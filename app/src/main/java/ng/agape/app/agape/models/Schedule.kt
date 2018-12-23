package ng.agape.app.agape.models

class Schedule {

    public var date: String = ""
    public var time: String = ""
    public var text: String = ""
    public var messageType: String = ""
    public var day: String = ""

    companion object {

        fun createEmptySchedule() : Schedule {

            val s = Schedule()
            s.date = "Set date"
            s.text = "Set message text"
            s.messageType = "Set message type"
            s.time = "Set time"

            return s
        }
    }

    public fun isEmptySchedule() = date == "Set date" || text == "Set message text"
            || messageType == "Set message type" || time == "Set time"

    public fun isRepeatingSchedule() = day.isNotEmpty()
}