package etl

import java.time.LocalDate
import java.time.LocalTime
import java.util.List

data class WorkDay(
    var employee: Employee,
    val date: LocalDate,
    var punches: ArrayList<LocalTime>
) {
    override fun toString(): String =  "TimeEntryVO [Empregado = " + employee + ", Data = " + date + ", Pontos = " + punches + "]"
}