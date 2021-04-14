package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse


class VoterInfoFragment : Fragment() {

    private val dataSource = context?.let { ElectionDatabase.getInstance(it).electionDao }

    private lateinit var electionVoterInfo: Election

    //Declare ViewModel
    private val viewModel: VoterInfoViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, VoterInfoViewModelFactory(dataSource)).get(VoterInfoViewModel::class.java)
    }

    private lateinit var binding: FragmentVoterInfoBinding
    private var isToggle = false

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_voter_info, container, false
        )

        //Add ViewModel values and create ViewModel
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        arguments?.let {

            /**
            Hint: You will need to ensure proper data is provided from previous fragment.
             */

            val division = VoterInfoFragmentArgs.fromBundle(it).argDivision
            val electionId = VoterInfoFragmentArgs.fromBundle(it).argElectionId
            electionVoterInfo = VoterInfoFragmentArgs.fromBundle(it).argElection
            //electionVoterInfo = election

            // Add binding values
            binding.title.text = electionVoterInfo.name

            //Populate voter info -- hide views without provided data.
            var voterKey = division.country + ", " + division.state
            viewModel.getVoterInfo(electionId = electionId.toString(), voterKey = voterKey)

            binding.stateBallot.setOnClickListener {
                openUrl(viewModel.voterInfoResponse.value?.state?.first()?.electionAdministrationBody?.ballotInfoUrl ?: "")
            }

            binding.stateLocations.setOnClickListener {
                openUrl(viewModel.voterInfoResponse.value?.state?.first()?.electionAdministrationBody?.votingLocationFinderUrl ?: "")
            }
        }


        //Handle save button UI state & cont'd Handle save button clicks
        binding.followButton.setOnClickListener {
            clickToggleButton()
        }

        return binding.root
    }

    //Create method to load URL intents
    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun clickToggleButton() {
        binding.followButton.text = if (isToggle) context?.getString(R.string.follow) else context?.getString(R.string.unfollow)
        isToggle = !isToggle

        if (isToggle) viewModel.saveElection(electionVoterInfo) else viewModel.removeElection(electionVoterInfo?.id)
    }

    //Refresh adapters when fragment loads
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.voterInfoResponse.observe(viewLifecycleOwner, Observer<VoterInfoResponse> { voterInfoResponse ->
            voterInfoResponse?.apply {

                //TODO MERVE binding adaptera tasi
                binding.stateHeader.visibility = View.VISIBLE
                binding.stateBallot.visibility = View.VISIBLE
                binding.stateLocations.visibility = View.VISIBLE
                binding.stateCorrespondenceHeader.visibility = View.VISIBLE
                binding.addressGroup.visibility = View.VISIBLE
            }
        })
    }
}