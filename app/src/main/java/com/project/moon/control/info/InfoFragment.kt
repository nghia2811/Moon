package com.project.moon.control.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.project.moon.R
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_info.view.*

class InfoFragment : Fragment() {

    val image = "https://firebasestorage.googleapis.com/v0/b/project-3-1ca3b.appspot.com/o/device%2FXe%20Arduino.png?alt=media&token=56d2311a-5dd8-4df2-92f7-70ff63c24aee"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_info, container, false)
        Glide.with(activity!!)
            .load(image).into(view.info_image)

        return view
    }

}