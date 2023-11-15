package com.example.chosa_application

class FDonModel {
    var orgName: String? = null
    var donContent: String? = null
    var donType: String? = null


    constructor(orgName: String?, donContent: String?, donType: String?){
        this.orgName = orgName
        this.donContent = donContent
        this.donType = donType

    }
    constructor(){

    }
}