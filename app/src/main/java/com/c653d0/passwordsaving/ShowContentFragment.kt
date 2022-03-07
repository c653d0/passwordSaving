package com.c653d0.passwordsaving

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.c653d0.passwordsaving.tool.ItemTouchHelperCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShowContentFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var addContentButton:FloatingActionButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layoutInflater = LayoutInflater.from(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_show_content,container,false)

        recyclerView = view.findViewById(R.id.password_list_recyclerview)
        addContentButton = view.findViewById(R.id.addContentButton)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel:PasswordViewModel by viewModels {
            PasswordViewModelFactory(requireActivity().application)
        }
        val adapter = PasswordAdapter()
        val callback = ItemTouchHelperCallback()
        callback.setViewModel(viewModel)
        val itemTouchHelper = ItemTouchHelper(callback)

        recyclerView?.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        itemTouchHelper.attachToRecyclerView(recyclerView)

        viewModel.getAllPassWord().observe(this, Observer {
            adapter.setData(it)
            callback.setList(it)
            adapter.notifyDataSetChanged()
        })

        addContentButton?.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_detailInfoFragment)
        }
    }

}