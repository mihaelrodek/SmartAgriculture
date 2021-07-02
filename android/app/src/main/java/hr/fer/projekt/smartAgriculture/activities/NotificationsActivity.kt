package hr.fer.projekt.smartAgriculture.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.services.NotificationService

class NotificationsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        val start_button = findViewById<Button>(R.id.start_button)
        val stop_button = findViewById<Button>(R.id.stop_button)

        start_button.setOnClickListener {
            NotificationService.KEEP_RUNNING = true
            Intent(this, NotificationService::class.java).also { intent ->
                startService(intent)
            }
            finish()
        }

        stop_button.setOnClickListener {
            NotificationService.KEEP_RUNNING = false
            finish()
        }
    }
}
