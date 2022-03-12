package com.example.ediya_kiosk.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class DatabaseControl {

    fun readData(database : SQLiteDatabase,
                 table : String,
                 returnColumnArray: Array<String>,
                 selectionList : ArrayList<String>,
                 selectionArgsArray :Array<String>)
    : ArrayList<ArrayList<String>> {
        Log.d("TAG","readData")

        var returnDataList = arrayListOf<ArrayList<String>>()

        var whereSql = "${selectionList[0]}=?"
        if (selectionList.size > 1) {
            for (i in 1 until selectionList.size) {
                whereSql = whereSql.plus(" and ${selectionList[i]}=?")
                Log.d("DATABASE","whereSql is $whereSql")
            }
        }

        val cursor = database.query(
            table,
            returnColumnArray,
            whereSql,
            selectionArgsArray,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val row = arrayListOf<String>()
            for ((i) in (returnColumnArray.indices).withIndex()) {
                val value = cursor.getString(i)
                row.add(value)
            }
            returnDataList.add(row)
        }

        Log.d("DATABASE","$returnDataList")

        cursor.close()

        return returnDataList
    }

    fun createData(database: SQLiteDatabase,
                   table : String,
                   columnList : ArrayList<String>,
                   valueList : ArrayList<String>
    ) {
        var values = ContentValues().apply {
            for ((i) in (0 until columnList.size).withIndex()) {
                Log.d("TAG","$i")
                put(columnList[i],valueList[i])
            }
        }
        database.insert(table,null,values)
    }

    fun updateData(database: SQLiteDatabase,
                   table: String,
                   columnList: ArrayList<String>,
                   valueList: ArrayList<String>,
                   selectionList: ArrayList<String>,
                   selectionArgsArray :Array<String>)
    : Int {
        var updateValues = ContentValues().apply {
            for ((i) in (0 until columnList.size).withIndex()) {
                Log.d("TAG", "$i")
                put(columnList[i], valueList[i])
            }
        }

        var whereSql = "${selectionList[0]}=?"
        if (selectionList.size > 1) {
            for (i in 1 until selectionList.size) {
                whereSql = whereSql.plus(" and ${selectionList[i]}=?")
            }
        }

            val count = database.update(
                table,
                updateValues,
                whereSql,
                selectionArgsArray
            )

        return count
    }

    fun deleteData(database: SQLiteDatabase,
                   table: String,
                   selectionList: ArrayList<String>,
                   selectionArgsArray: Array<String>
    ) {
        var whereSql = "${selectionList[0]}=?"
        if (selectionList.size > 1) {
            for (i in 1 until selectionList.size) {
                whereSql = whereSql.plus(" and ${selectionList[i]}=?")
            }
        }

        database.delete(table,whereSql,selectionArgsArray)
    }
}