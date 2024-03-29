package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.EXTRA_TASK_ID
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import hr.ferit.filipsirac.taskie.persistence.TaskieRepository
import hr.ferit.filipsirac.taskie.persistence.TaskieRoomRepository
import hr.ferit.filipsirac.taskie.ui.fragments.UpdateTaskFragmentDialog
import kotlinx.android.synthetic.main.fragment_task_details.*

class TaskDetailsFragment : BaseFragment(), UpdateTaskFragmentDialog.TaskUpdatedListener {

    private val repository: TaskieRepository = TaskieRoomRepository()
    private var taskID: Int = NO_TASK

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_task_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
    }


    private fun tryDisplayTask(id: Int) {
        try {
            val task = repository.get(id)
            update.setOnClickListener { updateTask(task) }
            displayTask(task)
        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun displayTask(task: Task) {
        detailsTaskTitle.text = task.title
        detailsTaskDescription.text = task.description
        detailsPriorityView.setImageResource(task.priority.getColor())
    }


    private fun updateTask(task: Task) {
        val dialog = UpdateTaskFragmentDialog.newInstance()
        dialog.setTaskUpdatedListener(this)
        dialog.setCurrentTask(task)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskUpdate() {
        tryDisplayTask(taskID)
    }


    companion object {
        const val NO_TASK: Int = -1

        fun newInstance(taskId: Int): TaskDetailsFragment {
            val bundle = Bundle().apply { putInt(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
