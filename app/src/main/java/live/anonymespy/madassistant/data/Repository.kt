package live.anonymespy.madassistant.data

import android.content.Context
import com.google.gson.Gson
import com.yariksoffice.lingver.Lingver
import java.io.InputStreamReader

class Repository(private val context: Context) {
    private val gson = Gson()

    fun getEmergencyContacts(): List<EmergencyContact> {
        val currentLanguage = Lingver.getInstance().getLanguage()
        val fileName = if (currentLanguage == "fr") "contact.json" else "contact-en.json"

        return try {
            val inputStream = context.assets.open(fileName)
            val reader = InputStreamReader(inputStream)
            val response = gson.fromJson(reader, EmergencyContactsResponse::class.java)
            reader.close()
            response.emergencyContacts
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getFirstAidProcedures(): FirstAidResponse {
        val currentLanguage = Lingver.getInstance().getLanguage()
        val fileName = if (currentLanguage == "fr") "FirstAid.json" else "FirstAid-en.json"

        return try {
            val inputStream = context.assets.open(fileName)
            val reader = InputStreamReader(inputStream)
            val response = gson.fromJson(reader, FirstAidResponse::class.java)
            reader.close()
            response
        } catch (e: Exception) {
            FirstAidResponse(emptyList(), EmergencyNumbers("", "", "", "", "", ""), emptyList())
        }
    }
}
