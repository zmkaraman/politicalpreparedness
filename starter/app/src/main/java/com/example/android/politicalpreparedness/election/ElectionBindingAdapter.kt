package com.example.android.politicalpreparedness.election

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import java.util.*


@BindingAdapter("electionDate")
fun bindTextViewToKmUnit(textView: TextView, date: Date) {
    val context = textView.context
    textView.text = date.toString()
}

@BindingAdapter("address")
fun bindTextViewToAddress(textView: TextView, voterInfoResponse: VoterInfoResponse?) {

    var state = voterInfoResponse?.state?.first()

    state?.let {
        textView.text = state.electionAdministrationBody.correspondenceAddress?.toFormattedString()
    }

}