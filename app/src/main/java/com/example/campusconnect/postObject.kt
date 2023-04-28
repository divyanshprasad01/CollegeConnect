package com.example.campusconnect

class postObject {
    var userName : String? = null
    var imageLink: String? = null
    var caption: String? = null

    constructor(){}

    constructor(userName: String?, imageLink:String?, caption:String?){
        this.userName = userName
        this.imageLink = imageLink
        this.caption = caption
    }
}