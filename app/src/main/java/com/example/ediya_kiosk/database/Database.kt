package com.example.ediya_kiosk.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context?, name : String, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(database: SQLiteDatabase?) {
        val accountSql = "CREATE TABLE IF NOT EXISTS account(id INTEGER, password TEXT, email TEXT,certification_num INTEGER,phone_num INTEGER)"
        val basketSql = "CREATE TABLE IF NOT EXISTS basket(id INTEGER, basket_index PRIMARY KEY AUTOINCREMENT, INTEGER, menu_name TEXT, menu_price INTEGER, menu_count INTEGER, menu_size INTEGER, menu_temp BOOLEAN, option_cost INTEGER, total_cost INTEGER)"
        val interfaceSql = "CREATE TABLE IF NOT EXISTS interface(id INTEGER, isMode BOOLEAN, isLanguage BOOLEAN)"
        val orderSql = "CREATE TABLE IF NOT EXISTS basketOrder(id INTEGER PRIMARY KEY AUTOINCREMENT, order_index INTEGER PRIMARY KEY AUTOINCREMENT, menu_name TEXT, menu_price INTEGER, menu_count INTEGER)"

        database!!.execSQL(accountSql)
        database!!.execSQL(basketSql)
        database!!.execSQL(interfaceSql)
        database!!.execSQL(orderSql)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 테이블을 삭제하고 새로 만들 때 쓰는 함수
    }
}