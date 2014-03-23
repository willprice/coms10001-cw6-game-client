public class TextOutput {
	
	private static boolean debugMode = false;
	private static boolean debugDeep = false;
	
	public static void setDebugMode(boolean mode)
	{
		debugMode = mode;
	}
	
	public static void printDebug(String message)
	{
		
		if(debugDeep)
		{
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			StackTraceElement currentElement = stackTraceElements[2];
			message = "["+ currentElement.getClassName() + " > " +  currentElement.getMethodName() + "]: " + message;
		}
		else
		{
			message = "[Debug]: " + message;
		}
		
		if(debugMode == true) System.out.print(message);
	}
	
	public static void printServer(String message)
	{
		if(debugMode)
			System.out.println("[Server Call]: " + message);
	}
	
	public static void printResponse(String message)
	{
		if(debugMode)
			System.out.println("[Server Reponse]: " + message);
	}
	
	public static void printFunction(int depth)
	{
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement currentElement = stackTraceElements[depth];
		String message = "[Function]: "+ currentElement.getClassName() + " > " +  currentElement.getMethodName() + "\n";
		System.out.print(message);
	}
	
	public static void  printStandard(String message)
	{
		message = "[Standard]: " + message; 
		System.out.print(message);
	}
	
	public static void  printStandardLn(String message)
	{
		message = "[Standard]: " + message; 
		System.out.println(message);
	}
	
	
	
	public static void printError(String message)
	{
		System.err.print("[error]: " + message);
	}
}
