package cslt.util.fileOperation;

public class Format {
	public static String get0X(int i) {
		if (i >= 10)
			return String.valueOf(i);
		else if(i >= 0 && i < 10) {
			return String.valueOf(0)+String.valueOf(i);
		} else {
			System.err.println("[illegal parameter: i < 0]");
			return null;
		}
	}
	
	public static String get000X(int i) {
		if (i >= 1000)
			return String.valueOf(i);
		else if(i >= 100) {
			return String.valueOf(0)+String.valueOf(i);
		} else if(i >= 10) {
			return String.valueOf(0)+String.valueOf(0)+String.valueOf(i);
		} else if(i >= 0) {
			return String.valueOf(0)+String.valueOf(0)+String.valueOf(0)+String.valueOf(i);
		} else {
			System.err.println("[illegal parameter: i < 0]");
			return null;
		}
	}
	
	public static String addQuotation(String q) {
		return "\"" + q + "\"";
	}
	
	public static void main(String[] args) {
		System.out.println(get000X(19));
	}
}
