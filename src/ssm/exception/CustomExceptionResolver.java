package ssm.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CustomExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		ex.printStackTrace();
		CustomException customException = null;
		
		//����׳�����ϵͳ�Զ�����쳣��ֱ��ת��
		if(ex instanceof CustomException) {
			customException = (CustomException) ex;
		} else {
			//����׳��Ĳ���ϵͳ�Զ�����쳣�����¹���һ��δ֪�����쳣
			customException = new CustomException("ϵͳδ֪����");
		}

		//��ǰ̨���ش�����Ϣ
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", customException.getMessage());
		modelAndView.setViewName("/WEB-INF/jsp/error.jsp");
		
		return modelAndView;
	}

}
