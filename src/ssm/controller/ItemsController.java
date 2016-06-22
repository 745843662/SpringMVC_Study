package ssm.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		
/*
		// �����ϴ���ͼƬ������ͼƬ
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
		//���ͼƬ���������ݿ��ˣ��ڴ˴�ӡһ�¼���
		for(MultipartFile myfile : items_pic) {
			if(myfile.isEmpty()){  
                System.out.println("�ļ�δ�ϴ�");  
            }else{  
                System.out.println("�ļ�����: " + myfile.getSize());  
                System.out.println("�ļ�����: " + myfile.getContentType());  
                System.out.println("�ļ�����: " + myfile.getName());  
                System.out.println("�ļ�ԭ��: " + myfile.getOriginalFilename());  
                System.out.println("========================================");  

                //д�����
                String originalFileName = myfile.getOriginalFilename();
                String pic_path = "E:\\github\\develop\\upload\\temp\\";
                String newFileName = UUID.randomUUID()
    					+ originalFileName.substring(originalFileName
    							.lastIndexOf("."));
                File newFile = new File(pic_path + newFileName);
                myfile.transferTo(newFile);
            }  
		}	
		

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

}
