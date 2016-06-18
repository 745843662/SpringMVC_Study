package ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ssm.po.ItemsCustom;
import ssm.service.ItemsService;

@Controller
public class ItemsController {
	
	@Autowired
	private ItemsService itemsService;
	
	@RequestMapping("/queryItemsssm")
	public ModelAndView queryItems() throws Exception {
		
		//����service�������ݿ⣬��ѯ��Ʒ�б�
		List<ItemsCustom> itemsList = itemsService.findItemsList(null);
		
		//����ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsList", itemsList);
		modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		
		return modelAndView;
	}
}
