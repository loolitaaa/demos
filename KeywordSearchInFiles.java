package findwar;
/**
 * 实现了在某路径下的所有文件中查找包含指定字符串的文件,并打印其路径
 * 方便阅读工程源码
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class KeywordSearchInFiles {
	public static void main(String[] args) {
		File dir = new File("F:/tomcat");//文件路径
		fileSearch(dir);
	}

	private static void fileSearch(File dir) {
		// 递归查找指定后缀名的文件
		File[] list = dir.listFiles();
		if(list==null)return;
		for (File file : list) {
			if(file.isDirectory()){
				fileSearch(file);
			} else{
				if(file.getName().endsWith(".java")){
					keywordSearch(file);
				}
			}
		}
	}

	private static void keywordSearch(File file) {
		// 按行读取文件,查找字符串
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String str;
			while((str = reader.readLine()) != null){
				if(str.contains("unpack")){
					System.out.println(file.getPath());
					reader.close();
					return;
				}
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
