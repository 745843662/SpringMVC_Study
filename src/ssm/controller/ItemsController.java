package ssm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

		model.addAttribute("itemsCustom", itemsCustom);

		// ͨ���β��е�model��model���ݴ���ҳ��
		// �൱��modelAndView.addObject����
		return "/WEB-INF/jsp/items/editItems.jsp";
	}

	@RequestMapping("/editItemsSubmit")
	public String editItemsSubmit(HttpServletRequest request, Integer id,
			ItemsCustom itemsCustom) throws Exception {

		// ����service������Ʒ��Ϣ��ҳ����Ҫ����Ʒ��Ϣ�����˷���
		itemsService.updateItems(id, itemsCustom);

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
	public String editItemsQueryResult(ItemsQueryVo itemsQueryVo) throws Exception {
		//�������ϵ㣬�����󿴿�itemsQueryVo�е�List<ItemsCustom>������û����ȷ���ղ���
		return "/WEB-INF/jsp/success.jsp";
	}

}
