package ai.ilisuite.base.log;

public interface IliAppStreamProcessor {
	public void writeMessage(String message, boolean isErrorStream);
	public void finishWriting();
}
