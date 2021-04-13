package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election

class ElectionsFragment : Fragment() {

    //Declare ViewModel
    private val viewModel: ElectionsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, ElectionsViewModelFactory()).get(ElectionsViewModel::class.java)
    }

    private var viewModelAdapter: ElectionListAdapter? = null


    private lateinit var binding: FragmentElectionBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_election, container, false
        )


        //Add ViewModel values and create ViewModel
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //Add binding values
        viewModelAdapter = ElectionListAdapter(ElectionListAdapter.ElectionListener { election ->
            // Link elections to voter info
            this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election, election.division))

        })

        // Populate recycler adapters
        // Sets the adapter of the RecyclerView
        binding.upcomingElectionsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.upcomingElectionsRv.adapter = viewModelAdapter

        //Initiate recycler adapters
        viewModel.getElections()

        return binding.root
    }

    //Refresh adapters when fragment loads
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.upcomingElection.observe(viewLifecycleOwner, Observer<List<Election>> { elections ->
            elections?.apply {
                viewModelAdapter?.submitList(elections)
                viewModelAdapter?.notifyDataSetChanged()

            }
        })
    }
}