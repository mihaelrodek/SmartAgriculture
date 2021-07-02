package hr.fer.projekt.smartAgriculture.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.model.LoginModel
import hr.fer.projekt.smartAgriculture.model.User
import hr.fer.projekt.smartAgriculture.repository.Repository
import hr.fer.projekt.smartAgriculture.util.Constants.Companion.CHANNEL_ID
import hr.fer.projekt.smartAgriculture.viewModel.LoginViewModel
import hr.fer.projekt.smartAgriculture.viewModel.factory.LoginViewModelFactory

class LogInActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val username = findViewById<EditText>(R.id.username_login)
        val password = findViewById<EditText>(R.id.password_login)
        val registerInLink = findViewById<TextView>(R.id.register_in_link)
        val signInButton = findViewById<Button>(R.id.sign_in_button)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val loginErrorTextView = findViewById<TextView>(R.id.login_error_text_view)
        val loginErrorResponse = findViewById<TextView>(R.id.login_error_response)

        createNotificationChannel()

        username.setOnFocusChangeListener { _, _ -> removeErrorMessages() }
        password.setOnFocusChangeListener { _, _ -> removeErrorMessages() }

        registerInLink.setOnClickListener {
            val intent = Intent(this, RegisterInActivity::class.java)
            startActivity(intent)
        }

        signInButton.setOnClickListener {


            loading.visibility = View.VISIBLE

            val repository = Repository()
            val viewModelFactory = LoginViewModelFactory(repository)

            viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
            val loginModel = LoginModel(username.text.toString(), password.text.toString())
            viewModel.login(loginModel)

            viewModel.responseLiveData.observe(this, Observer { response ->
                loading.visibility = View.GONE
                if (response.isSuccessful) {
                    User.user.token = response.body()?.token.toString()
                    User.user.username = response.body()?.username.toString()
                    val intent = Intent(this@LogInActivity, CulturesListActivity::class.java)
                    startActivity(intent)
                } else {
                    loginErrorTextView.visibility = View.VISIBLE
                    loginErrorResponse.text = "${response.message()}  ${response.code()}"
                    loginErrorResponse.visibility = View.VISIBLE
                }
            })
        }
    }

    private fun removeErrorMessages() {
        val loginErrorTextView = findViewById<TextView>(R.id.login_error_text_view)
        val loginErrorResponse = findViewById<TextView>(R.id.login_error_response)
        loginErrorTextView.visibility = View.GONE
        loginErrorResponse.visibility = View.GONE
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.chanel_name)
            val descriptionText = getString(R.string.chanel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
