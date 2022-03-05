package com.example.myapplication

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DatabaseControl {
    fun readAccount(database: SQLiteDatabase,id: String,pw: String) : ArrayList<ArrayList<String>> {
            var sql = "SELECT * FROM account WHERE id='$id' and pw='$pw'"
            var result: Cursor = database.rawQuery(sql,null)

            val dataList = ArrayList<ArrayList<String>>()

            while (result.moveToNext()) { // 다음 raw로 넘어감
            val seq = result.getInt(0)
            val id = result.getString(1)
            val pw = result.getString(2)

            val row = arrayListOf<String>(seq.toString(),id,pw)
            dataList.add(row)
        }
        result.close() //cursor를 닫아주지 않으면 다음 작업이 실행되지 않음

        return dataList
        }

        fun createAccount(database: SQLiteDatabase, id: String, pw:String) {
            val sql = "INSERT INTO account('id', 'pw') VALUES('$id', '$pw')"
            database.execSQL(sql)
        }

        fun updateAccount(database: SQLiteDatabase, seq:String, column:String, value:String) {
            val sql = "UPDATE account SET $column = '$value' WHERE seq = $seq"
            database.execSQL(sql)
        }

        fun deleteAccount(database: SQLiteDatabase, seq:String) {
            val sql = "DELETE FROM account WHERE seq = $seq"
            database.execSQL(sql)
        }
    }