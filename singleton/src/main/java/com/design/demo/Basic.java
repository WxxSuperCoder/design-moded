package com.design.demo;

import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIB;

/**
 * Description: 基本实现
 *
 * @author Administrator
 * @date 2022/7/10 15:47
 */
public class Basic {

    //私有静态实例，防止被实例化，null目的是实现延迟加载
    private static Basic instance = null;

    //要私有化，防止被实例化
    private Basic() {
    }

    //实例化对象
/*    private static Basic getInstance() {
        if (null == instance) {
            instance = new Basic();
        }
        return instance;
    }*/

    //实例化对象-线程安全的
/*    private static synchronized Basic getInstance() {
        if (null == instance) {
            instance = new Basic();
        }
        return instance;
    }*/

/*    实例化对象-线程安全的-改进
    出现的问题:
        假如A和B同时进入第一个if判断,A先进入synchronized块,此时instance为null,执行赋值操作;
    由于JVM内部优化机制,先划出区域给Basic实例的空白内存,并赋值给instantce,此时并没有实例化;
    B进入synchronized块,instance此时不是null,B离开并将结果返回给调用该方法的程序,此时B打算使
    用Basic,但是并没有实例化,报错.*/
    /*private static synchronized Basic getInstance() {
        if (null == instance) {
            synchronized (instance) {
                if (null == instance) {
                    instance = new Basic();
                }
            }
        }
        return instance;
    }*/

    /*使用内部类维护单例，防止上面的情况发生
    JVM内部机制保证当一个类被加载的时候,类的加载过程是线程互斥的*/
    private static class SingletonFactory {
        private static Basic instance = new Basic();
    }

    //获取实例
    public static Basic getInstance() {
        return SingletonFactory.instance;
    }

    //如果对象被用于序列化对象，可以保证对象在序列化前后保持一致
    public Object readResolve() {
        return instance;
    }

}
