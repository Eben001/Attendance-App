package com.ebenezer.gana.aisattendance.ui.start

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.ebenezer.gana.aisattendance.R
import com.ebenezer.gana.aisattendance.data.models.Day
import com.ebenezer.gana.aisattendance.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {


    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost = requireActivity()

        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.admin_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.action_admin ->{
                        findNavController().navigate(R.id.admin_nav_graph)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        observeViewModels()
        viewModel.getAvailableAttendance()
    }

    private fun observeViewModels() {
        viewModel.availableAttendance.observe(viewLifecycleOwner) {
            bind(it)
            setOnClickListeners(it)
        }
    }

    private fun setOnClickListeners(day: Day) {
        binding.cardAvailableAttendance.setOnClickListener {
            val action =
                StartFragmentDirections.actionStartFragmentToAttendanceListFragment(day)
           this. findNavController().navigate(action)
        }


    }


    private fun bind(day: Day) {
        binding.date.text = day.day
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}