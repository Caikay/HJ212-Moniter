package com.github.guocay.hj212.business.core.aop;

import com.github.guocay.hj212.business.core.annotaion.MonitorServiceListen;
import com.github.guocay.hj212.business.core.annotaion.TaskMethod;
import com.github.guocay.hj212.business.mapper.MonitorErrorMapping;
import com.github.guocay.hj212.business.mapper.MonitorInfoMapper;
import com.github.guocay.hj212.business.po.MonitorErrorPo;
import com.github.guocay.hj212.business.po.MonitorInfoPo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Aspect
@Component
public class MonitorServiceAop {

	private static final Logger log = LoggerFactory.getLogger(MonitorServiceAop.class);

	private final MonitorErrorMapping errorMapping;

	private final MonitorInfoMapper infoMapper;

	public MonitorServiceAop(MonitorErrorMapping errorMapping, MonitorInfoMapper infoMapper) {
		this.errorMapping = errorMapping;
		this.infoMapper = infoMapper;
	}

	@Around("@annotation(method)")
	public Object serviceBefore(ProceedingJoinPoint point, MonitorServiceListen method) {
		Object obj;
		Object[] args = point.getArgs();

		log.info("\nSystem Message ==> Monitor Data Is Processing,Request Data Is: {}", args[0]);

		LocalDateTime time = LocalDateTime.now(Clock.systemDefaultZone());
		MonitorInfoPo infoPo = MonitorInfoPo.builder().info((String) args[0]).time(time).IPAddress((String) args[1]).build();
		infoMapper.insert(infoPo);
		try {
			obj = point.proceed();
		} catch (Throwable ex) {
			//异常情况下,记录异常数据;
			MonitorErrorPo monitorErrorPo = MonitorErrorPo.builder().error(ex.getMessage()).infoId(infoPo.getId()).time(time).build();
			errorMapping.insert(monitorErrorPo);
			log.error(ex.getMessage(), ex);
			throw new RuntimeException(ex);
		}
		log.info("System Message ==> Response Data Is: {}", obj);
		return obj;
	}

	@Around("@annotation(method)")
	public Object loggerTaskAround(ProceedingJoinPoint point, TaskMethod method) {
		Object obj = false;
		log.info("\n---------------定时器[{}({})]执行开始----------------", method.taskName(), method.taskId());
		if (!method.taskSwitch()) {
			log.info("---------------定时器[{}({})]没有执行----------------", method.taskName(), method.taskId());
			return obj;
		}
		try {
			obj = point.proceed();
		} catch (Throwable throwable) {
			log.error(throwable.getMessage(), throwable);
			throw new RuntimeException(throwable);
		}
		log.info("---------------定时器[{}({})]执行结束,Result({})----------------", method.taskName(), method.taskId(), obj);
		return obj;
	}

}
