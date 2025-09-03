package com.example.frontend_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.frontend_android.R
import com.example.frontend_android.ui.dashboard.ProjectViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class AddProjectDialog : DialogFragment() {

    private val projectVM: ProjectViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_project_form, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val projectName = view.findViewById<EditText>(R.id.projectName)
        val projectDescription = view.findViewById<EditText>(R.id.description_key)
        val projectGenre = view.findViewById<EditText>(R.id.genre_key)
        val addBtn = view.findViewById<MaterialButton>(R.id.create_project_btn)
        val closeBtn = view.findViewById<MaterialButton>(R.id.close_dialog_btn)

        addBtn.setOnClickListener {
            projectVM.addProject(
                projectName.text.toString(),
                projectDescription.text.toString(),
                projectGenre.text.toString()
            )

        }


        isCancelable = true
    }
    override fun onStart(){
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}
