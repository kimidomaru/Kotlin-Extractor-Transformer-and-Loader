package alp.domain
import etl.WorkDay
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object TabelaTeste : Table() {
    val identificador = integer("id").primaryKey() // Column<String>
    val nome = varchar("name", length = 50) // Column<String>
}
fun main(){

    println("Antes de ler o DB")
    //val database = Database.connect("jdbc:postgresql://localhost:58358/testes", "org.postgresql.Driver")
    //Database.connect("jdbc:postgresql://localhost:58358/testes", "org.postgresql.Driver"
    //    , "postgres", "admin")

    //Database.connect("jdbc:mysql://localhost:3306/etldb", driver = "com.mysql.cj.jdbc.Driver")
    Database.connect("jdbc:mysql://localhost:3306/etldb?useTimezone=true&serverTimezone=UTC", "com.mysql.cj.jdbc.Driver", "root", "")
    println("Dps de ler o DB")

    transaction{
        println("Transaction")
        SchemaUtils.create(TabelaTeste)
        println("Criou tabela")
        TabelaTeste.insert {
            it[identificador] = 1
            it[nome] = "Mamado"
        }
        println("INSERIU!!!!!")
    }
    print("CABOU!!!!!!!!!!!")
}