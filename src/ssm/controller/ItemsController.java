package ssm.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ssm.controller.validation.ValidGroup1;
import ssm.exception.CustomException;
import ssm.po.ItemsCustom;
import ssm.po.ItemsQueryVo;
import ssm.service.ItemsService;

@Controller
public class ItemsController {

	@Autowired
	private ItemsService itemsService;

	@RequestMapping("/queryItems")
	public ModelAndView queryItems() throws Exception {

		// ����service�������ݿ⣬��ѯ��Ʒ�б�
		List<ItemsCustom> itemsList = itemsService.findItemsList(null);

		// ����ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsList", itemsList);
		modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");

		return modelAndView;
	}

	@RequestMapping("/editItems")
	public String editItems(Model model,
			@RequestParam(value = "id", required = true) Integer items_id)
			throws Exception {
		// ����id��ѯ��Ӧ��Items
		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);

		if (itemsCustom == null) {
			throw new CustomException("�޸ĵ���Ʒ��Ϣ�����ڣ�");
		}

		model.addAttribute("itemsCustom", itemsCustom);

		return "/WEB-INF/jsp/items/editItems.jsp";
	}

	@RequestMapping("/editItemsSubmit")
	public String editItemsSubmit(Model model, HttpServletRequest request,
			Integer id,
			@Validated(value = { ValidGroup1.class }) ItemsCustom itemsCustom,
			BindingResult bindingResult, 
			@RequestParam MultipartFile[] items_pic)
			throws Exception {

		// ��ȡУ�������Ϣ
		if (bindingResult.hasErrors()) {
			// ���������Ϣ
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				// System.out.println(objectError.getDefaultMessage());
				// ԭ����������䣬��������properties�ļ�Ĭ���޷��������ģ������Ұ�properties�ļ��ĳ���utf-8���룬
				// ���������Ļ���ȡ�������������ˣ�����������iso���ң�����utf-8���ɣ����ɽ����������
				System.out.println(new String(objectError.getDefaultMessage()
						.getBytes("ISO-8859-1"), "UTF-8"));
			}
			// ��������Ϣ����ҳ��
			model.addAttribute("allErrors", allErrors);
		}
		
		//����ļ��ϴ�
		for(MultipartFile myfile : items_pic) {
			if(myfile.isEmpty()){  
                System.out.println("�ļ�δ�ϴ�");  
            }else{  
                System.out.println("�ļ�����: " + myfile.getSize());  
                System.out.println("�ļ�����: " + myfile.getContentType());  
                System.out.println("�ļ�����: " + myfile.getName());  
                System.out.println("�ļ�ԭ��: " + myfile.getOriginalFilename());  
                System.out.println("========================================");  
                
                String originalFileName = myfile.getOriginalFilename();
    			String pic_path = "E:\\github\\develop\\upload\\temp\\";
    			String newFileName = UUID.randomUUID()
    					+ originalFileName.substring(originalFileName
    							.lastIndexOf("."));
    			File newFile = new File(pic_path + newFileName);
    			myfile.transferTo(newFile);
            }  
		}
