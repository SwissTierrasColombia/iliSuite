package ai.ilisuite.util.log;

public interface LogListener {
	public void writeMessage(String message, boolean isErrorStream);
}
