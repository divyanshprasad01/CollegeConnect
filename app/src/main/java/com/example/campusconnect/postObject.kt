package com.example.campusconnect

import android.net.Uri

class postObject {
    var userName : String? = null
    var publisher:String? = null
    var postId: String? = null
    var imageLink: String? = null
    var caption: String? = null

    constructor(){}

    constructor(userName: String?, publisher:String?, postId:String?, imageLink:String?, caption:String?){
        this.userName = userName
        this.publisher = publisher
        this.imageLink = imageLink
        this.caption = caption
        this.postId = postId
    }
}