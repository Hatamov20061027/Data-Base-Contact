
package com.example.contact6.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contact6.Models.MyContact
import com.example.contact6.Utils.Constant
import com.example.contact6.Utils.DBServiceInterface

class MyDbHelper(context: Context)
    : SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.DB_VERSION) ,
    DBServiceInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table ${Constant.TABLE_NAME} (${Constant.ID} integer not null primary key autoincrement unique, ${Constant.NAME} text not null, ${Constant.PHONE_NUMBER} text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists ${Constant.TABLE_NAME}")
        onCreate(db)
    }

    override fun addContact(contact: MyContact) {
        val dataBase = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(Constant.NAME, contact.name)
        contentValue.put(Constant.PHONE_NUMBER, contact.number)
        dataBase.insert(Constant.TABLE_NAME, null, contentValue)
        dataBase.close()
    }

    override fun deleteContact(contact: MyContact) {
        val database = this.writableDatabase
        database.delete(Constant.TABLE_NAME, "${Constant.ID} = ?", arrayOf("${contact.id}"))
        database.close()
    }

    override fun EditContact(contact:MyContact):Int {
        val database = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(Constant.ID, contact.id)
        contentValue.put(Constant.NAME, contact.name)
        contentValue.put(Constant.PHONE_NUMBER, contact.number)

       return database.update(Constant.TABLE_NAME, contentValue, "${Constant.ID} = ?", arrayOf(contact.id.toString()))
    }

    override fun getAllContact(): ArrayList<MyContact> {
        val list = ArrayList<MyContact>()
        val query = "select * from ${Constant.TABLE_NAME}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do{
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val phone = cursor.getString(2)
                val contact = MyContact(id, name, phone)
                list.add(contact)
            }while (cursor.moveToNext())
        }
        return list
    }
}