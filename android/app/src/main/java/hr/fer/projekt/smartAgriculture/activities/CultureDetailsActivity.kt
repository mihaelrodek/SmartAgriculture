package hr.fer.projekt.smartAgriculture.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.model.CultureModel
import hr.fer.projekt.smartAgriculture.model.User
import hr.fer.projekt.smartAgriculture.repository.Repository
import hr.fer.projekt.smartAgriculture.viewModel.CultureViewModel
import hr.fer.projekt.smartAgriculture.viewModel.factory.CultureViewModelFactory


class CultureDetailsActivity : AppCompatActivity() {

    lateinit var culture: CultureModel
    lateinit var cultureModel: CultureModel
    lateinit var viewModel: CultureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_culture_details)

        val measurementButton: Button = findViewById(R.id.measurements_button)
        val devicesButton: Button = findViewById(R.id.devices_button)
        val notificationBell: ImageView = findViewById(R.id.notification_bell)
        val title: TextView = findViewById(R.id.culture_title)
        val description: TextView = findViewById(R.id.culture_description)
        val deleteCulture = findViewById<Button>(R.id.delete_culture)

        val repository = Repository()
        val viewModelFactory = CultureViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CultureViewModel::class.java)

        culture = intent.getSerializableExtra("culture") as CultureModel
        cultureModel = CultureModel(
            culture.cultureId, culture.title, culture.devices, culture.description
        )

        title.text = culture.title
        description.text = culture.description

        measurementButton.setOnClickListener {
            viewModel.getCultures("Bearer ${User.user.token}")
            viewModel.responseLiveData.observe(this, {
                val cultureModelList = viewModel.responseLiveData.value?.body()
                if (cultureModelList != null) {
                    for (model in cultureModelList) {
                        if (model.cultureId == culture.cultureId)
                            cultureModel = model
                    }
                }
            })
            val intent: Intent = Intent(this, MeasurementsActivity::class.java)
            intent.putExtra("culture", cultureModel)
            startActivity(intent)
        }

        devicesButton.setOnClickListener {
            val intent: Intent = Intent(this, DevicesListActivity::class.java)
            intent.putExtra("culture", culture)
            startActivity(intent)
        }

        deleteCulture.setOnClickListener {
            viewModel.deleteCulture("Bearer ${User.user.token}", culture.cultureId)
            finish()
        }

        notificationBell.setOnClickListener {
            val intent: Intent = Intent(this, SetCultureBoundaries::class.java)
            intent.putExtra("cultureId", culture.cultureId)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCultures("Bearer ${User.user.token}")
        viewModel.responseLiveData.observe(this, {
            val cultureModelList = viewModel.responseLiveData.value?.body()
            if (cultureModelList != null) {
                for (model in cultureModelList) {
                    if (model.cultureId == culture.cultureId)
                        cultureModel = model
                }
            }
        })
    }
}