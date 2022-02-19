package cn.sias.community.ruike.util;


import cn.sias.community.ruike.entity.User;
import org.springframework.stereotype.Component;


/***
 * 持有用户信息，用于代替Session对象
 * threadlocal是一个线程内部的存储类，可以在指定线程内存储数据，数据存储以后，只有指定线程可以得到存储数据.
 * threadLocal提供了线程内存储变量的能力，这些变量不同之处在于每一个线程读取的变量是对应的互相独立的。通过get和set方法就可以得到当前线程对应的值。
 *
 *     ThreadLocal存储数据的过程，get同理
 *    public void set(T value) {
 *         Thread t = Thread.currentThread();
 *         ThreadLocalMap map = getMap(t);
 *         if (map != null)
 *             map.set(this, value);
 *         else
 *             createMap(t, value);
 *     }
 *
 *    ThreadLocal获取存储数据的过程，get同理
 *    Thread t = Thread.currentThread();
 *         ThreadLocalMap map = getMap(t);
 *         if (map != null) {
 *             ThreadLocalMap.Entry e = map.getEntry(this);
 *             if (e != null) {
 *                 @SuppressWarnings("unchecked")
 *                 T result = (T)e.value;
 *                 return result;
 *             }
 *         }
 *         return setInitialValue();
 */
@Component
public class HostHolder {

    private final ThreadLocal<User> threadLocal= new ThreadLocal<>();

    public void setUsers(User user){
        threadLocal.set(user);
    }
    public User getUser(){
        return threadLocal.get();
    }

    //清理当前线程里存储的值
    public void clear(){
        threadLocal.remove();
    }
}
