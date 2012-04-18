package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.ItemsDAO;
import pk.home.busterminal.domain.Items;

/**
 * Service class for entity class: Items
 * Items - запись ордера
 */
@Service
@Transactional
public class ItemsService extends ABaseService<Items> {

	@Autowired
	private ItemsDAO itemsDAO;

	@Override
	public ABaseDAO<Items> getAbstractBasicDAO() {
		return itemsDAO;
	}

}
