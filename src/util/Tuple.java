package util;

public class Tuple<A, B> {
	public A a = null; public B b = null;
	public Tuple(A a, B b) {
		this.a = a; this.b = b;
	}

	@Override
	public boolean equals(Object t) {
		if (t instanceof Tuple)
			return this.a.equals(this.getClass().cast(t).a) && this.b.equals(this.getClass().cast(t).b);
		else
			return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() { 
		return this.a + " " + this.b; 
	}
}

