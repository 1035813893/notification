package com.notification;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.notification.NotificationListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationCenter {

    final private String Tag = "NotificationCenter";

    private HashMap<String, List<WeakReference<NotificationListener>>> mListenersMap;

    public static NotificationCenter defaultCenter = new NotificationCenter();

    private int indexOf(NotificationListener listener, List<WeakReference<NotificationListener>> listeners) {
        for (int i = 0; i < listeners.size(); i++) {
            WeakReference<NotificationListener> item = listeners.get(i);
            if (item.get() == listener)
                return i;
        }
        return -1;
    }

    public synchronized void addListener(String notificationKey,
                                         NotificationListener listener) {

        if (notificationKey == null || notificationKey.isEmpty()
                || listener == null) {
           Log.w(Tag, "addListener notificationKey == null || notificationKey.isEmpty() || listener == null");
            return;
        }

        List<WeakReference<NotificationListener>> listeners = mListenersMap
                .get(notificationKey);
        if (listeners == null) {
            listeners = new ArrayList<>();
            mListenersMap.put(notificationKey, listeners);
        }

        int index = indexOf(listener, listeners);
        if (-1 == index) {
            listeners.add(new WeakReference<>(listener));
        }
    }

    public synchronized void removeListener(String notificationKey,
                                            NotificationListener listener) {

        if (notificationKey == null || notificationKey.isEmpty()
                || listener == null) {
           Log.w(Tag,
                    "removeListener notificationKey == null || notificationKey.isEmpty() || listener == null");
            return;
        }

        List<WeakReference<NotificationListener>> listeners = mListenersMap
                .get(notificationKey);

        int index = indexOf(listener, listeners);

        if (index != -1)
            listeners.remove(index);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public synchronized void removeListener(NotificationListener listener) {

        if (listener == null) {
           Log.w(Tag, "removeListener listener == null");
            return;
        }

        for (Object o : mListenersMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            List<WeakReference<NotificationListener>> listeners = (List<WeakReference<NotificationListener>>) entry
                    .getValue();

            int index = indexOf(listener, listeners);

            if (index != -1)
                listeners.remove(index);
        }
    }

    private synchronized void postNotification(String notificationKey, NotificationListener.Notification notification) {

        if (notificationKey == null || notificationKey.isEmpty()) {
            Log.w(Tag, "removeListener notificationKey == null || notificationKey.isEmpty()");
            return;
        }

        if (notification == null)
            notification = new NotificationListener.Notification();

        notification.key = notificationKey;
        this.runOnUiThread(new PostEventRunnable(notificationKey, notification));
    }

    public void postNotification(String notificationKey) {
        this.postNotification(notificationKey, null);
    }

    public void postNotification(String notificationKey, Object object) {
        NotificationListener.Notification notification = new NotificationListener.Notification();
        notification.object = object;
        this.postNotification(notificationKey, notification);
    }

    public void postNotification(String notificationKey, Object object,
                                 Object object1) {
        NotificationListener.Notification notification = new NotificationListener.Notification();
        notification.object = object;
        notification.object1 = object1;
        this.postNotification(notificationKey, notification);
    }
    public void postNotification(String notificationKey, Object object,
                                 Object object1,Object object2) {
        NotificationListener.Notification notification = new NotificationListener.Notification();
        notification.object = object;
        notification.object1 = object1;
        notification.object2 = object2;
        this.postNotification(notificationKey, notification);
    }
    public void postNotification(String notificationKey, Object object,
                                 Object object1,Object object2,Object object3) {
        NotificationListener.Notification notification = new NotificationListener.Notification();
        notification.object = object;
        notification.object1 = object1;
        notification.object2 = object2;
        notification.object3 = object3;
        this.postNotification(notificationKey, notification);
    }

    public void postNotification(String notificationKey, Object object, int arg1) {
        NotificationListener.Notification notification = new NotificationListener.Notification();
        notification.object = object;
        notification.arg1 = arg1;
        this.postNotification(notificationKey, notification);
    }

    public void postNotification(String notificationKey, Object object,
                                 int arg1, int arg2) {
        NotificationListener.Notification notification = new NotificationListener.Notification();
        notification.object = object;
        notification.arg1 = arg1;
        notification.arg2 = arg2;
        this.postNotification(notificationKey, notification);
    }

    public void postNotification(String notificationKey, int arg, int arg1,
                                 int arg2) {
        NotificationListener.Notification notification = new NotificationListener.Notification();

        notification.arg = arg;
        notification.arg1 = arg1;
        notification.arg2 = arg2;
        this.postNotification(notificationKey, notification);
    }

    public void postNotification(String notificationKey, int arg1) {
        NotificationListener.Notification notification = new NotificationListener.Notification();
        notification.object = null;
        notification.arg1 = arg1;
        this.postNotification(notificationKey, notification);
    }

    public void postNotification(String notificationKey, int arg1, int arg2) {
        NotificationListener.Notification notification = new NotificationListener.Notification();
        notification.object = null;
        notification.arg1 = arg1;
        notification.arg2 = arg2;
        this.postNotification(notificationKey, notification);
    }

    private NotificationCenter() {
        mListenersMap = new HashMap<>();
    }

    private void runOnUiThread(Runnable r) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        boolean success = handler.post(r);
        // a hack
        while (!success) {
            handler = null;
            runOnUiThread(r);
        }
    }

    private Handler handler;

    private class PostEventRunnable implements Runnable {

        private final String notificationKey;
        private final NotificationListener.Notification notification;

        public PostEventRunnable(String notificationKey, NotificationListener.Notification notification) {
            this.notificationKey = notificationKey;
            this.notification = notification;
        }

        @Override
        public void run() {
            List<WeakReference<NotificationListener>> listeners = mListenersMap.get(notificationKey);
            if (listeners != null) {
                ArrayList<WeakReference<NotificationListener>> list = new ArrayList<>(listeners);
                for (int i = 0; i < list.size(); i++) {
                    NotificationListener listener = list.get(i).get();

                    if (listener != null) {
                        listener.onNotification(notification);
                    }
                }
            }
        }
    }
}
