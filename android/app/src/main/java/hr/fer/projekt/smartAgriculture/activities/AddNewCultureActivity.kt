package hr.fer.projekt.smartAgriculture.activities

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.model.CultureModel
import hr.fer.projekt.smartAgriculture.model.DeviceModel
import hr.fer.projekt.smartAgriculture.model.User
import hr.fer.projekt.smartAgriculture.repository.Repository
import hr.fer.projekt.smartAgriculture.viewModel.CultureViewModel
import hr.fer.projekt.smartAgriculture.viewModel.factory.CultureViewModelFactory

class AddNewCultureActivity : AppCompatActivity() {

    lateinit var viewModel: CultureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_culture_activity)

        val saveCultureButton = findViewById<Button>(R.id.saveAgricultureButton)
        val agricultureTitleEditText = findViewById<EditText>(R.id.agricultureTitleEditText)
        val agricultureDescriptionEditText = findViewById<EditText>(R.id.agricultureDescriptionEditText)
        val devicesSpinner: Spinner = findViewById(R.id.devices_spinner)

        val repository = Repository()
        val viewModelFactory = CultureViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CultureViewModel::class.java)

        val adapter = OPTIONS?.let {ArrayAdapter(this, android.R.layout.simple_spinner_item, it)}

        devicesSpinner.adapter = adapter

        devicesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddNewCultureActivity, "Please select a device ", Toast.LENGTH_LONG).show()
            }
        }

        saveCultureButton.setOnClickListener {
            if (agricultureTitleEditText.text.toString() != "" && agricultureDescriptionEditText.text.toString() != "") {
                val devices: List<DeviceModel> = listOf(devicesSpinner.selectedItem as DeviceModel)
                val cultureModel = CultureModel(
                    cultureId = -1,
                    title = agricultureTitleEditText.text.toString(),
                    devices = devices,
                    description = agricultureDescriptionEditText.text.toString()
                )

                viewModel.addCulture("Bearer ${User.user.token}", cultureModel)
                finish()
            }
        }
    }

    companion object {
        var OPTIONS: Array<DeviceModel>? = null
    }
}