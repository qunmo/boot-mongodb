package sjz.sgy.lb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import sjz.sgy.lb.util.R;


@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
	}
	/**
	 * 处理自定义异常
	@ */
	@ExceptionHandler(RRException.class)
	public sjz.sgy.lb.util.R handleRRException(RRException e){
		logger.error(e.getMessage(), e);
		return R.error(e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error(e.getMessage());
	}

	
}
