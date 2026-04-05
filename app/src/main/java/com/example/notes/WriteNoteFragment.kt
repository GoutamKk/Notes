package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes.Database.NotesData
import com.example.notes.Database.RoomDB
import com.example.notes.Model.MainVMFactory
import com.example.notes.Model.MainViewModel
import com.example.notes.Model.Repository
import com.example.notes.databinding.FragmentWriteNoteBinding

class WriteNoteFragment : Fragment() {
    var _binding: FragmentWriteNoteBinding?=null
    val binding get() = _binding!!
    lateinit var viewModel: MainViewModel
    var clickId: Int =-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWriteNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickId = arguments?.getInt("current_id",-1)?:-1
        setviewModel()
        if (clickId != -1){
            setData(clickId!!.toInt())
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.saveBtn.setOnClickListener {
            saveData()
        }
    }


    fun saveData(){
        var title = binding.addNoteTitle.text.toString()
        var text = binding.addNoteDesc.text.toString()
        var time = System.currentTimeMillis().toString()
        if (text.isEmpty() && title.isEmpty()) return
        Thread{
            if (clickId != -1){
                viewModel.updteNote(NotesData(id=clickId!!.toInt() ,title =title, text = text ,time = time))
            }
            else{
                viewModel.insertNote(NotesData(title = title, text = text, time = time))
            }
            activity?.runOnUiThread {
                findNavController().popBackStack()
            }
        }.start()
    }

    private fun setData(id:Int){
        viewModel.getOne(id).observe(viewLifecycleOwner) { note ->
            if (note != null) {
                binding.addNoteTitle.setText(note.title)
                binding.addNoteDesc.setText(note.text)
            }
        }
    }

    private fun setviewModel(){
        val dao = RoomDB.getInstance(requireContext()).notesDao
        val repo = Repository(dao)
        val factory = MainVMFactory(repo)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}