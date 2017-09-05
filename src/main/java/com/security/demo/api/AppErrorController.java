package com.security.demo.api;

import com.google.common.collect.Maps;

import com.security.demo.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@ControllerAdvice(annotations = {Controller.class, RestController.class})
@RequestMapping(value = Constants.ERROR_PATH)
public class AppErrorController extends AbstractErrorController {

  public AppErrorController(ErrorAttributes errorAttributes) {
	super(errorAttributes);
  }

  private static Logger logger = LoggerFactory.getLogger(AppErrorController.class);

  @Override
  public String getErrorPath() {
	logger.debug("error path.");
	return "";
  }

  /**
   * 跳转错误页面
   */
  @RequestMapping(produces = "text/html")
  public String error(HttpServletRequest request) {
	Map<String, Object> errorAttributes = getErrorAttributes(request, false);
	logger.debug(">>>>error(text/html): {}", errorAttributes);
	request.setAttribute("errAttrs", errorAttributes);
	return "error";
  }

  /**
   * 以JSON格式返回错误信息
   */
  @RequestMapping
  public ResponseEntity<?> error2(HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> errorAttributes = getErrorAttributes(request, false);
	HttpStatus status = getStatus(request);
	logger.debug(">>>>error(ajax): {}", errorAttributes);
	return new ResponseEntity<>(errorAttributes, status);
  }

  private ResponseEntity<?> getBody(RuntimeException ex, HttpStatus status) {
	logger.error(">>>>Exception: ", ex);
	Map<String, Object> responseMessage = Maps.newHashMap();
	responseMessage.put("message", ex.getMessage());
	responseMessage.put("status", status.value());

	return new ResponseEntity<>(responseMessage, status);
  }

  @ExceptionHandler(value = NullPointerException.class)
  public ResponseEntity<?> npe(NullPointerException npe){
	return getBody(npe, HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
