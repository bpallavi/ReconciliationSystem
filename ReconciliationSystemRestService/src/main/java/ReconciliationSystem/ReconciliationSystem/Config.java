package ReconciliationSystem.ReconciliationSystem;


import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OSchemaProxy;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

public class Config 
{
	public static final String OUTPUT = "OUTPUT";
	public static final String USEROUTPUT = "USEROUTPUT";

	static final String RESULT = "RESULT";
	static final String USERPATTERNS = "USERPATTERNS";

	private static Config config = null;
	
	private String dbserver;
	private String dbuser;
	private String dbpasswd;
	private static  OrientGraphFactory  factory=null;
	
	
	
	public static OrientGraphFactory getFactory() {
		return factory;
	}


	public static void setFactory(OrientGraphFactory factory) {
		Config.factory = factory;
	}


	private Config()
	{
		dbserver = System.getenv("DB_SERVER").trim()  ;
		dbuser = System.getenv("DB_USER").trim()  ;
		dbpasswd = System.getenv("DB_PASSWD").trim() ;	
		factory = new OrientGraphFactory("remote:"+dbserver,dbuser,dbpasswd).setupPool(1,10);
		
	
		
		ODatabaseDocumentTx db = factory.getDatabase();
		OSchemaProxy schema  = db.getMetadata().getSchema();
		boolean bExists = schema.existsClass(RESULT);
		OrientGraphNoTx graph = factory.getNoTx();
		
		if(bExists == false ) 
		{
			
			OrientVertexType result  = graph.createVertexType(RESULT);
			result.createProperty(Config.OUTPUT, OType.EMBEDDEDMAP);
		}
	   bExists = schema.existsClass(USERPATTERNS);
		if(bExists == false ) 
		{
			
			OrientVertexType result  = graph.createVertexType(USERPATTERNS);
			result.createProperty(Config.USEROUTPUT, OType.EMBEDDEDMAP);
		}
		graph.commit();
	}
	
	
	public static Config load()
    {
        if (config == null) {
        	config = new Config();
        }
        return config;
    }
}
