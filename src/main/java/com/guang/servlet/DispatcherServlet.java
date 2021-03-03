package com.guang.servlet;

import com.guang.annotation.Controller;
import com.guang.annotation.RequestMapping;
import com.guang.bean.MvcMethod;
import com.guang.utils.ClassScannerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

    private Map<String, MvcMethod> cache = new HashMap<>();

    // 在初始化方法中来进行扫描，并将类和方法上的注解添加进来
    @Override
    public void init() throws ServletException {
        try {
            // 扫描包下的所有的class文件
            List<Class<?>> classList = ClassScannerUtils.getClasssFromPackage("com.guang.controller");
            // 扫描遍历字节码文件
            for (Class<?> clazz : classList) {
                // 看类上是否有controller注解
                boolean annotationPresentClass = clazz.isAnnotationPresent(Controller.class);
                // 如果有这样的注解，那么可以将其加载到内存到中
                if (annotationPresentClass){
                    // 创建出来一个对象
                    Object obj = clazz.newInstance();
                    // 调用对应方法
                    Method[] methods = clazz.getMethods();
                    // 遍历方法，看方法上是否有对应的注解
                    for (Method method : methods) {
                        boolean annotationPresentMethod = method.isAnnotationPresent(RequestMapping.class);
                        if (annotationPresentMethod){
                            // 获取得到注解的值
                            String urlValue = method.getAnnotation(RequestMapping.class).value();
                            // 创建对象，将注解值映射的放到map集合中去
                            MvcMethod mvcMethod = new MvcMethod(method,obj);
                            cache.put(urlValue,mvcMethod);
                        }
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 获取得到请求的URI
            String requestURI = req.getRequestURI();
            // 获取得到contextPath
            String contextPath = req.getContextPath();
            String requestUrl = requestURI.substring(contextPath.length(), requestURI.lastIndexOf(".do"));
           //  System.out.println(requestUrl);
            //从缓存中获取得到对应的值来进行执行
            MvcMethod mvcMethod = cache.get(requestUrl);
            // 调用方法来进行执行。
            mvcMethod.getMethod().invoke(mvcMethod.getObj(),req,resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
