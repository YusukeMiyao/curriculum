package test2;



public class Main {

	public static void main(String[] args) {
		Cfhef meatdish=new Cfhef ("肉料理 ");

		String  meat_dish =meatdish.grill("米沢牛");

		Waiter waiter=new Waiter();
		waiter.serving(meat_dish);


	}

}
