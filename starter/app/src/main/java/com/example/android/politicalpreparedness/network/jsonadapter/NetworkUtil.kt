package com.example.android.politicalpreparedness.network.jsonadapter

import com.example.android.politicalpreparedness.network.models.Election
import org.json.JSONObject
import java.text.SimpleDateFormat


fun parseElectionsJsonResult(jsonResult: JSONObject): ArrayList<Election> {

    val electionsJson = jsonResult.getJSONArray("elections")
    val electionAdapter : ElectionAdapter = ElectionAdapter()


    val electionList = ArrayList<Election>()

    for (i in 0 until electionsJson.length()) {
        val electionJson = electionsJson.getJSONObject(i)
        val id = electionJson.getLong("id")
        val name = electionJson.getString("name")
        val electionDay = electionJson.getString("electionDay")
        val division = electionJson.getString("ocdDivisionId")

        val div = electionAdapter.divisionFromJson(division)
        val date = SimpleDateFormat("YYYY-MM-DD").parse(electionDay)


        val election = Election(id.toInt(), name, date, div)
        electionList.add(election)
    }

    return electionList
}
