package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.ItemsDAO;
import pk.home.busterminal.domain.Items;

/**
 * Service class for entity class: Items Items - запись ордера
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

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Items persist(Items o) throws Exception {
		check(o);
		return super.persist(o);
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Items merge(Items o) throws Exception {
		check(o);
		return super.merge(o);
	}

	/**
	 * Проверка уровня сервиса
	 * @param o
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void check(Items o) throws Exception {
		o.check();
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void remove(Items object) throws Exception {
		super.remove(object);
	}

}
