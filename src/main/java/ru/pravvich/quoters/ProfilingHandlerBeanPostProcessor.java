package ru.pravvich.quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Ravvich.
 */
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();

    private ProfilingController controller = new ProfilingController();

    public ProfilingHandlerBeanPostProcessor() throws JMException {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (controller.isEnabled()) {
                        System.out.println("Start profiling");
                        long after = System.nanoTime();
                        Object receivedVal = method.invoke(bean, args);
                        long before = System.nanoTime();
                        System.out.println(before - after);
                        System.out.println("End profiling");
                        return receivedVal;
                    } else {
                        return method.invoke(bean, args);
                    }
                }
            });
        }
        return bean;
    }
}
