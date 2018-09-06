package re_encoding;
/**
 * 实现了将GBK格式编码的工程文件全部转码为utf-8
 * 严谨性有待提高
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Recharset {
	
	public static void main(String[] args) {
		File res = new File("F:/projects");
		File dir = new File("F:/lll");
		//防止损坏源文件
		if(dir.equals(res)){
			System.out.println("目标路径与原路径相同");
			return;
		}		
		getFile(res,dir);
	}
	
	private static void getFile(File res, File dir) {
		
		try {
			dir.mkdir();
			File[] files = res.listFiles();
			if(files == null)return ;//空文件夹
			for (File file : files) {
				if(file.isDirectory()){
					File newDir = new File(dir,res.getName());//创建新目录
					getFile(file,newDir);
				} else{
					File newFile = new File(dir,file.getName());//创建新路径
					fileRecharset(file,newFile);
				}
			}
		} catch (Exception e) {
			System.out.println("目标路径创建失败");
			e.printStackTrace();
		}
	}

	private static void fileRecharset(File res,File file) {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			file.createNewFile();
			//接入字符转换流
			in = new BufferedReader(new InputStreamReader(new FileInputStream(res), "GBK"));
			out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf8"));
			
			//写文件
			String str;
			while((str = in.readLine()) != null){
				out.println(str);
				out.flush();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			//关闭资源
			out.close();
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				
			}
		}
	}
}
