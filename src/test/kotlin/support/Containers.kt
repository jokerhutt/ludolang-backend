package support;

import org.testcontainers.containers.MySQLContainer;

object Containers {

val MYSQL: MySQLContainer<*> =
MySQLContainer("mysql:8.0.39")

init {
    MYSQL.start()
}
}