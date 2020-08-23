package test3;

public class Kichen extends Staff {

	public static int count_Kitchen_Staff=0;
	public int count_Kitchen_Staff_non=0;

	public Kichen(String name_data, int age_data) {
		super(name_data, age_data);
		this.count_Kitchen_Staff++;
		this.count_Kitchen_Staff_non++;
		// TODO 自動生成されたコンストラクター・スタブ
	}
public String cooking (String order) {
	System.out.println(name+"は"+order+"を作った");
	return order;
}
public void dishWashing() {
	System.out.print("皿を3枚洗った");
}
}
