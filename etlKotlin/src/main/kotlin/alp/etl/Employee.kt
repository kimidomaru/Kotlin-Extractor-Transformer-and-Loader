package etl

data class Employee(
        val name: String
) {
    override fun toString(): String =  "Employee [Nome = " + name + "]"
}