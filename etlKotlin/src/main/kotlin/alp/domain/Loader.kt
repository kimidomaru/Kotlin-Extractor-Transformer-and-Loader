package alp.domain
import etl.Employee
import etl.WorkDay
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class Loader {
    private val mapa: MutableMap<String, Int> = mutableMapOf()

    object TabelaEmpregados : Table() {
        val id = integer("id").primaryKey() // Column<Integer>
        val name = varchar("name", length = 50) // Column<String>
    }

    object TabelaWorkDay : Table(){
        val id = integer("id").autoIncrement().primaryKey()
        val tipoDate = varchar("data", length = 30)
        val empregado = varchar("empregado", length = 50).references(TabelaEmpregados.name)
        val pontos = varchar("pontos", length = 200)
    }

    fun load(workDays: List<WorkDay>){

        val database = Database.connect("jdbc:mysql://localhost:3306/etldb?useTimezone=true&serverTimezone=UTC",
                                    "com.mysql.cj.jdbc.Driver", "root", "")

        transaction (database) {
            SchemaUtils.create(TabelaEmpregados)
            SchemaUtils.create(TabelaWorkDay)
            var i = 0
            workDays.forEach{workDay ->
                val novoEmp: Employee = workDay.employee

                if(!mapa.containsKey(novoEmp.name)){
                    mapa[novoEmp.name] = i
                    TabelaEmpregados.insert {
                        it[id] = i
                        it[name] = novoEmp.name
                    }
                    i++
                }
                workDay.employee = novoEmp

                TabelaWorkDay.insert {
                    it[tipoDate] = workDay.date.toString()
                    it[empregado] = workDay.employee.name
                    it[pontos] = workDay.punches.toString()
                }
            }
            for(empregado in TabelaEmpregados.selectAll()){
                println("${empregado[TabelaEmpregados.id]} - ${empregado[TabelaEmpregados.name]}")
            }
            println("-------------------------------")
            for(diaTrabalho in TabelaWorkDay.selectAll()){
                println("${diaTrabalho[TabelaWorkDay.id]} - ${diaTrabalho[TabelaWorkDay.tipoDate]} - " +
                        "${diaTrabalho[TabelaWorkDay.empregado]} - ${diaTrabalho[TabelaWorkDay.pontos]}")
            }
        }

    }

}
