package com.example.campusconnect

class User {
    lateinit var name: String
    lateinit var uid: String
    lateinit var email: String
    lateinit var admissionNum: String

    constructor(){}

    constructor(name:String, email:String, uid:String){
        this.admissionNum = ""
        this.name = name
        this.email = email
        this.uid = uid
    }

    constructor(name:String, email:String, uid:String, admissionNum: String){
        this.admissionNum = admissionNum
        this.name = name
        this.email = email
        this.uid = uid
    }

}