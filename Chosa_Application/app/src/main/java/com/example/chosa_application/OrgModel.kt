package com.example.chosa_application

class OrgModel {
    var orgName: String? = null
    var orgDesc: String? = null
    var category: String? = null

    constructor(orgName: String?, orgDesc: String?, orgCategory: String?){
        this.orgName = orgName
        this.orgDesc = orgDesc
        this.category = orgCategory
    }
    constructor(){

    }
}