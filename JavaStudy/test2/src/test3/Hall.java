package test3;

public class Hall extends Staff {

	public Hall(String name_data, int age_data) {
		super(name_data, age_data);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public String order (String order) {
		System.out.println(name+"は"+order+"の注文を受けた");
		return order;
	}
		public void  serving(String dish) {
			System.out.println(name+"は"+dish+"を運んだ");
		}
	}


