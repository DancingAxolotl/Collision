package collision.Physics;

import java.util.Random;

public class Vector {

	public double x, y;

	public Vector(double Ix, double Iy) {
		x = Ix;
		y = Iy;
	}

	public Vector scale(double scale) {
		return new Vector(x * scale, y * scale);
	}

	public Vector multiply(Vector other) {
		return new Vector(x * other.x, y * other.y);
	}

	public Vector perp() {
		Vector vect = new Vector(-y, x);
		return vect;
	}

	public void randomize(Vector max, Vector min) {
		Random r = new Random();
		x = (r.nextFloat() * (max.x - min.x) + min.x);
		y = (r.nextFloat() * (max.y - min.y) + min.y);
	}

	public double dot(Vector axis) {
		return (x * axis.x + y * axis.y);
	}

}
