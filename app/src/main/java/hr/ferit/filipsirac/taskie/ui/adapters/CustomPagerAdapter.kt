package hr.ferit.brunozoric.taskie.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CustomPagerAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {

    var mFragmentItems: ArrayList<Fragment> = arrayListOf()
    var mFragmentTitle: ArrayList<String> = arrayListOf()

    fun addFragment(fragmentItem: Fragment, fragmentTitle: String){
        mFragmentItems.add(fragmentItem)
        mFragmentTitle.add(fragmentTitle)
    }
    override fun getItem(position: Int): Fragment = mFragmentItems[position]
    override fun getCount(): Int = mFragmentItems.size

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitle[position]
    }


}