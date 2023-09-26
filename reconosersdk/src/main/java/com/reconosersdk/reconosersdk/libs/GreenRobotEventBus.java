package com.reconosersdk.reconosersdk.libs;

public class GreenRobotEventBus implements EventBus {
    private org.greenrobot.eventbus.EventBus eventBus;

    private static class SingletonHolder {
        private static final GreenRobotEventBus INSTANCE = new GreenRobotEventBus();
    }

    public static GreenRobotEventBus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public GreenRobotEventBus() {
        eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }


    @Override
    public void register(Object subscriber) {
        if(!eventBus.isRegistered(subscriber)){
            eventBus.register(subscriber);
        }
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}