package test3;


public class Staff {
	public String affliation_store="赤坂店";
	public int hourly_wage=1100;
	public String name;
	public int age;

	public Staff (String name_data,int age_data) {
		this.name=name_data;
        this.age=age_data;
	}
public void dishWashing() {
	System.out.println("皿を一枚洗った");
}
}
