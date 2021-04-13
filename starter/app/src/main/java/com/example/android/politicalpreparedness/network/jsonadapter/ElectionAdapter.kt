package com.example.android.politicalpreparedness.network.jsonadapter

import com.example.android.politicalpreparedness.network.models.Division
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ElectionAdapter {
    @FromJson
    fun divisionFromJson (ocdDivisionId: String): Division {
        val countryDelimiter = "country:"
        val stateDelimiter = "state:"
        val country = ocdDivisionId.substringAfter(countryDelimiter,"")
                .substringBefore("/")
        val state = ocdDivisionId.substringAfter(stateDelimiter,"")
                .substringBefore("/")
        return Division(ocdDivisionId, country, state)
    }

    @ToJson
    fun divisionToJson (division: Division): String {
        return division.id
    }
}

@FromJson
fun divisionFromJson (ocdDivisionId: String): Division {
    val countryDelimiter = "country:"
    val stateDelimiter = "state:"
    val country = ocdDivisionId.substringAfter(countryDelimiter,"")
            .substringBefore("/")
    val state = ocdDivisionId.substringAfter(stateDelimiter,"")
            .substringBefore("/")
    return Division(ocdDivisionId, country, state)
}

/*
* "electionAdministrationBody": {
        "name": "Secretary of State",
        "electionInfoUrl": "https://www.sos.la.gov/ElectionsAndVoting/Pages/default.aspx",
        "electionRegistrationUrl": "https://www.sos.la.gov/ElectionsAndVoting/Pages/OnlineVoterRegistration.aspx",
        "electionRegistrationConfirmationUrl": "https://voterportal.sos.la.gov/",
        "absenteeVotingInfoUrl": "https://www.sos.la.gov/ElectionsAndVoting/PublishedDocuments/GeneralApplicationForAbsenteeByMailBallot.pdf",
        "votingLocationFinderUrl": "https://voterportal.sos.la.gov/Home/VoterLogin",
        "ballotInfoUrl": "https://voterportal.sos.la.gov/Home/VoterLogin",
        "correspondenceAddress": {
          "line1": "8585 Archives Ave",
          "city": "Baton Rouge",
          "state": "Louisiana",
          "zip": "70809"
        }
      }*/