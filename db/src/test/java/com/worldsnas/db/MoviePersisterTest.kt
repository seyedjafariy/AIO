package com.worldsnas.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class MoviePersisterTest {

    lateinit var driver: SqlDriver
    lateinit var queries : MovieQueries

    @Before
    fun init() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Main.Schema.create(driver)
        queries = Main(driver).movieQueries
    }

    @After
    fun tearDown(){
        driver.close()
    }

    @Test
    fun `puts data to db`(){

    }

}