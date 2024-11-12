package com.example.databaseexample.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context

class EmployeeDatabase(context: Context) {

    private val databaseHelper = DatabaseHelper(context)

    fun insert(name: String, code: String, image: ByteArray) {
        // get the writable database
        val db = databaseHelper.writableDatabase

        // create the ContentValues object
        val values = ContentValues().apply {
        put("employee_name", name)
        put("employee_code", code)
        put("employee_image", image)
        }

    // insert the data into the table
    db.insert("employee", null, values)

    // close the database connection
    db.close()
    }


    @SuppressLint("Range")
    fun getAll(): List<Employee> {
        val list = mutableListOf<Employee>()

        // get the readable database
        val db = databaseHelper.readableDatabase

        // select all data from the table
        val cursor = db.rawQuery("SELECT * FROM employee", null)

        // iterate through the cursor and add the data to the list
        while (cursor.moveToNext()) {
        val name = cursor.getString(cursor.getColumnIndex("employee_name"))
        val code = cursor.getString(cursor.getColumnIndex("employee_code"))
        val image = cursor.getBlob(cursor.getColumnIndex("employee_image"))
        list.add(Employee(name, code, image))
    }

    // close the cursor and database connection
    cursor.close()
    db.close()

    return list
    }

    fun update(id: Int, name: String, code: String, image: ByteArray) {
        // get the writable database
        val db = databaseHelper.writableDatabase

        // create the ContentValues object
        val values = ContentValues().apply {
        put("employee_name", name)
        put("employee_code", code)
        put("employee_image", image)
    }

    // update the data in the table
    db.update("employee", values, "id = ?", arrayOf(id.toString()))

    // close the database connection
    db.close()
}


fun delete(id: Int) {
    // get the writable database
    val db = databaseHelper.writableDatabase

    // delete the data from the table
    db.delete("employee", "id = ?", arrayOf(id.toString()))

    // close the database connection
    db.close()
}

data class Employee(val name: String, val code: String, val image: ByteArray)

}