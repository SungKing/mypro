package cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * @Description
 * @Author song.wang
 * @Date 2021-04-01
 */
public class Test {
    public static void main(String[] args) {
        //把CGLIB生成的字节码文件保存到本地D:\cglib，到时候可以拖进idea里看
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/song.wang/java/pro/mypro/mypro/src/test/resources");
        MethodInterceptor m1 = new MethodInterceptor() {

            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("拦截器1 before");
                methodProxy.invokeSuper(o, objects);
                System.out.println("拦截器1 after");
                return null;
            }
        };
        MethodInterceptor m2 = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("拦截器2 before");
                methodProxy.invokeSuper(o, objects);
                System.out.println("拦截器2 after");
                return null;
            }
        };
        //返回索引对应Callback数组里的拦截器索引
        CallbackFilter callbackFilter = method -> {
            if (method.getName().equals("f1"))
                return 0;
            else if (method.getName().equals("f2"))
                return 1;
            return 2;
        };

        CglibObj cglibObj1 = (CglibObj) Enhancer.create(CglibObj.class, null, callbackFilter, new Callback[]{m1, m2, NoOp.INSTANCE});
        cglibObj1.f1();
        cglibObj1.f1();
        cglibObj1.f2();
        CglibObj cglibObj2 = (CglibObj) Enhancer.create(CglibObj.class, null, callbackFilter, new Callback[]{m1, m2, NoOp.INSTANCE});
        cglibObj2.f1();
        cglibObj2.f2();
        System.out.println(cglibObj2.toString());;
    }

}
