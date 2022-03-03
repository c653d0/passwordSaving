package com.c653d0.passwordsaving

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private var viewPager:ViewPager2? = null
    private var bottomNavigationView:BottomNavigationView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layoutInflater = LayoutInflater.from(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_home, container, false)

        viewPager = view.findViewById(R.id.homeViewpager)
        bottomNavigationView = view.findViewById(R.id.homeBottomNavigationView)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //配置viewpager，一共两页，主页和菜单页
        viewPager?.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0 -> ShowContentFragment()
                    1 -> MenuFragment()
                    else -> MenuFragment()
                }
            }
        }
    }
}