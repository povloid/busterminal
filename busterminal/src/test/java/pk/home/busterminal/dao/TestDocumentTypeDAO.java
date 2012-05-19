package pk.home.busterminal.dao;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import pk.home.busterminal.domain.DocumentType;
import pk.home.busterminal.domain.DocumentType_;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * JUnit test DAO class for entity class: DocumentType
 * DocumentType - тип документа
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
public class TestDocumentTypeDAO {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private DocumentTypeDAO dataStore;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(DocumentTypeDAO dataStore) {
		this.dataStore = dataStore;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntities() throws Exception {

		long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			DocumentType documentType = new DocumentType();
			documentType.setKeyName("key " + i);
			dataStore.persist(documentType);
			index++;
		}

		List<DocumentType> list = dataStore.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities(javax.persistence.metamodel.SingularAttribute, pk.home.libs.combine.dao.ABaseDAO.SortOrderType)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntitiesSingularAttributeOfTQSortOrderType()
			throws Exception {
		long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			DocumentType documentType = new DocumentType();
			documentType.setKeyName("key " + i);
			dataStore.persist(documentType);
			index++;
		}

		List<DocumentType> list = dataStore.getAllEntities(DocumentType_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		long lastId = 0;
		for (DocumentType documentType : list) {
			assertTrue(lastId < documentType.getId());
			lastId = documentType.getId();
		}
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities(int, int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntitiesIntInt() throws Exception {

		// int index = 0;
		for (int i = 0; i < 100; i++) {
			DocumentType documentType = new DocumentType();
			documentType.setKeyName("key " + i);
			dataStore.persist(documentType);
			// index++;
		}

		List<DocumentType> list = dataStore.getAllEntities(10, 10);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities(int, int, javax.persistence.metamodel.SingularAttribute, pk.home.libs.combine.dao.ABaseDAO.SortOrderType)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntitiesIntIntSingularAttributeOfTQSortOrderType()
			throws Exception {
		// long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			DocumentType documentType = new DocumentType();
			documentType.setKeyName("key " + i);
			dataStore.persist(documentType);
			// index++;
		}

		List<DocumentType> list = dataStore.getAllEntities(10, 10, DocumentType_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (DocumentType documentType : list) {
			assertTrue(lastId < documentType.getId());
			lastId = documentType.getId();
		}
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities(boolean, int, int, javax.persistence.metamodel.SingularAttribute, pk.home.libs.combine.dao.ABaseDAO.SortOrderType)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntitiesBooleanIntIntSingularAttributeOfTQSortOrderType()
			throws Exception {
		long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			DocumentType documentType = new DocumentType();
			documentType.setKeyName("key " + i);
			dataStore.persist(documentType);
			index++;
		}

		// all - FALSE
		List<DocumentType> list = dataStore.getAllEntities(false, 10, 10, DocumentType_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (DocumentType documentType : list) {
			assertTrue(lastId < documentType.getId());
			lastId = documentType.getId();
		}

		// all - TRUE
		list = dataStore.getAllEntities(true, 10, 10, DocumentType_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		lastId = 0;
		for (DocumentType documentType : list) {
			assertTrue(lastId < documentType.getId());
			lastId = documentType.getId();
		}
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#find(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testFind() throws Exception {

		DocumentType documentType = new DocumentType();
		documentType.setKeyName("key " + 999);
		documentType = dataStore.persist(documentType);

		long id = documentType.getId();

		DocumentType documentType2 = dataStore.find(id);

		assertEquals(documentType, documentType2);
		assertTrue(documentType.getId() == documentType2.getId());
		assertEquals(documentType.getKeyName(), documentType2.getKeyName());

	}

	/**
	 * Test method for {@link pk.home.libs.combine.dao.ABaseDAO#count()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testCount() throws Exception {
		long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			DocumentType documentType = new DocumentType();
			documentType.setKeyName("key " + i);
			dataStore.persist(documentType);
			index++;
		}

		long count = dataStore.count();

		assertTrue(count == index);
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#persist(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testPersist() throws Exception {
		DocumentType documentType = new DocumentType();
		documentType.setKeyName("key " + 999);
		documentType = dataStore.persist(documentType);

		long id = documentType.getId();

		DocumentType documentType2 = dataStore.find(id);

		assertEquals(documentType, documentType2);
		assertTrue(documentType.getId() == documentType2.getId());
		assertEquals(documentType.getKeyName(), documentType2.getKeyName());
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#refresh(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testRefresh() throws Exception {
		DocumentType documentType = new DocumentType();
		documentType.setKeyName("key " + 999);
		documentType = dataStore.persist(documentType);

		long id = documentType.getId();

		DocumentType documentType2 = dataStore.find(id);

		assertEquals(documentType, documentType2);
		assertTrue(documentType.getId() == documentType2.getId());
		assertEquals(documentType.getKeyName(), documentType2.getKeyName());

		documentType2.setKeyName("key 65535");
		documentType2 = dataStore.refresh(documentType2);

		assertEquals(documentType, documentType2);
		assertTrue(documentType.getId() == documentType2.getId());
		assertEquals(documentType.getKeyName(), documentType2.getKeyName());

	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#merge(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testMerge() throws Exception {
		DocumentType documentType = new DocumentType();
		documentType.setKeyName("key " + 999);
		documentType = dataStore.persist(documentType);

		long id = documentType.getId();

		DocumentType documentType2 = dataStore.find(id);

		assertEquals(documentType, documentType2);
		assertTrue(documentType.getId() == documentType2.getId());
		assertEquals(documentType.getKeyName(), documentType2.getKeyName());

		documentType2.setKeyName("key 65535");
		documentType2 = dataStore.merge(documentType2);

		documentType = dataStore.refresh(documentType);

		assertEquals(documentType, documentType2);
		assertTrue(documentType.getId() == documentType2.getId());
		assertEquals(documentType.getKeyName(), documentType2.getKeyName());
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#remove(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testRemove() throws Exception {
		DocumentType documentType = new DocumentType();
		documentType.setKeyName("key " + 999);
		documentType = dataStore.persist(documentType);

		long id = documentType.getId();

		DocumentType documentType2 = dataStore.find(id);

		assertEquals(documentType, documentType2);
		assertTrue(documentType.getId() == documentType2.getId());
		assertEquals(documentType.getKeyName(), documentType2.getKeyName());

		dataStore.remove(documentType);

		DocumentType documentType3 = dataStore.find(id);
		assertTrue(documentType3 == null);

	}
	
	
	
	// -----------------------------------------------------------------------------------------------------------------
	
	@Test
	@Rollback(true)
	public void insertEntities() throws Exception {

		long index = dataStore.count();
		for (int i = 200; i < 210; i++) {
			DocumentType documentType = new DocumentType();
			documentType.setKeyName("key " + i);
			dataStore.persist(documentType);
			index++;
		}

		List<DocumentType> list = dataStore.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}
	

}
