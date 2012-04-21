package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.CustomerDAO;
import pk.home.busterminal.domain.Customer;

/**
 * Service class for entity class: Customer
 * Customer - клиент
 */
@Service
@Transactional
public class CustomerService extends ABaseService<Customer> {

	@Autowired
	private CustomerDAO customerDAO;

	@Override
	public ABaseDAO<Customer> getAbstractBasicDAO() {
		return customerDAO;
	}

}
