package com.preibisch.pinna2d.util

import com.preibisch.pinna2d.controllers.AnnotationController
import com.preibisch.pinna2d.model.AnnotationEntryTbl
import com.preibisch.pinna2d.model.ImageEntryTbl
import com.preibisch.pinna2d.tools.Log
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.io.File
import java.sql.Connection

private var LOG_TO_CONSOLE: Boolean = false

fun newTransaction(): Transaction = TransactionManager.currentOrNew(Connection.TRANSACTION_SERIALIZABLE).apply {
    if (LOG_TO_CONSOLE) addLogger(StdOutSqlLogger)
}

fun enableConsoleLogger() {
    LOG_TO_CONSOLE = true
}

fun createTables() {
    with(newTransaction()) {
        SchemaUtils.create(AnnotationEntryTbl)
        SchemaUtils.create(ImageEntryTbl)
    }
}

fun <T> execute(command: () -> T): T {
    with(newTransaction()) {
        return command().apply {
            commit()
            close()
        }
    }
}

fun initDB(folder: String) {
    val dbPath = String.format("jdbc:sqlite:%s", File(folder,"pinny-annotations.db").absolutePath)
    Log.info("DB Path : $dbPath")
    Database.connect(dbPath, "org.sqlite.JDBC")
    createTables()

    // controller(es)
    AnnotationController()
}