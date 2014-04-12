package ListGraph;

import java.util.HashMap;

public class Test {

	public static void main(String[] args) {
		HashMap<String, Integer> test = new HashMap<>();
		test.put("Hej", 5);
		test.put("Hej", 9);
		System.out.println(test.get("Hej"));
	}

}
