package cn.idealismxxm.onlinejudge.web.handler;

import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.AjaxResult;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 业务异常处理
 *
 * @author idealism
 * @date 2018/3/27
 */
@Component
public class BusinessExceptionHandler implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        // 1. 打印日志
        LOGGER.error(e.getMessage(), e);

        // 2. 生成错误结果封装
        AjaxResult<Integer> result;
        if (e instanceof BusinessException) {
            result = new AjaxResult<>(e.getMessage());
        } // 非业务异常均视为未知错误
        else {
            result = new AjaxResult<>(ErrorCodeEnum.UNKNOWN.getMsg());
        }

        String header = request.getHeader("X-Requested-With");
        // 3. 处理 ajax 请求
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter printWriter = null;
            try {
                printWriter = response.getWriter();
                printWriter.write(JsonUtil.objectToJson(result));
                printWriter.flush();
                printWriter.close();
            } catch (Exception ex) {
                LOGGER.error("#resolveException error, result: {}", result, ex);
            } finally {
                if (printWriter != null) {
                    printWriter.close();
                }
            }
            return null;
        }

        // 4. 处理页面请求
        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("msg", result.getMsg());
        return mav;
    }
}
