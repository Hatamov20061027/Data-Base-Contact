package com.example.contact6.Models

class MyContact {
    var id : Int? = null

    constructor(name: String?, phoneNumber: String?) {
        this.name = name
        this.number = phoneNumber
    }

    constructor(id: Int?) {
        this.id = id
    }

    constructor(id: Int?, name: String?, phoneNumber: String?) {
        this.id = id
        this.name = name
        this.number = phoneNumber
    }
    var name:String? = null
    var number:String? = null
    override fun toString(): String {
        return "MyContact(id=$id, name=$name, number=$number)"
    }


}