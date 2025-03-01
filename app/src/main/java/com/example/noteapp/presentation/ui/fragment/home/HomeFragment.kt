package com.example.noteapp.presentation.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.data.model.Note
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.presentation.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HomeNoteAdapter
    private lateinit var rcvListNotes: RecyclerView


    private val viewModel: NoteViewModel by viewModels()
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
        addMenu()
        adapter = HomeNoteAdapter()
        rcvListNotes = binding.rcvListNotes
        rcvListNotes.adapter = adapter
        rcvListNotes.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.filterState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                1 -> {
                    binding.tvNoFilter.setBackgroundResource(R.drawable.custom_tv_filter_select)
                    binding.tvFilterAsc.setBackgroundResource(R.drawable.custom_tv_filter)
                    binding.tvFilterDesc.setBackgroundResource(R.drawable.custom_tv_filter)

                    viewModel.allNotes.observe(viewLifecycleOwner, Observer { notes ->
                        handlerUiEmptyNote(notes)
                        adapter.setData(notes)
                    })
                }

                2 -> {
                    binding.tvNoFilter.setBackgroundResource(R.drawable.custom_tv_filter)
                    binding.tvFilterAsc.setBackgroundResource(R.drawable.custom_tv_filter)
                    binding.tvFilterDesc.setBackgroundResource(R.drawable.custom_tv_filter_select)

                    viewModel.allNotesHighToLow.observe(viewLifecycleOwner, Observer { notes ->
                        handlerUiEmptyNote(notes)
                        adapter.setData(notes)
                    })
                }

                3 -> {
                    binding.tvNoFilter.setBackgroundResource(R.drawable.custom_tv_filter)
                    binding.tvFilterAsc.setBackgroundResource(R.drawable.custom_tv_filter_select)
                    binding.tvFilterDesc.setBackgroundResource(R.drawable.custom_tv_filter)

                    viewModel.allNotesLowToHigh.observe(viewLifecycleOwner, Observer { notes ->
                        handlerUiEmptyNote(notes)
                        adapter.setData(notes)
                    })
                }
            }
        })
    }

    private fun handlerUiEmptyNote(notes: List<Note>) {
        if(notes.isEmpty()){
            binding.layoutNoteEmpty.visibility = View.VISIBLE
            binding.rcvListNotes.visibility = View.GONE
        }else{
            binding.layoutNoteEmpty.visibility = View.GONE
            binding.rcvListNotes.visibility = View.VISIBLE
        }
    }

    private fun initListener() {
        binding.floatBtnAddNoteHome.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
        binding.tvNoFilter.setOnClickListener {
            viewModel.setStateFilter(1)
        }
        binding.tvFilterDesc.setOnClickListener {
            viewModel.setStateFilter(2)

        }
        binding.tvFilterAsc.setOnClickListener {
            viewModel.setStateFilter(3)
        }
    }

    private fun handleBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun addMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.my_menu, menu)

                val actionSearch = menu.findItem(R.id.action_search)
                val searchView = actionSearch.actionView as SearchView
                searchView.queryHint = "Search by title..."
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.searchNoteWithTitle("%$newText%")
                            .observe(viewLifecycleOwner) { notes ->
                                adapter.setData(notes)
                            }
                        return true
                    }
                })

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        true
                    }

                    R.id.action_delete -> {
                        deleteAllNotes()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun deleteAllNotes() {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete all notes")
                .setMessage("Do you want to delete all notes?")
                .setPositiveButton("Yes") { _, _ ->
                    lifecycleScope.launch {
                        viewModel.deleteAllNotes()
                    }
                    Toast.makeText(requireContext(), "Notes deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}