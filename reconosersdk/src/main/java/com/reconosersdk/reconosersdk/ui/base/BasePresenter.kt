package com.reconosersdk.reconosersdk.ui.base

abstract class BasePresenter<V : MvpView>() : MvpPresenter<V> {
    var mvpView: V? = null
    private val isViewAttached: Boolean get() = mvpView != null

    override fun onAttach(view: V?) {
        this.mvpView = view
    }

    override fun getView(): V? = mvpView

    override fun onDetach() {
        mvpView = null
    }
}