package ssm.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ssm.mapper.ItemsMapper;
import ssm.mapper.ItemsMapperCustom;
import ssm.po.Items;
import ssm.po.ItemsCustom;
import ssm.po.ItemsQueryVo;
import ssm.service.ItemsService;

public class ItemsServiceImpl implements ItemsService {

	@Autowired
	private ItemsMapperCustom itemsMapperCustom;

	@Autowired
	private ItemsMapper itemsMapper;

	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)
			throws Exception {

		// ͨ��itemsMapperCustom��ѯ���ݿ�
		return itemsMapperCustom.findItemsList(itemsQueryVo);
	}

	@Override
	public ItemsCustom findItemsById(Integer id) throws Exception {

		// ͨ��id��ѯitems��Ϣ
		Items items = itemsMapper.selectByPrimaryKey(id);
		// �������ʵ�����������Ʒ��Ϣ����ҵ����
		// ....
		// ������չ��ItemsCustom
		ItemsCustom itemsCustom = new ItemsCustom();
		// ��items�����Կ�����itemsCustom
		BeanUtils.copyProperties(items, itemsCustom);

		return itemsCustom;
	}

	@Override
	public void updateItems(Integer id, ItemsCustom itemsCustom)
			throws Exception {
		// ���ҵ��У�飬ͨ����service�ӿڶԹؼ���������У��
		// У�� id�Ƿ�Ϊ�գ����Ϊ���׳��쳣

		// ������Ʒ��Ϣʹ��updateByPrimaryKeyWithBLOBs����id����items���������ֶΣ����� ���ı������ֶ�
		// updateByPrimaryKeyWithBLOBsҪ�����ת��id
		itemsCustom.setId(id);
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
	}

}
