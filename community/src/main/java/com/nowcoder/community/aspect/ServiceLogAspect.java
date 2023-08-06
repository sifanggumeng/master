package com.nowcoder.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    //返回值 包名.所有的业务组件.所有的方法(所有的返回值)
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut() {
    }

    //有接口情况，使用JDK动态代理；创建接口实现类代理对象，增强类的方法
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {   //连接点：代码织入的部位。提供接口实现动态代理（调用代理执行目标组件方法）
        // 用户[1.2.3.4],在[xxx],访问了[com.nowcoder.community.service.xxx()].
        //从request当中获取ip地址
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //除了controller，eventConsumer也会调用service，此时不会产生request对象，故会产生空指针异常NullPointerException
        if (attributes==null){
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //调用目标组件的方法
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        logger.info(String.format("用户[%s],在[%s],访问了[%s].", ip, now, target));

    }
}
