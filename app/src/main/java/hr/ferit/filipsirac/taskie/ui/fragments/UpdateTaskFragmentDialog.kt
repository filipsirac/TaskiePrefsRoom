package hr.ferit.filipsirac.taskie.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.filipsirac.taskie.persistence.TaskiePrefs
import hr.ferit.filipsirac.taskie.persistence.TaskieRepository
import hr.ferit.filipsirac.taskie.persistence.TaskieRoomRepository
import kotlinx.android.synthetic.main.fragment_dialog_update_task.*

class UpdateTaskFragmentDialog : DialogFragment() {

    private var taskUpdatedListener: TaskUpdatedListener? = null
    private val repository: TaskieRepository = TaskieRoomRepository()
    private lateinit var currentTask: Task

    interface TaskUpdatedListener {
        fun onTaskUpdate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    fun setTaskUpdatedListener(listener: UpdateTaskFragmentDialog.TaskUpdatedListener) {
        taskUpdatedListener = listener
    }

    fun setCurrentTask(task: Task) {
        currentTask = task
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_update_task, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi() {
        context?.let {
            prioritySelectorUpdate.adapter =
                ArrayAdapter<Priority>(it, android.R.layout.simple_spinner_dropdown_item, Priority.values())
            prioritySelectorUpdate.setSelection(currentTask.priority.ordinal)
            updateTaskTitleInput.setText(currentTask.title)
            updateTaskDescriptionInput.setText(currentTask.description)

        }

    }

    private fun initListeners() {
        updateTaskAction.setOnClickListener { updateTask() }
    }

    private fun updateTask() {
        if (isInputEmpty()) {
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = updateTaskTitleInput.text.toString()
        val description = updateTaskDescriptionInput.text.toString()
        val priority = prioritySelectorUpdate.selectedItem as Priority

        TaskiePrefs.store(TaskiePrefs.KEY_PRIORITY_NAME, priority.ordinal)

        repository.editTask(
            currentTask,
            title,
            description,
            priority
        )

        clearUi()
        taskUpdatedListener?.onTaskUpdate()
        dismiss()
    }

    private fun clearUi() {
        updateTaskTitleInput.text.clear()
        updateTaskDescriptionInput.text.clear()
        prioritySelectorUpdate.setSelection(0)
    }

    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(updateTaskTitleInput.text) || TextUtils.isEmpty(
        updateTaskDescriptionInput.text
    )

    companion object {
        fun newInstance(): UpdateTaskFragmentDialog {
            return UpdateTaskFragmentDialog()
        }
    }


}