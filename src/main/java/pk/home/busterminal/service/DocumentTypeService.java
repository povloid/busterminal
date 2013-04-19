package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.DocumentTypeDAO;
import pk.home.busterminal.domain.DocumentType;

/**
 * Service class for entity class: DocumentType
 * DocumentType - тип документа
 */
@Service
@Transactional
public class DocumentTypeService extends ABaseService<DocumentType> {

	@Autowired
	private DocumentTypeDAO documentTypeDAO;

	@Override
	public ABaseDAO<DocumentType> getAbstractBasicDAO() {
		return documentTypeDAO;
	}

}
