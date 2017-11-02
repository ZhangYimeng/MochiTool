package z.test;

import mochi.tool.mongodb.cache.Field;
import mochi.tool.mongodb.cache.Matryoshka;

public class MatryoshakaTest {

	public static void main(String[] args) {
		Matryoshka matry = new Matryoshka();
		Field field1 = new Field("20170101");
		Field field2 = new Field("20170101");
		matry.addField(field1);
		matry.addField(field2);
	}

}
