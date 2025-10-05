package live.anonymespy.madassistant.data

import com.google.gson.annotations.SerializedName

data class EmergencyContact(
    val id: Int,
    val title: String,
    val description: String,
    val tel: String,
    @SerializedName("alternativeTel")
    val alternativeTel: String? = null,
    @SerializedName("thirdTel")
    val thirdTel: String? = null,
    @SerializedName("fourthTel")
    val fourthTel: String? = null,
    @SerializedName("emergencyTel")
    val emergencyTel: String? = null,
    val category: String,
    val subcategory: String? = null,
    val location: String? = null,
    val email: String? = null,
    val availability: String? = null
)

data class EmergencyContactsResponse(
    val emergencyContacts: List<EmergencyContact>
)
