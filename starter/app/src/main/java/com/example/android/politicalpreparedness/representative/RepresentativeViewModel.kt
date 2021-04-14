package com.example.android.politicalpreparedness.representative

import android.widget.Toast
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.jsonadapter.parseRepresentativesResult
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch
import org.json.JSONObject

class RepresentativeViewModel: ViewModel() {

    //Establish live data for representatives and address
    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    //Create function to fetch representatives from API from a provided address
    fun getRepresentativesByAddress(address: String) {

        viewModelScope.launch {
            try {
                val responseBody = CivicsApi.retrofitService.getRepresentativesByAdress(address = address)

                _representatives.value = parseRepresentativesResult(JSONObject(responseBody.string()))

               // _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }


            } catch (e: Exception) {
                //TODO MERVE hata mesaji bas
            }

        }
    }


    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    fun getRepresentativesByDivisionId(ocdId: String) {

        viewModelScope.launch {
            try {
                val responseBody = CivicsApi.retrofitService.getRepresentativesByOcdId(ocdId = ocdId)
                var jsonResult = (JSONObject(responseBody.string()))

            } catch (e: Exception) {
                //TODO MERVE hata mesaji bas
            }

        }
    }


    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}

class RepresentativeViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepresentativeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepresentativeViewModel() as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }

}