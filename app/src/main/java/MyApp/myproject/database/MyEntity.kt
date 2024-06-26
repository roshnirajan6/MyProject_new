package MyApp.myproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Update

@Entity
class MyEntity() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_column")
    var Id : Int = 0

    @ColumnInfo(name = "name_column")
    var Name : String = ""

    @ColumnInfo(name = "email_column")
    var Email : String = ""

    @ColumnInfo(name = "password_column")
    var Password : String = ""



}