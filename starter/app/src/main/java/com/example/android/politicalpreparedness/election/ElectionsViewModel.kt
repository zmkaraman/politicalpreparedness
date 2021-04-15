package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.jsonadapter.parseElectionsJsonResult
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject


class ElectionsViewModel(private val dataSource: ElectionDao) : ViewModel() {


    // Create live data val for upcoming elections
    private val _upcomingElection = MutableLiveData<List<Election>>()
    val upcomingElection: LiveData<List<Election>>
        get() = _upcomingElection

    //Create live data val for saved elections
    private val _savedElections = MutableLiveData<List<Election>>()
    val savedElections: LiveData<List<Election>>
        get() = _savedElections

    var upcomingElections: List<Election> = mutableListOf() //TODO MERVE

    //Add live data to hold voter info
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage


    // Create val and functions to populate live data for upcoming elections from the API and
    fun getElections() {
        viewModelScope.launch {
            try {
                val responseBody = CivicsApi.retrofitService.getElections()
                upcomingElections = parseElectionsJsonResult((JSONObject(responseBody.string())))
                _upcomingElection.value = upcomingElections

            } catch (e: Exception) {
                _errorMessage.postValue("Sorry something went wrong! Please try again later!")

            }

        }
    }

    // saved elections from local database
    fun getSavedElections() {
        viewModelScope.launch(context = Dispatchers.IO) {
            try {
                var list = dataSource.getAllElections()
                _savedElections.postValue(list)
            } catch (e: Exception) {
                _savedElections.postValue(emptyList())
            }
        }
    }

    fun resetErrorMsg(){
        _errorMessage.value = null
    }
    //TODO: Create functions to navigate to saved or upcoming election voter info


}