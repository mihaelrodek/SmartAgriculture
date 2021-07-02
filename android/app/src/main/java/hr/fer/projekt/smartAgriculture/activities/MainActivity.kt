package hr.fer.projekt.smartAgriculture.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import hr.fer.projekt.smartAgriculture.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerButton = findViewById<Button>(R.id.register_in_main)
        val signInButton = findViewById<Button>(R.id.sign_in_main)

        signInButton.setOnClickListener {
            val intent: Intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val intent: Intent = Intent(this, RegisterInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun removeErrorMessages() {

        val login_error_text_view = findViewById<TextView>(R.id.login_error_response)
        val login_error_response = findViewById<TextView>(R.id.login_error_response)
        login_error_text_view.visibility = View.GONE
        login_error_response.visibility = View.GONE
    }

}