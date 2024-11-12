package com.example.databaseexample.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "employee.db"
        private const val DATABASE_VERSION = 1
        private const val CREATE_TABLE_EMPLOYEE = """
            CREATE TABLE employee (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                employee_name TEXT,
                employee_code TEXT,
                employee_image BLOB
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_EMPLOYEE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // drop the employee table if it exists
        db.execSQL("DROP TABLE IF EXISTS employee")

        // create the new employee table
        onCreate(db)
    }

}