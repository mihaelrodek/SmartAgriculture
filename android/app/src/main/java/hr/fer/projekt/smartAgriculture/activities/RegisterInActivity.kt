package hr.fer.projekt.smartAgriculture.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.model.RegistrationModel
import hr.fer.projekt.smartAgriculture.model.User
import hr.fer.projekt.smartAgriculture.repository.Repository
import hr.fer.projekt.smartAgriculture.viewModel.RegisterViewModel
import hr.fer.projekt.smartAgriculture.viewModel.factory.RegisterViewModelFactory

class RegisterInActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_in)


        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val email = findViewById<EditText>(R.id.email)

        findViewById<TextView>(R.id.first_name).setOnFocusChangeListener { v, hasFocus -> removeErrorMessages() }
        findViewById<TextView>(R.id.last_name).setOnFocusChangeListener { v, hasFocus -> removeErrorMessages() }
        findViewById<TextView>(R.id.email).setOnFocusChangeListener { v, hasFocus -> removeErrorMessages() }
        findViewById<TextView>(R.id.username).setOnFocusChangeListener { v, hasFocus -> removeErrorMessages() }
        findViewById<TextView>(R.id.password).setOnFocusChangeListener { v, hasFocus -> removeErrorMessages() }

        val registerButton = findViewById<Button>(R.id.register_in_button)
        registerButton.setOnClickListener {


            val repository = Repository()
            val viewModelFactory = RegisterViewModelFactory(repository)

            viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)
            val registrationModel = RegistrationModel(
                username = username.text.toString(),
                email = email.text.toString(),
                password = password.text.toString()
            )
            viewModel.registerUser(registrationModel)

            viewModel.responseLiveData.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    User.user.token = response.body()?.token.toString()
                    User.user.username = response.body()?.username.toString()
                    val intent = Intent(this@RegisterInActivity, LogInActivity::class.java)
                    startActivity(intent)
                } else {
                    findViewById<TextView>(R.id.login_error_text_view).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.login_error_response).text =
                        "${response.message()} ${response.code()}"
                    findViewById<TextView>(R.id.login_error_response).visibility = View.VISIBLE
                }
            })

        }
    }

    private fun removeErrorMessages() {
        findViewById<TextView>(R.id.login_error_text_view).visibility = View.GONE
        findViewById<TextView>(R.id.login_error_response).visibility = View.GONE
    }
}