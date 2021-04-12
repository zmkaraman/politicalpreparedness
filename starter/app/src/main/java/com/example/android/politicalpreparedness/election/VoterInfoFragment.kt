package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import kotlinx.android.synthetic.main.list_item_election.view.*

class VoterInfoFragment : Fragment() {

    private lateinit var binding: FragmentVoterInfoBinding


    private var isToggle = false
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_voter_info, container, false
        )


        arguments?.let {
            val division = VoterInfoFragmentArgs.fromBundle(it).argDivision
            val electionId = VoterInfoFragmentArgs.fromBundle(it).argElectionId
            val election = VoterInfoFragmentArgs.fromBundle(it).argElection


            //  Toast.makeText(context, "Division ${division.state} - ${division.country} - " +
            //        "electionId $electionId ", Toast.LENGTH_SHORT).show()


            //binding.electionName.title = "Hey tih is election"
           binding.title.text = election.name

            //TODO: Add ViewModel values and create ViewModel

            //TODO: Add binding values

            //TODO: Populate voter info -- hide views without provided data.
            /**
            Hint: You will need to ensure proper data is provided from previous fragment.
             */


            //TODO: Handle loading of URLs

        }



        //Handle save button UI state & cont'd Handle save button clicks
        binding.followButton.setOnClickListener {
            clickToggleButton()
        }

        return binding.root
    }

    //TODO: Create method to load URL intents

    private fun clickToggleButton() {
        binding.followButton.text = if (isToggle) context?.getString(R.string.follow) else context?.getString(R.string.unfollow)
        isToggle = !isToggle
        //TODO MERVE dbye kaydetme
    }

}