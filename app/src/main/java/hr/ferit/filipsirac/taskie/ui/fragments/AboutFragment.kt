package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.adapters.CustomPagerAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : BaseFragment() {

     private lateinit var pagerAdapter: CustomPagerAdapter


    override fun getLayoutResourceId() = R.layout.fragment_about

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onResume() {
        super.onResume()
        this.setHasOptionsMenu(false)
    }

    private fun initUi() {
        setUpAdapter()
        customViewPager.adapter = pagerAdapter
        customTabLayout.setupWithViewPager(customViewPager)
    }

    private fun setUpAdapter() {
        pagerAdapter = CustomPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(AboutAuthorFragment(), "AboutAuthor")
        pagerAdapter.addFragment(AboutAppFragment(), "AboutApp")
    }


    companion object {
        fun newInstance(): Fragment {
            return AboutFragment()
        }
    }

}