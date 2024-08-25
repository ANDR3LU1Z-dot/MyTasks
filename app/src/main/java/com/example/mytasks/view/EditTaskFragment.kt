package com.example.mytasks.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.example.mytasks.R
import com.example.mytasks.database.model.TaskModel
import com.example.mytasks.databinding.FragmentEditTaskBinding
import com.example.mytasks.viewmodel.TasksViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.concurrent.timerTask

class EditTaskFragment : Fragment() {

    private lateinit var binding : FragmentEditTaskBinding

    private val viewModel: TasksViewModel by lazy {
        ViewModelProvider(this)[TasksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val args: EditTaskFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.task?.let {
            binding.newTaskLabel.text = "Edite sua tarefa: "
            binding.addTaskButton.setText(R.string.update_button_text)
            binding.editTextTitleTask.setText(it.title)
            binding.editTextDescTask.setText(it.description)

        }


        setListeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setListeners(){

        binding.addTaskButton.setOnClickListener {
            val title = binding.editTextTitleTask.text.toString()
            val desc = binding.editTextDescTask.text.toString()

            if(title.isEmpty()){
                Toast.makeText(
                    requireActivity(),
                    "Por favor, preencha o titulo da tarefa",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val task = TaskModel(
                    args.task?.id ?: 0,
                    title,
                    desc,
                    getCurrentData(),
                    args.task?.done ?: false
                )
//                viewModel.insertOrUpdateTask(task)
                if(args.task == null){
                    viewModel.insertTask(task)
                    Toast.makeText(
                        requireActivity(),
                        "Tarefa adicionada com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                } else {
                    viewModel.updateTask(task)
                    Toast.makeText(
                        requireActivity(),
                        "Tarefa atualizada com sucesso",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (args.task != null){
            binding.deleteTaskButton.visibility = View.VISIBLE
            binding.deleteTaskButton.setOnClickListener {
                args.task?.let {
                    viewModel.deleteTask(it)
                    Toast.makeText(
                        requireActivity(),
                        "Tarefa deletada com sucesso",
                        Toast.LENGTH_SHORT).show()
                }
                findNavController().popBackStack()

            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentData(): String {

        val localDate = LocalDate.now()

        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate)

    }

}