package raum.muchbeer.roomktx

import android.app.RemoteInput
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        receiveRemoteInput()
    }

    private fun receiveRemoteInput() {
        val receiveIntent = Intent(this.intent)
        val receiveRemoteInput = androidx.core.app.RemoteInput.getResultsFromIntent(receiveIntent)

        if(receiveRemoteInput !=null) {
            val inputString = receiveRemoteInput.getCharSequence("key_value")

        }
    }
}