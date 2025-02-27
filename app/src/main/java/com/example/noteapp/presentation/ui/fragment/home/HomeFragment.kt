package com.example.noteapp.presentation.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.data.model.Note
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.presentation.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding :FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HomeNoteAdapter
    private lateinit var rcvListNotes : RecyclerView


    private val viewModel : NoteViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListener()
        handleBack()
    }

    private fun initUi() {
        adapter = HomeNoteAdapter()
        rcvListNotes = binding.rcvListNotes
        rcvListNotes.adapter = adapter
        rcvListNotes.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.allNotes.observe(viewLifecycleOwner, Observer { notes ->
            adapter.setData(notes)
        })
    }

    private fun initListener() {
        binding.floatBtnAddNoteHome.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleBack(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

}