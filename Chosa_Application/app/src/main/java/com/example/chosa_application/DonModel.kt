package com.example.chosa_application

class DonModel {
    var orgName: String? = null
    var userAddress: String? = null
    var donContent: String? = null
    var donType: String? = null


    constructor(orgName: String?, userAddress: String?, donContent: String?, donType: String?){
        this.orgName = orgName
        this.userAddress = userAddress
        this.donContent = donContent
        this.donType = donType

    }
    constructor(){

    }
}