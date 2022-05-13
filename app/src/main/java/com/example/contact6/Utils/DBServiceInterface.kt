package com.example.contact6.Utils

import com.example.contact6.Models.MyContact

interface DBServiceInterface {
    fun addContact(contact: MyContact)

    fun deleteContact(contact: MyContact)

    fun EditContact(contact: MyContact):Int

    fun getAllContact():List<MyContact>
}