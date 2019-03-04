import java.util.Objects;

public class MyObject{

	int value;

	public MyObject(int value){
		this.value = value;
	}

	@Override
	public boolean equals(Object o){	
		if (o == null)
			return false;
		if (!(o instanceof MyObject))
			return false;
		MyObject obj = (MyObject)o;
		return this.value == obj.value;
	}

	@Override
	public int hashCode(){
		Integer objInt = new Integer(this.value);
		return Objects.hashCode(objInt);
	}
	
	@Override
	public String toString(){
		return getClass().getCanonicalName()+"("+value+")";
	}
}
