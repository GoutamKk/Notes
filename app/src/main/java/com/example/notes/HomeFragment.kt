package com.example.notes

import android.os.Bundle
import android.os.SystemClock
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.Visibility
import com.example.notes.Database.NotesData
import com.example.notes.Database.RoomDB
import com.example.notes.Model.MainVMFactory
import com.example.notes.Model.MainViewModel
import com.example.notes.Model.Repository
import com.example.notes.Recycler.RecyclerAdapter
import com.example.notes.databinding.FragmentHomwBinding

class HomeFragment : Fragment() {

    var _binding : FragmentHomwBinding?= null
    val binding get() = _binding!!
    lateinit var adapter: RecyclerAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomwBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()
        setupRecyclerView()
        searchNote()

        viewModel.getAll().observe(viewLifecycleOwner) { notes->
            if (notes.isEmpty()){
                binding.noDataHere.visibility = View.VISIBLE
                binding.homeRecycler.visibility =View.GONE
            }
            binding.noDataHere.visibility = View.GONE
            binding.homeRecycler.visibility =View.VISIBLE
            adapter.setNotes(notes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDeleteDialog(note: NotesData) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete '${note.title}'?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteNote(note)
            }
            .setNegativeButton("No", null)
            .show()
    }

    fun searchNote(){
        binding.searchId.doOnTextChanged { text,_,_,_ ->
            viewModel.searchNote(text.toString()).observe(viewLifecycleOwner) { adapter.setNotes(it) }
        }
    }

    private fun setViewModel(){
        val dao = RoomDB.getInstance(requireContext()).notesDao
        val repo = Repository(dao)
        val factory = MainVMFactory(repo)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = RecyclerAdapter(
            onClick = { note ->
                val bundle = Bundle().apply { putInt("current_id", note.id) }
                findNavController().navigate(R.id.writeNoteFragment, bundle)
            },
            onDelete = { note ->
                showDeleteDialog(note)
            }
        )
        binding.homeRecycler.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.homeRecycler.adapter = adapter
    }
}