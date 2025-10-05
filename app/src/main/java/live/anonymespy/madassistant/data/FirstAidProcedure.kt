package live.anonymespy.madassistant.data

import com.google.gson.annotations.SerializedName

data class FirstAidProcedure(
    val id: Int,
    val title: String,
    val description: String,
    val category: String
)

data class EmergencyNumbers(
    val police: String,
    val fire: String,
    val gendarmerie: String,
    val medicalEmergency: String,
    val hospitalCHU: String,
    val cliniqueMM: String
)

data class FirstAidCategory(
    val id: String,
    val name: String,
    val description: String,
    val color: String
)

data class FirstAidResponse(
    val firstAidProcedures: List<FirstAidProcedure>,
    val emergencyNumbers: EmergencyNumbers,
    val categories: List<FirstAidCategory>
)
