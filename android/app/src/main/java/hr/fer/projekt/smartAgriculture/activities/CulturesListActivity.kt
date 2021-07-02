package hr.fer.projekt.smartAgriculture.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.model.User
import hr.fer.projekt.smartAgriculture.repository.Repository
import hr.fer.projekt.smartAgriculture.viewModel.CultureViewModel
import hr.fer.projekt.smartAgriculture.viewModel.DeviceViewModel
import hr.fer.projekt.smartAgriculture.viewModel.factory.CultureViewModelFactory
import hr.fer.projekt.smartAgriculture.viewModel.factory.DeviceViewModelFactory

class CulturesListActivity : AppCompatActivity() {

    lateinit var viewModel: CultureViewModel
    lateinit var culturesAdapter: CulturesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_culures_list)

        val listOfCulturesView = findViewById<RecyclerView>(R.id.listOfAgriculturesView)
        val newAgricultureActionButton = findViewById<FloatingActionButton>(R.id.newAgricultureActionButton)

        listOfCulturesView.layoutManager = LinearLayoutManager(applicationContext)

        val decorator = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.cell_divider)!!)
        listOfCulturesView.addItemDecoration(decorator)

        val repository = Repository()
        val viewModelFactory = CultureViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CultureViewModel::class.java)
        culturesAdapter = CulturesAdapter(viewModel)
        listOfCulturesView.adapter = culturesAdapter

        viewModel.responseLiveData.observe(this, Observer {
            culturesAdapter.notifyDataSetChanged()
        })

        newAgricultureActionButton.setOnClickListener {
            val devicesViewModelFactory = DeviceViewModelFactory(repository)
            val devicesViewModel = ViewModelProvider(this, devicesViewModelFactory).get(DeviceViewModel::class.java)
            devicesViewModel.getDevices("Bearer ${User.user.token}")
            devicesViewModel.responseLiveData.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    AddNewCultureActivity.OPTIONS = response.body()?.toTypedArray()
                    val intent = Intent(this, AddNewCultureActivity::class.java)
                    startActivity(intent)
                }
            })
        }

        val myTasksButton = findViewById<Button>(R.id.MyTasksButton)
        myTasksButton.setOnClickListener {
            startActivity(Intent(applicationContext, TasksActivity::class.java))
        }

        val notificationsButton = findViewById<Button>(R.id.NotificationsButton)
        notificationsButton.setOnClickListener {
            startActivity(Intent(applicationContext, NotificationsActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCultures("Bearer ${User.user.token}")
    }

    inner class CulturesAdapter(listOfCulturesViewModel: CultureViewModel) : RecyclerView.Adapter<CulturesAdapter.ViewHolder>() {

        private var listOfCultures: CultureViewModel = listOfCulturesViewModel

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var cultureTitleTextView: TextView? = null

            init {
                cultureTitleTextView = itemView.findViewById(R.id.noteTitleTextView)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CulturesAdapter.ViewHolder {
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val agricultureListElement = inflater.inflate(R.layout.culture_list_element, parent, false)

            val listOfCulturesView = findViewById<RecyclerView>(R.id.listOfAgriculturesView)

            agricultureListElement.setOnClickListener {

                var itemPosition = listOfCulturesView.getChildLayoutPosition(it)
                val culture = listOfCultures.responseLiveData.value?.body()?.get(itemPosition)
                startActivity(Intent(this@CulturesListActivity, CultureDetailsActivity::class.java).putExtra("culture", culture))
                culturesAdapter.notifyDataSetChanged()
            }
            return ViewHolder(agricultureListElement)
        }

        override fun getItemCount(): Int {
            return if (listOfCultures.responseLiveData.value?.body() != null) {
                listOfCultures.responseLiveData.value?.body()!!.count()
            } else {
                0
            }
        }

        override fun onBindViewHolder(holder: CulturesAdapter.ViewHolder, position: Int) {
            holder.cultureTitleTextView?.text =
                listOfCultures.responseLiveData.value?.body()!![position].title
        }

    }

}

