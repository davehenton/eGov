package org.egov.erpcollection.models;
import org.egov.models.AbstractPersistenceServiceTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OnlinePaymentTest extends AbstractPersistenceServiceTest<OnlinePayment,Long> {
	private CollectionObjectFactory factory;
	
	public OnlinePaymentTest() {		
		this.type = OnlinePayment.class;
	}
	
	@Before
	public void setUp() {
		factory = new CollectionObjectFactory(session);
	}
	
	@Test
	public void testcreateReceiptDetail()
	{
		Assert.assertNotNull(service.create(factory.createOnlinePayment()));
	}
}
