import java.io.*;

public class Rom{
	private byte []bytes;
	private String filename;

	public Rom(String filename){
		try(InputStream file = new FileInputStream(filename)){
			int size = (int) new File(filename).length();
			bytes = new byte[size];
			this.filename = filename;

			file.read(bytes);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public Rom(){
		filename = null;
		bytes = new byte[40*1024];
	}

	public int getSize(){
		return bytes.length;
	}

	public String getFilename(){
		return filename;
	}

	public void save(String filename){
		String current_filename;
		if(filename == null) current_filename = this.filename;
		else current_filename = filename;

		try(OutputStream outputStream = new FileOutputStream(current_filename)){
			outputStream.write(bytes);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public int getPixel(int x, int y, int offset){
		if(offset*16 + y + 8 > getSize()) return 0;

		x = 7 - x;
		byte j = (byte) (1 << x);

		byte color_arg1 = (byte) (j & bytes[0 + y + offset*16]);
		byte color_arg2 = (byte) (j & bytes[8 + y + offset*16]);

		if(color_arg1 != 0 && color_arg2 != 0) return 3;
		if(color_arg1 != 0) return 2;
		if(color_arg2 != 0) return 1;
		return 0;
	}

	public void putPixel(int x, int y, int offset, int color){
		if(offset*16 + y + 8 > getSize()) return;

		x = 7 - x;
		int j = 1 << x;

		int new_j = 255 - j;

		byte color_arg1 = (byte) (new_j & bytes[y + offset*16]);
		byte color_arg2 = (byte) (new_j & bytes[8 + y + offset*16]);

		if((color & 1) == 1) color_arg2 = (byte) (color_arg2 | j);
		if((color & 2) == 2) color_arg1 = (byte) (color_arg1 | j);

		bytes[y + offset*16] = color_arg1;
		bytes[8 + y + offset*16] = color_arg2;
	}
}
