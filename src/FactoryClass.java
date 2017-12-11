
public class FactoryClass {

	private FactoryClass()
	{
		
	}
	
	private static CrawlInterface ci;
	
	public static CrawlInterface getInstance() {
		ci=new CrawlImpl();
		return ci;
	}
}
