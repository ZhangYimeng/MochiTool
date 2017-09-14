package mochi.tool.infiniteinteger;

public class InfiniteInteger {
	
	private String number;
	private boolean pn;
	/**
	 * 倒序存放。
	 */
	private int[] container;
	@SuppressWarnings("unused")
	private static final int magnitude = 1000000000;
	
	public InfiniteInteger(String number) {
		this.number = number;
		this.pn = this.number.substring(0, 1).equals("-")? false: true;
		int numberLength;
		numberLength = this.number.length();
		int index = 0;
		this.container = new int[numberLength/9 + 1];
		System.out.println(numberLength);
		if(pn == true) {
			while(numberLength > 9) {
				this.container[index] = Integer.parseInt(this.number.substring(numberLength - 9, numberLength));
				System.out.println(this.container[index]);
				index++;
				numberLength -= 9;
			}
			if(numberLength != 0) {
				this.container[index] = Integer.parseInt(this.number.substring(0, numberLength));
				System.out.println(this.container[index]);
			}
		} else {
			while(numberLength > 9) {
				this.container[index] = -Integer.parseInt(this.number.substring(numberLength - 9, numberLength));
				System.out.println(this.container[index]);
				index++;
				numberLength -= 9;
			}
			if(numberLength != 0) {
				this.container[index] = Integer.parseInt(this.number.substring(0, numberLength));
				System.out.println(this.container[index]);
			}
		}
	}
	
	public boolean PositiveOrNegtive() {
		return this.pn;
	}
	
	public int returnContainerLength() {
		return this.container.length;
	}
	
	public InfiniteInteger plus(InfiniteInteger integer) {
		return null;
	}
		
	public InfiniteInteger minus(InfiniteInteger integer) {
		return null;
	}
	
	public InfiniteInteger multiple(InfiniteInteger integer) {
		return null;
	}
	
	public String oString() {
		return this.number;
	}
	
}
