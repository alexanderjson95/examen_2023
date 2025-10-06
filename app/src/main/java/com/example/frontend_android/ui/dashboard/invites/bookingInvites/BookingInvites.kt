package com.example.frontend_android.ui.dashboard.invites.bookingInvites
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_android.R
import com.example.frontend_android.model.Bookings.BookingStatusType
import com.example.frontend_android.ui.dashboard.invites.projectInvites.ProjectInvitesDirections
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookingInvites : Fragment(R.layout.fragment_booking_invites)
{
    private val vm: UserBookingsViewmodel by viewModels()
    private lateinit var adapter: UserBookingsRequestStatusAdapter

    // regissor - 111111

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.projectRequestRecycler)
        adapter = UserBookingsRequestStatusAdapter(
            remove = { b -> vm.declineBooking(b) },
            accept = { b -> vm.acceptBooking(b, BookingStatusType.ACCEPTED) }
        )

        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val no_invite_card = view.findViewById<MaterialCardView>(R.id.no_invite_card)


        val toggleFilter = view.findViewById<ChipGroup>(R.id.filterToggleGroup)
        toggleFilter.setOnCheckedStateChangeListener { _, checkedIds ->

            val all = vm.bookings.value
            val filter = all.filter  { p ->
                if (checkedIds.isEmpty()){
                    adapter.submitList(all)
                    return@setOnCheckedStateChangeListener
                }


                /*
                    1,1 = accepterad    (kommer bytas ut med ENUM längre fram, detta är en gammal lösning)
                    0,1 = inbjuden
                    1,0 = tillgänglig
                 */
                val bList = listOf(p.status == BookingStatusType.AVAILABLE,p.status == BookingStatusType.ACCEPTED)
                when(bList){
                    listOf(true, true) -> R.id.fAccepted in checkedIds
                    listOf(false, true) -> R.id.fInvite in checkedIds
                    listOf(true, false) -> R.id.fAvailability in checkedIds
                    else -> false
                }
            }
            adapter.submitList(filter)
        }




        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnProjectNav  -> {
                        val action = BookingInvitesDirections.toProjectInvites()
                        findNavController().navigate(action)

                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            vm.invites.collect { m ->
                if (m.isNotEmpty()) {
                    adapter.submitList(m)
                    no_invite_card.visibility = if (showCard()) View.VISIBLE else View.GONE
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            vm.available.collect { m ->
                if (m.isNotEmpty()) {
                    adapter.submitList(m)
                    no_invite_card.visibility = if (showCard()) View.VISIBLE else View.GONE
                }
            }
        }


    }
    private fun showCard(): Boolean {
        val inv= vm.invites.value
        val avail = vm.available.value
        return inv.isNullOrEmpty() && avail.isNullOrEmpty()
    }
}