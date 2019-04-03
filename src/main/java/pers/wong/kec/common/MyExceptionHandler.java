//package pers.wong.kec.common;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
///**
// * @author Wangjunwei
// * @Date 1/24/2019 3:59 PM
// * @Description 异常处理器
// */
//
////ControllerAdvice注解拦截controller层面的异常
//@ControllerAdvice
//@ResponseBody
//public class MyExceptionHandler {
//
//  //ExceptionHandler注解指定处理哪种异常
//  @ExceptionHandler(BusinessException.class)
//  //ResponseStatus指定返回的http状态码
//  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//  public Result handlerMyException(BusinessException e) {
//    return Result.fail(e);
//  }
//
//  @ExceptionHandler(BusinessException.class)
//  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//  public Result handlerMyRuntimeException(Exception e) {
//    return Result.fail(e);
//  }
//}
