package JythonExecutor;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import ReconciliationSystem.ReconciliationSystem.ExecutionService;



public class JythonEngine {

	private PythonInterpreter interp;
	private Reconciliation recSystem;
	private ExecutionService application;
	
	public PythonInterpreter getInterp() {
		return interp;
	}

	public void setInterp(PythonInterpreter interp) {
		this.interp = interp;
	}
	
	public JythonEngine(String baseFolder,ExecutionService application)
	{
		/*CommandLine d = application.getCmdLine();*/
		PySystemState sys = new PySystemState(); 
		//baseFolder  += "/";
		sys.path.append(new PyString(baseFolder)); 
		PyStringMap dict = new PyStringMap(); 
		interp = new PythonInterpreter(dict,sys);
		interp.exec("import sys \nprint sys.path\n");
		interp.exec("import sys \nprint sys.path\n");
		this.application = application;
	}
	
	
	public void scriptInitialise(String fileName)
	{
		try
		{
			if(fileName == null || fileName.equalsIgnoreCase(""))
			{
				return;
			}
			 
		
			String scriptName = fileName.substring(0, fileName.indexOf("."));
			scriptName = scriptName.trim();
			if(interp != null)
			{
				System.out.println("from " + scriptName + " import " + scriptName);
				interp.exec("from " + scriptName + " import " + scriptName);
		        PyObject testRunClass = interp.get(scriptName);
		        
		        if(testRunClass != null)
		        {
		        	PyObject testRunObject = testRunClass.__call__();
			        recSystem = (Reconciliation)testRunObject.__tojava__(Reconciliation.class);
			        recSystem.initialize(this.application);
			        
		        }
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
			//appGridView.addToDebugConsole("Exception in jyhton script Initialise : " + e.toString());;
		}
		
	}
	
}
