package MyApp.myproject
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
class SharedPreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyCart", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveSelectedItemsList(selectedItemsList: MutableList<Map<String, String>>) {
        val json = gson.toJson(selectedItemsList)
        sharedPreferences.edit().putString("selectedItemsList", json).apply()
    }
    fun getSelectedItemsList(): MutableList<Map<String, String>> {
        val json = sharedPreferences.getString("selectedItemsList", null)
        val type: Type = object : TypeToken<MutableList<Map<String, String>>>() {}.type
        return gson.fromJson(json, type)
    }
    fun removeItemFromList(position: Int) {
        val currentList = getSelectedItemsList().toMutableList()
        currentList.removeAt(position)
        saveSelectedItemsList(currentList)
    }
    fun clearSelectedItemsList() {
        // Clear the selected items list from SharedPreferences
    sharedPreferences.edit().remove("selectedItemsList").apply()

    }
}