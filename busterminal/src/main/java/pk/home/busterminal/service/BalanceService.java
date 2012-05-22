package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.BalanceDAO;
import pk.home.busterminal.domain.Balance;

/**
 * Service class for entity class: Balance
 * Balance - баланс
 */
@Service
@Transactional
public class BalanceService extends ABaseService<Balance> {

	@Autowired
	private BalanceDAO balanceDAO;

	@Override
	public ABaseDAO<Balance> getAbstractBasicDAO() {
		return balanceDAO;
	}

}
