package ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//����������1
public class HandlerInterceptor1 implements HandlerInterceptor{

	//����Handler����֮ǰִ��
	//�������������֤�������Ȩ�������֤û��ͨ����ʾ�û�û�е�½����Ҫ�˷������ز�������ִ�У�����ͷ���
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		System.out.println("HandlerInterceptor1....preHandle");
		
		//false��ʾ���أ�������ִ�У�true��ʾ����
		return true;
	}

	//����Handler����֮�󣬷���ModelAndView֮ǰִ��
	//Ӧ�ó�����modelAndView�����������õ�ģ�����ݣ�����˵�����֮��ģ������ﴫ����ͼ��Ҳ����������ͬһָ����ͼ
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		System.out.println("HandlerInterceptor1....postHandle");
		
	}

	//ִ��Handler���֮��ִ��
	//Ӧ�ó�����ͳһ�쳣����ͳһ��־����
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		System.out.println("HandlerInterceptor1....afterCompletion");
	}

}
