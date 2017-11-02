package z.test;

public class KeyObject implements Comparable<KeyObject> {
	
	private int key;
	
	public KeyObject() {
		key = 0;
	}
	
	public KeyObject(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}
	
	@Override
	public int compareTo(KeyObject o) {
		if(o.key >= key) {
			return 1;
		} else {
			return -1;
		}
	}

}