/*
		// �����ϴ��ĵ���ͼƬ
		// ԭʼ����
		String originalFileName = items_pic.getOriginalFilename();
		// �ϴ�ͼƬ
		if (items_pic != null && originalFileName != null && originalFileName.length() > 0) {
			// �洢ͼƬ������·��
			String pic_path = "E:\\github\\develop\\upload\\temp\\";
			// �µ�ͼƬ����
			String newFileName = UUID.randomUUID()
					+ originalFileName.substring(originalFileName
							.lastIndexOf("."));
			// ��ͼƬ
			File newFile = new File(pic_path + newFileName);
			// ���ڴ��е�����д�����
			items_pic.transferTo(newFile);
			// ����ͼƬ����д��itemsCustom��
			itemsCustom.setPic(newFileName);
		} else {
			//����û�û��ѡ��ͼƬ���ϴ��ˣ�����ԭ����ͼƬ
			ItemsCustom temp = itemsService.findItemsById(itemsCustom.getId());
			itemsCustom.setPic(temp.getPic());
		}

		// ����service������Ʒ��Ϣ��ҳ����Ҫ����Ʒ��Ϣ�����˷���
		itemsService.updateItems(id, itemsCustom);
*/
		// return "redirect:queryItems.action";
		// return "forward:queryItems.action";

		return "/WEB-INF/jsp/success.jsp";
	}

	@RequestMapping("/editItemsAllSubmit")
	public String editItemsAllSubmit(Model model, ItemsQueryVo itemsQueryVo)
			throws Exception {

		// ����ϵ㣬���ɲ鿴itemsQueryVo��itemsCustom�Ƿ��Ѿ����յ���ǰ�洫����name
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
		model.addAttribute("itemsList", itemsList);
		return "/WEB-INF/jsp/items/itemsList.jsp";
	}

	@RequestMapping("/deleteItems")
	public String deleteItems(Integer[] items_id) throws Exception {

		// ����Ͳ�ɾ�ˣ���Ϊ�������Ǻ��滹��Ҫ����returnǰ����ϵ㣬��һ��items_id�е�ֵ����
		return "/WEB-INF/jsp/success.jsp";
	}

	// �����޸���Ʒҳ�棬����Ʒ��Ϣ��ѯ��������ҳ���п��Ա༭��Ʒ��Ϣ
	@RequestMapping("/editItemsQuery")
	public ModelAndView editItemsQuery(HttpServletRequest request,
			ItemsQueryVo itemsQueryVo) throws Exception {

		// ����service���� ���ݿ⣬��ѯ��Ʒ�б�
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);

		// ����ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		// �൱ ��request��setAttribut����jspҳ����ͨ��itemsListȡ����
		modelAndView.addObject("itemsList", itemsList);

		modelAndView.setViewName("/WEB-INF/jsp/items/editItemsQuery.jsp");

		return modelAndView;

	}

	@RequestMapping("/editItemsQueryResult")
	public String editItemsQueryResult(ItemsQueryVo itemsQueryVo)
			throws Exception {
		// �������ϵ㣬�����󿴿�itemsQueryVo�е�List<ItemsCustom>������û����ȷ���ղ���
		return "/WEB-INF/jsp/success.jsp";
	}
	
	//�����������json��(��Ʒ��Ϣ)�����json(��Ʒ��Ϣ)
	//@RequestBody���������Ʒ��Ϣ��json��ת��itemsCustom����
	//@ResponseBody��itemsCustom����ת��json���
	@RequestMapping("/requestJson")
	public @ResponseBody ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom) {
		
		return itemsCustom; //����@ResponseBodyע�⣬��itemsCustomת��json��ʽ����
	}
	
	//�����������key/value(��Ʒ��Ϣ)�����json(��Ʒ��Ϣ)
	//@ResponseBody��itemsCustom����ת��json���
	@RequestMapping("/responseJson")
	public @ResponseBody ItemsCustom responseJson(ItemsCustom itemsCustom) {
		
		return itemsCustom; //����@ResponseBodyע�⣬��itemsCustomת��json��ʽ����
	}
	
	//��ѯ��Ʒ��Ϣ�����json��ʹ��RESTful
	@RequestMapping("/itemsView/{id}")
	public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id) throws Exception {
		ItemsCustom itemsCustom = itemsService.findItemsById(id);
		return itemsCustom;
	}
	
	//��½
	@RequestMapping("/login")
	public String login(HttpServletRequest request, String username, String password) throws Exception {
		
		//ʵ����Ҫȥ�����ݿ�ƥ���
		//....
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		return "redirect:queryItems.action";
	}
	
	//�˳�
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) throws Exception {
		
		
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:queryItems.action";
	}
}
