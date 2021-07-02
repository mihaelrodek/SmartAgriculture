package hr.fer.projekt.smartAgriculture.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.model.BoundaryModel
import hr.fer.projekt.smartAgriculture.model.User
import hr.fer.projekt.smartAgriculture.repository.Repository
import hr.fer.projekt.smartAgriculture.viewModel.CultureBoundariesViewModel
import hr.fer.projekt.smartAgriculture.viewModel.factory.CultureBoundariesViewModelFactory

class SetCultureBoundaries : AppCompatActivity() {

    lateinit var viewModel: CultureBoundariesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_culture_boundaries)

        val cultureId = intent.getLongExtra("cultureId", 0)

        val saveNewCultureBoundary = findViewById<Button>(R.id.save_new_culture_boundary)
        saveNewCultureBoundary.setOnClickListener {
            val newBoundaries = BoundaryModel(
                cultureId = cultureId,
                minAirTemperature = findViewById<EditText>(R.id.min_air_tem_edit_text).text.toString().toDouble(),
                maxAirTemperature = findViewById<EditText>(R.id.max_air_tem_edit_text).text.toString().toDouble(),
                minSoilTemperature = findViewById<EditText>(R.id.min_soil_temp_edit_text).text.toString().toDouble(),
                maxSoilTemperature = findViewById<EditText>(R.id.max_soil_temp_edit_text).text.toString().toDouble(),
                minAirHumidity = findViewById<EditText>(R.id.min_air_humidity_edit_text).text.toString().toDouble(),
                maxAirHumidity = findViewById<EditText>(R.id.max_air_humidity_edit_text).text.toString().toDouble(),
                minSoilHumidity = findViewById<EditText>(R.id.min_soil_humidity_edit_text).text.toString().toDouble(),
                maxSoilHumidity = findViewById<EditText>(R.id.max_soil_humidity_edit_text).text.toString().toDouble(),
                minPressure = findViewById<EditText>(R.id.min_pressure_edit_text).text.toString().toDouble(),
                maxPressure = findViewById<EditText>(R.id.max_pressure_edit_text).text.toString().toDouble(),
                minLuminosity = findViewById<EditText>(R.id.min_luminosity_edit_text).text.toString().toDouble(),
                maxLuminosity = findViewById<EditText>(R.id.max_luminosity_edit_text).text.toString().toDouble()
            )

            val repository = Repository()
            val viewModelFactory = CultureBoundariesViewModelFactory(repository)
            viewModel = ViewModelProvider(
                this,
                viewModelFactory
            ).get(CultureBoundariesViewModel::class.java)
            viewModel.addBoundary("Bearer ${User.user.token}", newBoundaries)
            viewModel.responseLiveData.observe(this, Observer {
            })

            finish()
        }
    }
}
