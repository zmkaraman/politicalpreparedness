package com.example.android.politicalpreparedness.network.jsonadapter

import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.Office
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.representative.model.Representative
import com.google.gson.Gson
import org.json.JSONObject
import java.text.SimpleDateFormat


fun parseElectionsJsonResult(jsonResult: JSONObject): ArrayList<Election> {

    val electionsJson = jsonResult.getJSONArray("elections")
    val electionList = ArrayList<Election>()

    for (i in 0 until electionsJson.length()) {
        val electionJson = electionsJson.getJSONObject(i)
        val id = electionJson.getLong("id")
        val name = electionJson.getString("name")
        val electionDay = electionJson.getString("electionDay")
        val division = electionJson.getString("ocdDivisionId")

        val div = divisionFromJson(division)
        val date = SimpleDateFormat("YYYY-MM-DD").parse(electionDay)
        //var el = Gson()?.fromJson(electionsJson.getJSONObject(i).toString(), Election::class.java)

        val election = Election(id.toInt(), name, date, div)
        electionList.add(election)
    }

    return electionList
}

fun parseVoterJsonResult(jsonResult: JSONObject): VoterInfoResponse {
    return Gson()?.fromJson(jsonResult.toString(), VoterInfoResponse::class.java)
}

