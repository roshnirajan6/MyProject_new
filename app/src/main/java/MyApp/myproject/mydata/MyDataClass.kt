package MyApp.myproject.mydata

import android.os.Parcel
import android.os.Parcelable


data class MyDataClass(var title : String, var url : String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyDataClass> {
        override fun createFromParcel(parcel: Parcel): MyDataClass {
            return MyDataClass(parcel)
        }

        override fun newArray(size: Int): Array<MyDataClass?> {
            return arrayOfNulls(size)
        }
    }
}



/*

data class MyDataClass(var imageurl : String ,var title : String) {
}*/
