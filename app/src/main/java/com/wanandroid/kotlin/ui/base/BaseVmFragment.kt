package com.wanandroid.kotlin.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.wanandroid.kotlin.MyApp
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseVmFragment<VM : ViewModel, B : ViewBinding> : Fragment() {

    lateinit var viewModel: VM
    private var _binding: B? = null
    val binding get() = _binding!!

    protected val TAG = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, setVmFactory())[getViewModelClass()]

        val method = getViewBindingClass().getDeclaredMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        _binding = method.invoke(null, layoutInflater, container, false) as B
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initView()
        initObserve()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }

    private fun getViewBindingClass(): Class<B> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1]
        return type as Class<B>
    }

    abstract fun setVmFactory(): ViewModelProvider.Factory

    abstract fun initView()

    open fun initData() {}

    open fun initObserve() {}


    fun showToast(msgContent: String?) {
        Toast.makeText(MyApp.context, msgContent, Toast.LENGTH_SHORT).show()
    }

}