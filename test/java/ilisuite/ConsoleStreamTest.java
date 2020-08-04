package ilisuite;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import ai.ilisuite.base.log.IliAppMessagesClasifier;
import ai.ilisuite.base.log.IliAppStreamProcessor;
import ai.ilisuite.base.log.IliMessageReceiver;
import ai.ilisuite.base.log.IliMessageType;


public class ConsoleStreamTest implements IliMessageReceiver {
	private static final String TEST_OUT = "test/data/";
	private Map<IliMessageType, List<String>> messages;
	
	public ConsoleStreamTest() {
		messages = new HashMap<>();
	}
	
	@Test
	public void summary() {
		IliAppStreamProcessor clasifier = new IliAppMessagesClasifier(this);
		
		invokeClasifier(clasifier, "summary.log");
		
		int summary = getNumberOfRecords(IliMessageType.RegisterReport);
		
		Assert.assertEquals(1, summary);
		
		String expectedResult =
				" 27439 objects in CLASS Datos_SNR_V2_9_6.Datos_SNR.SNR_Derecho\n" + 
				" 27439 objects in CLASS Datos_SNR_V2_9_6.Datos_SNR.SNR_Fuente_Derecho\n" + 
				" 27439 objects in CLASS Datos_SNR_V2_9_6.Datos_SNR.SNR_Predio_Registro\n" + 
				" 30198 objects in CLASS Datos_SNR_V2_9_6.Datos_SNR.SNR_Titular\n" + 
				" 40255 objects in CLASS Datos_SNR_V2_9_6.Datos_SNR.snr_titular_derecho\n";
		
		String actualResult = messages.get(IliMessageType.RegisterReport).get(0);
		
		Assert.assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void result() {
		IliAppStreamProcessor clasifier = new IliAppMessagesClasifier(this);
		
		invokeClasifier(clasifier, "ok_simport.log");
		Assert.assertEquals(1, getNumberOfRecords(IliMessageType.Result));
		
		messages = new HashMap<>();
		invokeClasifier(clasifier, "ok_import.log");
		Assert.assertEquals(1, getNumberOfRecords(IliMessageType.Result));
		
		messages = new HashMap<>();
		invokeClasifier(clasifier, "ok_validation.log");
		Assert.assertEquals(1, getNumberOfRecords(IliMessageType.Result));
		
		messages = new HashMap<>();
		invokeClasifier(clasifier, "ok_export.log");
		Assert.assertEquals(1, getNumberOfRecords(IliMessageType.Result));
		
		// failed
		messages = new HashMap<>();
		invokeClasifier(clasifier, "ko_import.log");
		Assert.assertEquals(1, getNumberOfRecords(IliMessageType.Result));
		
		messages = new HashMap<>();
		invokeClasifier(clasifier, "ko_validation.log");
		Assert.assertEquals(1, getNumberOfRecords(IliMessageType.Result));
	}
	
	@Test
	public void otherErrorWarningInfo() {
		IliAppStreamProcessor clasifier = new IliAppMessagesClasifier(this);

		invokeClasifier(clasifier, "single_line.log");
		
		int errors = getNumberOfRecords(IliMessageType.Error);
		int warnings = getNumberOfRecords(IliMessageType.Warning);
		int others = getNumberOfRecords(IliMessageType.Other);
		int infos = getNumberOfRecords(IliMessageType.Info);
		
		Assert.assertEquals(1, errors);
		Assert.assertEquals(1, warnings);
		Assert.assertEquals(1, others);
		Assert.assertEquals(17, infos);
		
		// with trace (multiple line)
		messages = new HashMap<>();

		invokeClasifier(clasifier, "multiple_lines.log");

		errors = getNumberOfRecords(IliMessageType.Error);
		warnings = getNumberOfRecords(IliMessageType.Warning);
		others = getNumberOfRecords(IliMessageType.Other);
		infos = getNumberOfRecords(IliMessageType.Info);
		
		Assert.assertEquals(1, errors);
		Assert.assertEquals(1, warnings);
		Assert.assertEquals(1, others);
		Assert.assertEquals(20, infos);
	}
	
	private void invokeClasifier(IliAppStreamProcessor clasifier, String fileName) {
		List<String> linesLog;
		try {
			linesLog = Files.readAllLines(Paths.get(TEST_OUT + fileName), Charset.forName("UTF-8"));
		} catch (IOException e) {
			Assert.fail("log file " + fileName + " could not load");
			return;
		}
		
		for(String line:linesLog)
			clasifier.writeMessage(line, false);
		
		clasifier.finishWriting();
	}
	
	private int getNumberOfRecords(IliMessageType messageType) {
		List<String> msgs = messages.get(messageType);
		if(msgs == null)
			return 0;
		else
			return msgs.size();
	}

	@Override
	public void message(String message, IliMessageType messageType) {
		List<String> messageList = messages.get(messageType);
		
		if(messageList == null) {
			messageList = new ArrayList<>();
			messages.put(messageType, messageList);
		}
		
		messageList.add(message);
	}
}
