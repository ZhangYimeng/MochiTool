package mochi.tool.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import mochi.tool.file.exception.CanNotWriteException;
import mochi.tool.file.exception.NotAFileException;

public class MochiTxtFile {

	private File path;
	private File file;
	private InputStream is;
	private BufferedReader br;
	private BufferedWriter bw;

	public MochiTxtFile(File _file) throws IOException, NotAFileException {
		file = _file;
		this.path = new File(file.getParent());
		if (!this.path.exists()) {
			this.path.mkdir();
		}
		if (!file.isDirectory()) {
			if (!file.exists()) {
				file.createNewFile();
			}
		} else {
			throw new NotAFileException();
		}
		is = new FileInputStream(_file);
		br = new BufferedReader(new FileReader(file));
		bw = new BufferedWriter(new FileWriter(file, true));
	}
	
	public MochiTxtFile(String path) throws IOException, NotAFileException {
		file = new File(path);
		this.path = new File(file.getParent());
		if (!this.path.exists()) {
			this.path.mkdir();
		}
		if (!file.isDirectory()) {
			if (!file.exists()) {
				file.createNewFile();
			}
		} else {
			throw new NotAFileException();
		}
		is = new FileInputStream(path);
		br = new BufferedReader(new FileReader(file));
		bw = new BufferedWriter(new FileWriter(file, true));
	}
	
	public MochiTxtFile(File _file, boolean append) throws IOException, NotAFileException {
		file = _file;
		this.path = new File(file.getParent());
		if (!this.path.exists()) {
			this.path.mkdir();
		}
		if (!file.isDirectory()) {
			if (!file.exists()) {
				file.createNewFile();
			}
		} else {
			throw new NotAFileException();
		}
		is = new FileInputStream(_file);
		br = new BufferedReader(new FileReader(file));
		bw = new BufferedWriter(new FileWriter(file, append));
	}
	
	public MochiTxtFile(String path, boolean append) throws IOException, NotAFileException {
		file = new File(path);
		this.path = new File(file.getParent());
		if (!this.path.exists()) {
			this.path.mkdir();
		}
		if (!file.isDirectory()) {
			if (!file.exists()) {
				file.createNewFile();
			}
		} else {
			throw new NotAFileException();
		}
		is = new FileInputStream(path);
		br = new BufferedReader(new FileReader(file));
		bw = new BufferedWriter(new FileWriter(file, append));
	}

	public void recreateNewFile() throws IOException, NotAFileException {
		if (file.isFile()) {
			file.delete();
			file.createNewFile();
		} else if (!file.isDirectory()) {
			if (!file.exists()) {
				file.createNewFile();
			} else {
				// 好像永远不会被执行
				file.delete();
				file.createNewFile();
			}
		} else {
			throw new NotAFileException();
		}
	}

	public void write(String content) throws IOException, CanNotWriteException {
		if (file.canWrite()) {
			bw.write(content);
			bw.flush();
		} else {
			throw new CanNotWriteException();
		}
	}

	public void resetIndexOfReadLineFunction() throws FileNotFoundException {
		br = new BufferedReader(new FileReader(file));
	}
	
	public String readLine() throws IOException {
		return br.readLine();
	}

	public String readAllContents() throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString("UTF-8");
	}

	public boolean ready() throws IOException {
		return br.ready();
	}
	
	public void close() {
		try {
			is.close();
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, CanNotWriteException, NotAFileException {
		MochiTxtFile mf = new MochiTxtFile("/Users/zhangyimeng/iPhone X.txt", false);
		mf.write("你好呀！");
		mf.write("哦！你也好！\n");
		mf.write("=======+=====");
		System.out.print(mf.readAllContents());
		System.out.println("over");
	}

}
