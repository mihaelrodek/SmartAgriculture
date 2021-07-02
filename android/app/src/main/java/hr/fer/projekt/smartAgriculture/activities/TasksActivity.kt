package hr.fer.projekt.smartAgriculture.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.fer.projekt.smartAgriculture.database.DatabaseHandler
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.viewModel.TasksViewModel
import hr.fer.projekt.smartAgriculture.viewModel.factory.TaskViewModelFactory

class TasksActivity : AppCompatActivity() {

    lateinit var tasksAdapter: TasksAdapter
    lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        val listOfTasksView = findViewById<RecyclerView>(R.id.list_of_tasks_view)


        listOfTasksView.layoutManager = LinearLayoutManager(applicationContext)

        val decorator = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.cell_divider
            )!!
        )
        listOfTasksView.addItemDecoration(decorator)

        val databaseHandler: DatabaseHandler = DatabaseHandler(applicationContext)
        val viewModelFactory = TaskViewModelFactory(databaseHandler)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TasksViewModel::class.java)
        tasksAdapter = TasksAdapter(viewModel)
        listOfTasksView.adapter = tasksAdapter

        viewModel.tasksList.observe(this, Observer {
            tasksAdapter.notifyDataSetChanged()
        })

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddNewTaskActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTasks()
    }

    inner class TasksAdapter(listOfTasksViewModel: TasksViewModel) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

        private var listOfTasks: TasksViewModel = listOfTasksViewModel

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var taskTextView: TextView? = null

            init {
                taskTextView = itemView.findViewById(R.id.task_text_text_view)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val taskListElement = inflater.inflate(R.layout.task_element_layout, parent, false)
            val listOfTasksView = findViewById<RecyclerView>(R.id.list_of_tasks_view)

            taskListElement.setOnClickListener {
                val itemPosition = listOfTasksView.getChildLayoutPosition(it)
                val task = listOfTasks.tasksList.value?.get(itemPosition)
                startActivity(
                    Intent(
                        this@TasksActivity,
                        AddNewTaskActivity::class.java
                    ).putExtra("task", task).putExtra("position", itemPosition)
                )
            }
            taskListElement.setOnLongClickListener {
                var itemPosition = listOfTasksView.getChildLayoutPosition(it)
                var task = listOfTasks.tasksList.value?.get(itemPosition)
                if (task != null) {
                    viewModel.deleteTask(task)
                }
                true
            }


            return ViewHolder(taskListElement)
        }

        override fun getItemCount(): Int {
            return if (listOfTasks.tasksList.value != null) {
                listOfTasks.tasksList.value!!.count()
            } else {
                0
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.taskTextView?.text = listOfTasks.tasksList.value!![position].task
        }
    }
}
