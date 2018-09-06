package findwar;
/**
 * ʵ������ĳ·���µ������ļ��в��Ұ���ָ���ַ������ļ�,����ӡ��·��
 * �����Ķ�����Դ��
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class KeywordSearchInFiles {
	public static void main(String[] args) {
		File dir = new File("F:/tomcat");//�ļ�·��
		fileSearch(dir);
	}

	private static void fileSearch(File dir) {
		// �ݹ����ָ����׺�����ļ�
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
		// ���ж�ȡ�ļ�,�����ַ���
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
