package vo

data class TimeEntry(
        val employee: String,
        val date: String,
        val hours: String
) {
    override fun toString(): String =  "TimeEntryVO [Empregado = " + employee + ", Data = " + date + ", Horas = " + hours + "]"
}