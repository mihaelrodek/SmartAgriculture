package hr.fer.projekt.smartAgriculture.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.activities.adapters.SlideAdapter
import hr.fer.projekt.smartAgriculture.model.CultureModel
import hr.fer.projekt.smartAgriculture.model.User
import hr.fer.projekt.smartAgriculture.repository.Repository
import hr.fer.projekt.smartAgriculture.viewModel.MeasurementsViewModel
import hr.fer.projekt.smartAgriculture.viewModel.factory.MeasurementsViewModelFactory

class MeasurementsActivity : AppCompatActivity() {

    private lateinit var viewModel: MeasurementsViewModel
    private var slideAdapter: SlideAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measurements_sliding)

        val culture: CultureModel = intent.getSerializableExtra("culture") as CultureModel

        getLatestMeasurements(culture)
    }

    private fun getLatestMeasurements(cultureModel: CultureModel) {

        val slideViewPager = findViewById<ViewPager>(R.id.slideViewPager)
        val progres_bar = findViewById<ProgressBar>(R.id.progres_bar)
        val lastMeasurementDate = findViewById<TextView>(R.id.lastMeasurementDate)

        slideViewPager.visibility = View.INVISIBLE
        progres_bar.visibility = View.VISIBLE

        val repository = Repository()
        val viewModelFactory = MeasurementsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MeasurementsViewModel::class.java)
        viewModel.getLastMeasurements("Bearer ${User.user.token}")

        viewModel.responseLiveDataGetLastMeasurements.observe(this, Observer { response ->
            if (response.isSuccessful) {
                slideViewPager.visibility = View.VISIBLE
                progres_bar.visibility = View.GONE

                val list = response.body()?.filter {
                    cultureModel.devices.contains(it.device)
                }

                if (list != null) {
                    lastMeasurementDate.text =
                        getString(R.string.last_measurement_text) + " " + list.maxBy { m -> m.time.time }?.time.toString()
                }
                slideAdapter =
                    SlideAdapter(this@MeasurementsActivity, response.body()!!.toMutableList(), cultureModel)
                slideViewPager?.adapter = slideAdapter
            }
        })
    }
}
