package com.example.ediya_kiosk.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class DatabaseControl {
    fun readData(database : SQLiteDatabase, table : String, returnColumnList: ArrayList<String>, selectionList : ArrayList<String>,selectionArgsList :ArrayList<String>)
    : ArrayList<ArrayList<String>> {

        val returnDataList = ArrayList<ArrayList<String>>()
        var columnArray = returnColumnList.toArray(arrayOfNulls<String>(returnColumnList.size))
        var selectionArgsArray = selectionArgsList.toArray(arrayOfNulls<String>(selectionArgsList.size))

        var whereSql = "${selectionList[0]} =?"
        if (selectionList.size > 1) {
            for ((i) in (0 until selectionList.size).withIndex()) {
                whereSql + "and ${selectionList[i+1]}"
            }
        }

        val cursor = database.query(
            table,
            columnArray,
            whereSql,
            selectionArgsArray,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val row = arrayListOf<String>()
            for ((i) in (0 until returnColumnList.size).withIndex()) {
                val value = cursor.getString(i)
                row.add(value)
            }
            returnDataList.add(row)
        }

        cursor.close()

        return returnDataList
    }

    fun createData(database: SQLiteDatabase, table : String, columnList : ArrayList<String>, valueList : ArrayList<String>) {
        var values = ContentValues().apply {
            for ((i) in (0 until columnList.size).withIndex()) {
                put(columnList[i],valueList[i])
            }
        }
        database.insert(table,null,values)
    }

    fun updateData() {

    }

    fun deleteData() {

    }
}