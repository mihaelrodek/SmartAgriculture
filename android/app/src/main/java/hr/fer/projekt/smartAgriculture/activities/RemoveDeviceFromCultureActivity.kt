package hr.fer.projekt.smartAgriculture.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.model.DeviceModel
import hr.fer.projekt.smartAgriculture.model.User
import hr.fer.projekt.smartAgriculture.repository.Repository
import hr.fer.projekt.smartAgriculture.viewModel.DeviceViewModel
import hr.fer.projekt.smartAgriculture.viewModel.factory.DeviceViewModelFactory

class RemoveDeviceFromCultureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_device_from_culture)

        val deviceId = findViewById<TextView>(R.id.device_id)
        val deviceDevId = findViewById<TextView>(R.id.device_dev_id)
        val removeDeviceButton = findViewById<Button>(R.id.remove_device_button)

        val device = intent.getSerializableExtra("device") as DeviceModel
        val cultureId = intent.getLongExtra("cultureId", 0)

        val repository = Repository()
        val viewModelFactory = DeviceViewModelFactory(repository)
        val deviceViewModel = ViewModelProvider(this, viewModelFactory).get(DeviceViewModel::class.java)

        deviceId.text = device.id.toString()
        deviceDevId.text = device.devId

        removeDeviceButton.setOnClickListener {
            deviceViewModel.deleteDeviceFromCulture("Bearer ${User.user.token}", cultureId, device.id)
            finish()

        }















    }
}