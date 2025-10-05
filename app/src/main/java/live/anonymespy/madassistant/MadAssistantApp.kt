package live.anonymespy.madassistant

import android.app.Application
import com.yariksoffice.lingver.Lingver
import java.util.Locale

class MadAssistantApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Lingver with default locale
        Lingver.init(this, Locale.ENGLISH)
    }
}
