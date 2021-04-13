package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.jsonadapter.parseVoterJsonResult
import com.example.android.politicalpreparedness.network.models.AdministrationBody
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch
import org.json.JSONObject

class VoterInfoViewModel(private val dataSource: ElectionDao?) : ViewModel() {

    //Add live data to hold voter info
    private val _voterInfoResponse = MutableLiveData<VoterInfoResponse>()
    val voterInfoResponse: LiveData<VoterInfoResponse>
        get() = _voterInfoResponse

    //Add live data to hold voter info
    private val _administrationBody = MutableLiveData<AdministrationBody>()
    val administrationBody: LiveData<AdministrationBody>
        get() = _administrationBody

    //Add var and methods to populate voter info
    fun getVoterInfo(voterKey: String, electionId: String) {

        viewModelScope.launch {
            try {
                val responseBody = CivicsApi.retrofitService.getVoterInfo(voterKey = voterKey, electionId =  electionId)
                _voterInfoResponse.value = parseVoterJsonResult((JSONObject(responseBody.string())))
                //_administrationBody.value = _voterInfoResponse.value.state.get(0).electionAdministrationBody

            } catch (e: Exception) {
            }

        }
    }

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    fun saveElection(election: Election){
    }

    fun removeElection(electionId: Int){
    }

    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */


}