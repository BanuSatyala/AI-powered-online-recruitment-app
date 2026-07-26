package com.example.aionlinerecurtement.Models

import android.os.Parcel
import android.os.Parcelable

data class Jobs (var id:Int,
                 var jobtitle:String,
                 var description:String,
                 var roles:String,
                 var skills:String,
                 var companyid:String,
                 var url:String,
                 var state:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(jobtitle)
        parcel.writeString(description)
        parcel.writeString(roles)
        parcel.writeString(skills)
        parcel.writeString(companyid)
        parcel.writeString(url)
        parcel.writeString(state)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Jobs> {
        override fun createFromParcel(parcel: Parcel): Jobs {
            return Jobs(parcel)
        }

        override fun newArray(size: Int): Array<Jobs?> {
            return arrayOfNulls(size)
        }
    }
}