package com.example.xmbus.bus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XmBus {
    private static XmBus xmBus = new XmBus();

    private Map<Object, List<MethodParams>> map;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private XmBus(){
        map = new HashMap<>();
    }

    public static XmBus getInstance()
    {
        return xmBus;
    }

    public void regist(Object obj)
    {
        List<MethodParams> methods = map.get(obj);
        if (methods == null)
        {
            methods = findMethodByKey(obj);
            map.put(obj,methods);
        }
    }

    private List<MethodParams> findMethodByKey(Object obj) {
        List<MethodParams> methods = new ArrayList<>();
        Class<?> aClass = obj.getClass();

        //获取所有方法
        Method[] declaredMethods = aClass.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods)
        {
            Subscribe subscribe = declaredMethod.getAnnotation(Subscribe.class);
            if (subscribe == null)
            {
                continue;
            }

            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
            if (parameterTypes == null || parameterTypes.length != 1)
            {
                continue;
            }
            MethodParams methodParams = new MethodParams(declaredMethod,parameterTypes[0],subscribe.value());

            methods.add(methodParams);
        }

        return methods;
     }

     public void post(final Object params)
     {
         Set<Object> objects = map.keySet();

         for(final Object obj:objects)
         {
             List<MethodParams> methods = map.get(obj);

             for(final MethodParams method:methods)
             {
                 Class<?> type = method.getType();

                 if (type.isAssignableFrom(params.getClass()))
                 {
                     ThreadMode threadMode = method.getThreadMode();
                     switch (threadMode)
                     {
                         case BACKGROUND:
                             if (Looper.getMainLooper() == Looper.myLooper())
                             {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(method.getMethod(),params,obj,method.getThreadMode());
                                    }
                                }).start();
                             }
                             else
                             {
                                 invoke(method.getMethod(),params,obj,method.getThreadMode());
                             }
                             break;
                         case MAIN:
                             if (Looper.getMainLooper() == Looper.myLooper())
                             {
                                 invoke(method.getMethod(),params,obj,method.getThreadMode());
                             }
                             else
                             {
                                 mHandler.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         invoke(method.getMethod(),params,obj,method.getThreadMode());
                                     }
                                 });
                             }

                             break;
                         case POSITION:
                             invoke(method.getMethod(),params,obj,method.getThreadMode());
                             break;
                     }

                 }
             }
         }
     }

    private void invoke(Method method, Object params, Object obj,ThreadMode threadMode) {
        try {
            method.invoke(obj,params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
