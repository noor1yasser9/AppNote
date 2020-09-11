package com.nurbk.ps.noteapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nurbk.ps.noteapp.R
import com.nurbk.ps.noteapp.adapter.NoteAdapter
import com.nurbk.ps.noteapp.data.models.Note
import com.nurbk.ps.noteapp.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragmnet_my_note.*

class MyNoteFragment : Fragment(R.layout.fragmnet_my_note), NoteAdapter.OnItemClick {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    private val adapterNote by lazy {
        NoteAdapter(ArrayList(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNoteMain.setOnClickListener {
            findNavController().navigate(R.id.action_myNoteFragment_to_createNoteFragment)
        }

        initRecyclerView()

        viewModel.getNotes().observe(viewLifecycleOwner, Observer {
            adapterNote.data.clear()
            adapterNote.data = it as ArrayList<Note>
            adapterNote.notifyDataSetChanged()
        })


    }


    private fun initRecyclerView() {
        notesRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            adapter = adapterNote
            setHasFixedSize(true)
        }
    }


    override fun onClickItem(note: Note) {
        val bundle = Bundle()
        bundle.putParcelable("note", note)
        findNavController().navigate(
            R.id.action_myNoteFragment_to_createNoteFragment, bundle
        )
    }
}