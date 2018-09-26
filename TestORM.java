package reflect;
/**
 *  一个简单的demo:将A对象中封装的信息映射到B类对象中
 *  A类的属性名与B类中的set方法名比对,
 *  若匹配,则比对参数类型,扔匹配则调用set方法赋值
 * 否则抛异常 新建实例引用置空
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class A{
	private String name;
	private int age;
	private String email;
	
	public A() {
		super();
	}	
	public A(String name, int age, String email) {
		super();
		this.name = name;
		this.age = age;
		this.email = email;
	}
	@Override
	public String toString() {
		return "A [name=" + name + ", age=" + age + ", email=" + email + "]";
	}	
}
class B{
	private String name;
	private int age;
	private String emaill;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return emaill;
	}
	public void setEmail(String email) {
		this.emaill = email;
	}
	@Override
	public String toString() {
		return "B [name=" + name + ", age=" + age + ", email=" + emaill + "]";
	}	
}

public class TestORM {
	public static void main(String[] args) throws Exception {
		//已知的A的实例
		A a = new A("张三",18,"zhangfei@shuhan.com");
		//获得A B的字节码对象
		Class classA = a.getClass();
		Class classB = Class.forName("reflect.B");
		
		//由反射得到的无参构造创建B的实例
		Constructor constructor = classB.getDeclaredConstructor();
		constructor.setAccessible(true);
		Object newInstanceOfB = constructor.newInstance();
		
		//获取A的属性并遍历
		Field[] fieldsInA = classA.getDeclaredFields();
		for (Field field : fieldsInA) {
			//获取首字母大写的属性名
			String fieldName = field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
			//是否匹配的标志
			boolean isMapped = false;
			//获取B类方法
			Method[] methodsOfB = classB.getDeclaredMethods();
			for (Method method : methodsOfB) {
				if(method.getName().equals("set"+fieldName) && method.getParameterTypes()[0].equals(field.getType())) {
					isMapped = true;
					field.setAccessible(true);
					method.invoke(newInstanceOfB, field.get(a));
				}
			}
			if(!isMapped) {
				newInstanceOfB = null;
				throw new Exception("未找到匹配的set方法");
			}
		}
		System.out.println(newInstanceOfB);
	}
}
