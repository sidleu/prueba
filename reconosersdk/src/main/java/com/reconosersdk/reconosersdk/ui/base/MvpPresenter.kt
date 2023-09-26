package com.reconosersdk.reconosersdk.ui.base

interface MvpPresenter<V : MvpView> {

    fun onAttach(view: V?)
    fun onDetach()
    fun getView(): V?
}