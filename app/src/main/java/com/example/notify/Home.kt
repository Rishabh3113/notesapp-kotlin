package com.example.notify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class Home : AppCompatActivity() {

    //  lateinit var note:TabItem
    //lateinit var profile:TabItem
     lateinit var tabLayout: TabLayout
     lateinit var viewPager: ViewPager
     lateinit var pagerAdapter:PagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
// we dont need to intialiize tab items in kotlin but in java we do
       // profile=findViewById(R.id.profile)
      //  note=findViewById(R.id.notes)
        tabLayout=findViewById(R.id.tab)
        viewPager=findViewById(R.id.pager)
        pagerAdapter = PagerAdapter(supportFragmentManager,2 )
        viewPager.adapter=pagerAdapter

       // tabLayout.setupWithViewPager(viewPager)

        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager.currentItem = it.position
                    if (it.position == 0 || it.position == 1) {
                        pagerAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
                viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

}