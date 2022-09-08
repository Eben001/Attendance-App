package com.ebenezer.gana.aisattendance.ui.baseFragment

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ebenezer.gana.aisattendance.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    /**
     * Creates and show the snack bar
     * @param message the message to display on the snack bar
     * @param isError changes the color of the snackBar if true or false
     */
    fun showSnackBar(message: String, isError: Boolean) {
        val snackBar = Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message, Snackbar.LENGTH_LONG
        )
        val snackBarView = snackBar.view
        if (isError) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorSnackBarError
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(), R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }


}