package com.example.ediya_kiosk.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context?, name : String, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(database: SQLiteDatabase?) {
        val accountSql = "CREATE TABLE IF NOT EXISTS account(id TEXT, password TEXT, email TEXT, name TEXT,certification_num TEXT,phone_num TEXT,point INTEGER)"
        val basketSql = "CREATE TABLE IF NOT EXISTS basket(id TEXT, basket_index INTEGER, menu_name TEXT, menu_count INTEGER, menu_temp TEXT, menu_size TEXT, menu_price INTEGER, menu_img TEXT, option_cost INTEGER, total_cost INTEGER,pay_state Boolean)"
        val interfaceSql = "CREATE TABLE IF NOT EXISTS interface(id TEXT, isMode BOOLEAN, isLanguage BOOLEAN)"

        database!!.execSQL(accountSql)
        database!!.execSQL(basketSql)
        database!!.execSQL(interfaceSql)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 테이블을 삭제하고 새로 만들 때 쓰는 함수
    }
}