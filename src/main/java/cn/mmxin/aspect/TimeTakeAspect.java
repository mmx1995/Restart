package cn.mmxin.aspect;

import cn.mmxin.pojo.ActionRecord;
import cn.mmxin.pojo.mapper.ActionRecordMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Component
@Aspect
public class TimeTakeAspect {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    private Logger logger = LoggerFactory.getLogger(TimeTakeAspect.class);
    private ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final String POINT_CUT = "execution(public * cn.mmxin.controller.*.*(..))";

    @Pointcut(POINT_CUT)
    public void pointCut(){}

    @Around(value = "pointCut()")
    public Object before(ProceedingJoinPoint proceedingJoinPoint){
        ActionRecord actionRecord = new ActionRecord();
        //获取请求数据
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //请求的地址
        actionRecord.setRequest_url(request.getRequestURL().toString());
        //请求的ip
        actionRecord.setIp(request.getRemoteAddr());
        //请求的方式
        actionRecord.setMethod(request.getMethod());
        //获取目标方法参数信息
        Object[] args = proceedingJoinPoint.getArgs();
        StringBuffer argsString = new StringBuffer();
        Arrays.stream(args).forEach(arg->{
            try {
                argsString.append(OBJECT_MAPPER.writeValueAsString(arg) + ",");
            } catch (JsonProcessingException e) {
                logger.info(arg.toString());
            }
        });
        //设置请求参数
        actionRecord.setRequest(argsString.toString());
        Object response = null;
        Date before = new Date();
        try {
            response = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (response != null){
            actionRecord.setResponse(response.toString());
        }
        actionRecord.setTime_take(new Date().getTime() - before.getTime());
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ActionRecordMapper mapper = sqlSession.getMapper(ActionRecordMapper.class);
        mapper.insert(actionRecord);
        sqlSession.close();
        return response;
    }
}
