/* NAM NV created on 22:10 12-4-2023 */
package com.example.englishttcm.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.englishttcm.OnActionCallback
import com.example.englishttcm.R

abstract class BaseActivity<VM: ViewBinding> : AppCompatActivity(), OnActionCallback{

    protected lateinit var binding: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getLayout()
        setContentView(binding.root)
        initViews()
    }

    abstract fun initViews()

    abstract fun getLayout(): VM

    override fun showFragment(
        fromFragment: Class<*>,
        toFragment: Class<*>,
        data: Any?,
        isBack: Boolean
    ) {
        val clazz = Class.forName(toFragment.name)
        val fragment = clazz.getConstructor().newInstance() as BaseFragment<*>
        fragment.callback = this
        fragment.data = data
        val trans = supportFragmentManager.beginTransaction()
        if(isBack){
            trans.addToBackStack(null)
        }
        trans.replace(R.id.fr_main, fragment, toFragment.name)
        trans.commit()
    }

    override fun backToPrevious() {
        onBackPressed()
    }

    fun notify(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}