package hr.fer.projekt.smartAgriculture.activities

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.model.DeviceModel
import hr.fer.projekt.smartAgriculture.model.User
import hr.fer.projekt.smartAgriculture.repository.Repository
import hr.fer.projekt.smartAgriculture.viewModel.DeviceViewModel
import hr.fer.projekt.smartAgriculture.viewModel.factory.DeviceViewModelFactory


class AddNewDeviceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_device)

        val devicesSpinner: Spinner = findViewById(R.id.devices_spinner)
        val addButton: Button = findViewById(R.id.add_button)

        val adapter = OPTIONS?.let {ArrayAdapter(this, android.R.layout.simple_spinner_item, it)}
        val cultureId: Long = intent.getLongExtra("cultureId", 0)

        val repository = Repository()
        val viewModelFactory = DeviceViewModelFactory(repository)
        val deviceViewModel = ViewModelProvider(this, viewModelFactory).get(DeviceViewModel::class.java)

        devicesSpinner.adapter = adapter

        devicesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddNewDeviceActivity, "Please select a device ", Toast.LENGTH_LONG).show()
            }
        }

        addButton.setOnClickListener {
            val device: DeviceModel = devicesSpinner.selectedItem as DeviceModel
            deviceViewModel.addDeviceToCulture("Bearer ${User.user.token}", cultureId, device.id)
            finish()
        }
    }

    companion object {
        var OPTIONS: Array<DeviceModel>? = null
    }
}