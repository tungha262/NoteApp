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
import com.example.noteapp.R
import com.example.noteapp.data.model.Note
import com.example.noteapp.databinding.FragmentAddNoteBinding
import com.example.noteapp.presentation.viewmodel.NoteViewModel
import com.example.noteapp.util.Const
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class AddNoteFragment : Fragment() {
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private var priority = Const.LOW_PRIORITY
    private lateinit var tvTitle: String
    private lateinit var tvContent: String

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        lifecycleScope.launch {
            viewModel.isCreatedNote.collect { isCreated ->
                if(isCreated){
                    Toast.makeText(requireContext(), "Note created successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addNoteFragment_to_homeFragment)
                }else{
                    Toast.makeText(requireContext(), "Title can't be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initListener() {
        binding.floatBtnAddNote.setOnClickListener {
            tvTitle = binding.edtNoteTitle.text.toString()
            tvContent = binding.edtNoteContent.text.toString()
            creatNote(tvTitle, tvContent)
        }

        binding.imgPriorityGreen.setOnClickListener {
            binding.imgPriorityGreen.setImageResource(R.drawable.ic_done)
            binding.imgPriorityRed.setImageResource(0)
            binding.imgPriorityYellow.setImageResource(0)
            priority = Const.LOW_PRIORITY
        }

        binding.imgPriorityRed.setOnClickListener {
            binding.imgPriorityRed.setImageResource(R.drawable.ic_done)
            binding.imgPriorityGreen.setImageResource(0)
            binding.imgPriorityYellow.setImageResource(0)
            priority = Const.HIGH_PRIORITY
        }

        binding.imgPriorityYellow.setOnClickListener {
            binding.imgPriorityYellow.setImageResource(R.drawable.ic_done)
            binding.imgPriorityRed.setImageResource(0)
            binding.imgPriorityGreen.setImageResource(0)
            priority = Const.MEDIUM_PRIORITY
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun creatNote(tvTitle: String, tvContent: String) {
        val calendar = Calendar.getInstance()
        val dinhDang = SimpleDateFormat("HH:mm    dd/MM/yyyy")
        val date = dinhDang.format(calendar.time)
        val note = Note(0,tvTitle,tvContent,date,priority)
        lifecycleScope.launch {
            viewModel.addNote(note)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}