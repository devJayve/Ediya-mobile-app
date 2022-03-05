package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory

class Database(context: Context?, name : String, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(database: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS account(seq INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, pw TEXT)"
        database!!.execSQL(sql)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 테이블을 삭제하고 새로 만들 때 쓰는 함수
    }
}