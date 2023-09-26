package com.reconosersdk.reconosersdk.libs

interface EventBus {

    abstract fun register(subscriber: Any)
    abstract fun unregister(subscriber: Any)
    abstract fun post(event: Any)
}