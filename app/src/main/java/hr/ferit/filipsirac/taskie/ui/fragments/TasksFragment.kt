package hr.ferit.brunozoric.taskie.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.EXTRA_SCREEN_TYPE
import hr.ferit.brunozoric.taskie.common.EXTRA_TASK_ID
import hr.ferit.brunozoric.taskie.common.gone
import hr.ferit.brunozoric.taskie.common.visible
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.ui.activities.ContainerActivity
import hr.ferit.brunozoric.taskie.ui.activities.MainActivity
import hr.ferit.brunozoric.taskie.ui.adapters.TaskAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import hr.ferit.filipsirac.taskie.persistence.TaskieRepository
import hr.ferit.filipsirac.taskie.persistence.TaskieRoomRepository
import hr.ferit.filipsirac.taskie.ui.fragments.MenuMethods
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener, MenuMethods.ClearAll,
    MenuMethods.sortByPriority {


    private val repository: TaskieRepository = TaskieRoomRepository()
    private val adapter by lazy { TaskAdapter({ onItemSelected(it) }, { onItemSwiped(it) }) }


    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        refreshTasks()
    }

    override fun onResume() {
        super.onResume()
        this.setHasOptionsMenu(true)
        (activity as MainActivity).setListeners(this)
        refreshTasks()

    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }

    override fun sortByPriority() {
        progress.gone()
        val data = repository.getAllTasks()
        if (data.isNotEmpty()) {
            data.sortByDescending { it.priority.ordinal }
            noData.gone()
        } else {
            noData.visible()
        }
        adapter.setData(data)
    }

    override fun clearAllTasks() {
        if (repository.getAllTasks().isNotEmpty()) {
            repository.deleteAllTasks()
        }
        refreshTasks()
    }

    private fun onItemSelected(task: Task) {
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.id)
        }
        startActivity(detailsIntent)
    }


    private fun onItemSwiped(it: Task) {
        repository.deleteTask(it)
        Toast.makeText(this.context, "Task deleted", Toast.LENGTH_LONG).show()
        refreshTasks()
    }

    private fun refreshTasks() {
        progress.gone()
        val data = repository.getAllTasks()
        if (data.isNotEmpty()) {
            noData.gone()
        } else {
            noData.visible()
        }
        adapter.setData(repository.getAllTasks())
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskAdded(task: Task) {
        refreshTasks()
    }

    companion object {
        fun newInstance(): Fragment {
            return TasksFragment()
        }
    }
}