package iliSuite.plugin.ili2pg;

public class TestMain {

	public static void main(String[] args) {
		Ili2pgPlugin prueba = new Ili2pgPlugin();
		
		System.out.println(
		prueba.getNameDB()+
		
		prueba.getHelpText()+
		
		prueba.getName()
		);
	}

}
