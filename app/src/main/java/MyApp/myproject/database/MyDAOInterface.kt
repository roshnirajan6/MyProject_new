package MyApp.myproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MyDAOInterface {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveData(myEntity: MyEntity)

    @Query("Select * from MyEntity")
    fun readData() : List<MyEntity>

    @Query("select * from MyEntity where email_column = :email")
    fun getUserByEmail(email: String): MyEntity?

}