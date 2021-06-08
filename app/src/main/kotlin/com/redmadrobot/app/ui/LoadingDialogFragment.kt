package com.redmadrobot.app.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.redmadrobot.app.R

class LoadingDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater: LayoutInflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.loading_dialog_fragment, null)

//        val spinner = view.findViewById<ImageView>(R.id.spinner)
//        var rocketAnimation: AnimationDrawable
//        spinner.apply {
//            setBackgroundResource(R.drawable.amination_spin)
//            rocketAnimation = background as AnimationDrawable
//        }
//        rocketAnimation.start()
        return AlertDialog.Builder(requireContext(), R.style.Widget_Workplaces_Dialog)
            .setView(view)
            .show()
    }

    companion object {
        const val TAG = "LoadingDialog"
    }
}
