package ubr.persanal.todolist.ui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ubr.persanal.todolist.databinding.FragmentProgressBinding

class ProgressFragment :Fragment() {

    private lateinit var binding: FragmentProgressBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressBinding.inflate(inflater, container,false)
        return binding.root
    }



}