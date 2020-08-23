package test3;

public class Main {


	public static void main(String[] args) {
		Kichen airo =new Kichen("Y口I郎",24);
		Hall aiko=new Hall("Y林I子",21);

		int Kitchen_Staf_num=Kitchen .count_Kitchen_Staff;

		System.out.println(Kitchen_Staf_num+"人");

		int Kitchen_Staff_non_num=airo.count_Kitchen_Staff_non;
		System.out.println(Kitchen_Staff_non_num+"人");

		Kitchen jeiro=new Kitchen("Y口J郎",22);
		System.out.println(Kitchen_Staf_num+"人");
		Kitchen_Staff_non_num=jeiro.count_Kitchen_Staff_non;
		System.out.println( Kitchen_Staff_non_num+"人");

		System.out.println(airo.affliation_store);
		String order =aiko.order("厚焼き玉子");
		String dish =airo.cooking(order);
		aiko.serving(dish);
		// TODO 自動生成されたメソッド・スタブ

	int Count_Staff=Staff.count_Staff;
	Staff.printStaffNum(count_Staff);
	}

}

