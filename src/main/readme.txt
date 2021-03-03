自定义springmvc

    1、在传统的servlet中，需要继承HttpServlet之后，才具备处理动态资源的能力。接受来自客户端的请求
       但是在自己写的一个类中，实现了原始的servlet的五个方法，只有两个方法是我们真正常用的。doGet/doPost方法


       第一次改进方法：
            在web/controller包下的类中，继承了HttpServlet之外，重写了doGet/doPost方法外，还可以写上增删改查方法
            在前端发送过来的参数中，需要带个后缀action，参数名称是方法名称.do，在访问的时候截取掉，然后将方法名称保留下来。
            在doGet/doPost方法中利用equals方法来进行比对，判断请求的是哪个方法。然后就调用controller中的哪个方法。
            那么意味着类中有多少方法，就会写多少层if...else来进行判断。这种判断语句来进行执行，麻烦。

       第二种改进方法：
            利用反射思想来进行改进
            既然通过了类上的@WebServlet(path="/xxx.do")，那么说明了请求的是这个类里面的方法，将请求路径中的后缀去掉之后，xxx，
            那么这里的xxx代表就是方法名称。那么利用反射来进行调用。
            首先获取得到字节码文件对象，创建对象，获取得到方法对象。方法对象.invoke(this,HttpRequest.class,HttpResponse.class);
            来进行调用

    2、自定义springmvc的思想

        思想一
            在一个web中，有很多的sevlet类，比较这么多的servlet。发现servlet之间有共性，根据java三大特性的继承，应该进行共性抽取
            将doGet/doPost抽取到BaseServlet中去，BaseServlet去实现HttpServlet类，让其他的servlet来继承BaseServlet
            那么在servlet可以写增删改查方法了，需要注意的是增删改查方法中需要传递两个参数HttpRequets和HttpResponse对象。
            有了这些方法最终还是会在doGet/doPost方法中进行执行。利用上面的第二步改进方法来得到方法对象，在doGet/doPost方法中反射执行。
           缺点：
                1、虽然抽取了共性的代码，但是耦合性增强了。
                2、每个方法上都得写上两个必须的参数
                3、每一次请求，都需要请求到BaseServlet中去来进行路径解析和方法调用，重复性太高，影响到了性能。
        思想二：
            希望controller/web层的类不去继承任何类，一个普通的java类来实现servlet的功能。
            不希望核心控制器根据路径跟方法名称来进行匹配，用反射来进行执行

            编写一个DispatcherServlet类来继承HttpServlet类，利用自定义注解的方式来进行执行。
            在DispatcherServlet类中定义，拦截一切从客户端发送过来的.do的方法，然后在初始化方法中扫描类和方法上的注解
            如果发现都有，那么添加到cache中去。这是最终的实现方法。

            思路：
                初始：在类上和方法上加入注解，每次请求都从DispatcherServlet上经过，每次都进行扫描浪费时间
                改进：利用缓存的思想，首先判断了类上和方法上的注解之后，这个时候就需要调用方法了。
                     对象调用四大步骤：类对象、方法名称、参数、方法返回值
                     反射调用：方法对象、类对象、参数的字节码对象。
                        在这个项目中，方法对象可以根据方法名称来获取，类对象可以由字节码对象得到，参数传递进来的是HttpRequest/Response对象

                     但是利用缓存思想，需要考虑到一个问题。map中两个参数如果来写。
                     我利用的是路径来作为key，因为每个类的uri是不同的。value需要保存到两个对象，字节码对象和方法对象，所以另外创建了一个类。







