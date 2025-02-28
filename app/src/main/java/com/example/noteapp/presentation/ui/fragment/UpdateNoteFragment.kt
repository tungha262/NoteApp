package com.example.noteapp.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentUpdateNoteBinding
import com.example.noteapp.util.Const
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import com.example.noteapp.data.model.Note
import com.example.noteapp.presentation.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateNoteFragment : Fragment() {
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var priorityUpdate: String
    private val viewModel: NoteViewModel by viewModels()

    private val arg: UpdateNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListener()
        lifecycleScope.launch {
            viewModel.isUpdatedNote.collect { update ->
                if (update) {
                    Toast.makeText(
                        requireContext(),
                        "Note updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), "Title can't be empty", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun initUi() {
        val note = arg.note
        priorityUpdate = note.priority
        binding.edtNoteTitle.setText(note.title)
        binding.edtNoteContent.setText(note.content)
        when (note.priority) {
            Const.HIGH_PRIORITY -> {
                binding.imgPriorityRed.setImageResource(R.drawable.ic_done)
            }

            Const.MEDIUM_PRIORITY -> {
                binding.imgPriorityYellow.setImageResource(R.drawable.ic_done)
            }

            else -> {
                binding.imgPriorityGreen.setImageResource(R.drawable.ic_done)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initListener() {
        binding.floatBtnUpdateNote.setOnClickListener {
            val noteOld = arg.note
            val title = binding.edtNoteTitle.text.toString()
            val content = binding.edtNoteContent.text.toString()
            val calendar = Calendar.getInstance()
            val dinhDang = SimpleDateFormat("HH:mm    dd/MM/yyyy")
            val date = dinhDang.format(calendar.time)
            val noteUpdate = Note(noteOld.id, title, content, date, priorityUpdate)
            if(checkNoteUpdate(noteOld, noteUpdate)){
                lifecycleScope.launch {
                    viewModel.updateNote(noteUpdate)
                }
            }
            binding.edtNoteTitle.clearFocus()
            binding.edtNoteContent.clearFocus()
        }


        binding.imgPriorityGreen.setOnClickListener {
            binding.imgPriorityGreen.setImageResource(R.drawable.ic_done)
            binding.imgPriorityRed.setImageResource(0)
            binding.imgPriorityYellow.setImageResource(0)
            priorityUpdate = Const.LOW_PRIORITY
        }

        binding.imgPriorityRed.setOnClickListener {
            binding.imgPriorityRed.setImageResource(R.drawable.ic_done)
            binding.imgPriorityGreen.setImageResource(0)
            binding.imgPriorityYellow.setImageResource(0)
            priorityUpdate = Const.HIGH_PRIORITY
        }

        binding.imgPriorityYellow.setOnClickListener {
            binding.imgPriorityYellow.setImageResource(R.drawable.ic_done)
            binding.imgPriorityRed.setImageResource(0)
            binding.imgPriorityGreen.setImageResource(0)
            priorityUpdate = Const.MEDIUM_PRIORITY
        }
    }

    private fun checkNoteUpdate(noteOld: Note, noteUpdate: Note) : Boolean{
        return noteOld.title != noteUpdate.title || noteOld.content != noteUpdate.content || noteOld.priority != noteUpdate.priority
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}