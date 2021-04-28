package com.tencent.chiris.projectrank.aop;

import com.tencent.chiris.projectrank.annotations.ValidateDate;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class ValidateAspect {

    @Pointcut(value = "execution(* com.tencent.chiris..projectrank.controller.*.*(..,java.util.Date,java.util.Date)) && @annotation(com.tencent.chiris.projectrank.annotations.ValidateDate)")
    public void pointcut(){

    }

//    @Before(value = "pointcut() && @annotation(validateDate) && args(.., beginDate, endDate)", argNames = "joinPoint,validateDate,beginDate,endDate")
//    public Object validate(JoinPoint joinPoint, ValidateDate validateDate, Date beginDate, Date endDate) throws Exception {
//        System.out.println(beginDate);
//        System.out.println(endDate);
//        int earliest = validateDate.earliest();
//        System.out.println(earliest);
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method method = methodSignature.getMethod();
//        String methodname = method.getName();
//        System.out.println(methodname);
//        throw new BadHttpRequest(new RuntimeException());
//    }

    @Around(value = "pointcut() && @annotation(validator)", argNames = "proceedingJoinPoint, validator")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, ValidateDate validator) throws Throwable {

        Object[] argValues = proceedingJoinPoint.getArgs();
        String[] argNames = ((CodeSignature)proceedingJoinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < argNames.length; i++){
            System.out.println(argNames[i] + " : " + argValues[i]);
        }
        return proceedingJoinPoint.proceed();
    }

}
