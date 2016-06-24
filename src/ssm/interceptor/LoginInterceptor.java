package ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//����������1
public class LoginInterceptor implements HandlerInterceptor{

	//����Handler����֮ǰִ��
	//�������������֤�������Ȩ�������֤û��ͨ����ʾ�û�û�е�½����Ҫ�˷������ز�������ִ�У�����ͷ���
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//��ȡ�����url
		String url = request.getRequestURI();
		//�ж�url�Ƿ񹫿���ַ��ʵ��ʹ��ʱ��������ַ���õ������ļ��У�
		//���﹫����ַ�Ƿ��½�ύ�ĵ�ַ
		if(url.indexOf("login.action") > 0) {
			//������е�½�ύ������
			return true;
		}
		
		//�ж�session
		HttpSession session = request.getSession();
		//��session��ȡ���û������Ϣ
		String username = (String) session.getAttribute("username");
		if(username != null) {
			return true;
		}
		
		
		//ִ�е������ʾ�û������Ҫ��֤����ת����½ҳ��
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		
		return false;
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
