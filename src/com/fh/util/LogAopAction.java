package com.fh.util;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fh.entity.system.User;
import com.fh.service.system.operLog.OperLogService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.sun.org.apache.bcel.internal.generic.NEW;
/** 
 *  
 * @author zandezhang
 * @since 2017.01.17
 */  
@Aspect
public class LogAopAction {
    //注入service,用来将日志信息保存在数据库
    @Resource(name="operLogService")
    private OperLogService operLogService;
    
     //配置接入点,如果不知道怎么配置,可以百度一下规则
     @Pointcut("execution(* com.fh.controller..*.*(..))")  
     private void controllerAspect(){}//定义一个切入点
 
     @Around("controllerAspect()")
     public Object around(ProceedingJoinPoint pjp) throws Throwable {
    	 HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
    			 .getRequestAttributes()).getRequest();  
         //常见日志实体对象
         //LogEntity log = new LogEntity(); 
         PageData lo = new PageData();
         //获取系统时间
         //String time = DateUtil.getTime();
         //log.setDATA(time);
         lo.put("OPER_DATE", new Date());
         

         lo.put("IP_ADDR", HttpRequest.getIpAddr(request));
        //方法通知前获取时间,为什么要记录这个时间呢？当然是用来计算模块执行时间的
         long start = System.currentTimeMillis();
        // 拦截的实体类，就是当前正在执行的controller
        Object target = pjp.getTarget();
        // 拦截的方法名称。当前正在执行的方法
        String methodName = pjp.getSignature().getName();
        // 拦截的方法参数
        Object[] args = pjp.getArgs();
        // 拦截的放参数类型
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();
        
        Object object = null;
        // 获得被拦截的方法
        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        }
        if (null != method) {
            // 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
            if (method.isAnnotationPresent(SystemLog.class)) {
                SystemLog systemlog = method.getAnnotation(SystemLog.class);
                
                //String type = systemlog.type();
                //获取登录用户账户
                Subject currentUser = SecurityUtils.getSubject();
                Session session = currentUser.getSession();
               // String username = "";
                
        		User user = (User) session.getAttribute("sessionUser");
                
                /*if(type.equals("后台")){
                    User user = (User) session.getAttribute("sessionUser");
                    username = user.getUSERNAME();
                }else if(type.equals("前台")){
                    PageData userPd = (PageData) session.getAttribute("loginUser");
                    username = userPd.getString("username");
                }*/
                
                //log.setUSERID(user.getUSERNAME());
                lo.put("USER_ID", user.getID());
                
                
                //log.setMODULE(systemlog.module());
                //lo.put("OPER_TYPE", systemlog.module());
                //log.setMETHOD(systemlog.methods());
                lo.put("OPER_OBJECT", systemlog.methods());
                //log.setTYPE(systemlog.type());
                lo.put("OPER_TYPE", systemlog.type());
                try {
                    object = pjp.proceed();
                    long end = System.currentTimeMillis();
                    //将计算好的时间保存在实体中
                    //log.setRSPONSE_DATE(""+(end-start));
                    lo.put("rsponse_date", ""+(end-start));
                    //log.setCOMMTIE("执行成功！");
                    lo.put("commtie", "成功！");
                    lo.put("ID", UuidUtil.get32UUID());
                    //保存进数据库
                    operLogService.saveOperLog(lo);
                } catch (Throwable e) {
                    long end = System.currentTimeMillis();
                    //log.setRSPONSE_DATE(""+(end-start));
                    lo.put("rsponse_date", ""+(end-start));
                    //log.setCOMMTIE("执行失败");
                    lo.put("commtie", "失败！");
                    lo.put("ID", UuidUtil.get32UUID());
                    lo.put("exce", e.toString());
                    operLogService.saveOperLog(lo);
                   
                }
            } else {//没有包含注解
                object = pjp.proceed();
            }
        } else { //不需要拦截直接执行
            object = pjp.proceed();
        }
        return object;
     }
}