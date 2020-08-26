package check;

public class Check {
	private static String firstName="Yusuke";
	private static String lastName="Miyao";

	private static String printName (String firstName,String lastName) {
return firstName+lastName;
	}
	public static void main(String[] args) {
		System.out.println("printNameメソッド→"+printName(firstName,lastName));

		Pet pt =new Pet(constants.Constants.CHECK_CLASS_JAVA,constants.Constants.CHECK_CLASS_HOGE);

pt.introduce();

RobotPet tp =new RobotPet(constants.Constants.CHECK_CLASS_R2D2,constants.Constants.CHECK_CLASS_LUKE);


tp.introduce();
	}
}


