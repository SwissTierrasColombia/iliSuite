package ai.ilisuite.util.exception;

public class ExitException extends SecurityException 
{
	private static final long serialVersionUID = 1L;
	
	public final int status;
    public ExitException(int status) 
    {
        super("There is no escape!");
        this.status = status;
    }
}