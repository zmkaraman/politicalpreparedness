package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.jsonadapter.parseElectionsJsonResult
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch
import org.json.JSONObject

//TODO: Construct ViewModel and provide election datasource

enum class CivisApiStatus { LOADING, ERROR, DONE }

class ElectionsViewModel: ViewModel() {

    private val _status = MutableLiveData<CivisApiStatus>()
    val status: LiveData<CivisApiStatus>
        get() = _status

    // Create live data val for upcoming elections
    private val _upcomingElection = MutableLiveData<List<Election>>()
    val upcomingElection: LiveData<List<Election>>
        get() = _upcomingElection

    //Create live data val for saved elections
    private val _savedElection = MutableLiveData<List<Election>>()
    val savedElection: LiveData<List<Election>>
        get() = _savedElection

    var upcomingElections: List<Election> = mutableListOf()


    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    fun getElections(): List<Election> {

        viewModelScope.launch {

            _status.value = CivisApiStatus.LOADING
            try {

                val responseBody = CivicsApi.retrofitService.getElections()

                upcomingElections = parseElectionsJsonResult((JSONObject(responseBody.string())))
                _upcomingElection.value = upcomingElections
                _status.value = CivisApiStatus.DONE

            } catch (e: Exception) {
                _status.value = CivisApiStatus.ERROR
            }

        }

        return upcomingElections
    }


    //TODO: Create functions to navigate to saved or upcoming election voter info

}