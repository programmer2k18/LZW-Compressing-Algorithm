 package LZWTechnique;

public class Handeler {

	public static void main(String[] args) {
		try {
			Compression c=new Compression();
			c.Compress();
			c.Decompress();
		}
		catch(Exception e) {
			System.out.println("Error to compress or decompress this file " + e.getMessage());
		}
		
	}

}
