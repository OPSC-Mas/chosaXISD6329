package com.example.chosa_application

class CatModel {
    var catName: String? = null
    var catDesc: String? = null

    constructor(category: String?, description: String?){
        this.catName = category
        this.catDesc = description
    }
    constructor(){

    }
}
