package com.example.mychatapplication

class MessageModel {
    var message:String?=null
    var senderId:String?=null
    constructor(message:String,senderId:String){
        this.message=message
        this.senderId=senderId
    }
    constructor()
}