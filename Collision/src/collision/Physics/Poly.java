package collision.Physics;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

public class Poly {

	private int count = 0;
	private Vector vertices[];
	private double radius;
	private float r, g, b;
	private Vector3f color;
	private double width, height;
	private float min, max;
	private int xCenter, yCenter;
	double ang;

	public Poly(int count, double radius, double width, double height) {
		Random random = new Random();
		this.radius = radius;
		this.setCount(count);
		this.width = width;
		this.height = height;
		double a;
		setVertices(new Vector[count]);

		for (int i = 0; i < count; i++) {
			a = 2 * Math.PI * i / count;
			double tempX = Math.cos(a) * radius * width;
			double tempY = Math.sin(a) * radius * height;
			getVertices()[i] = new Vector(tempX, tempY);
		}
		r = (float) random.nextDouble();
		g = (float) random.nextDouble();
		b = (float) random.nextDouble();
		color = new Vector3f(r, g, b);
	}

	public Poly(int count, int[] x, int[] y) {
		Random random = new Random();
		this.radius = 0;
		this.setCount(count);
		this.width = 1;
		this.height = 1;
		setVertices(new Vector[count]);

		for (int i = 0; i < count; i++) {
			getVertices()[i] = new Vector(x[i], y[i]);
		}
		r = (float) random.nextDouble();
		g = (float) random.nextDouble();
		b = (float) random.nextDouble();
	}

	public void changeSize(double width, double height) {
		this.width = width;
		this.height = height;
		double a;
		setVertices(new Vector[count]);

		for (int i = 0; i < count; i++) {
			a = 2 * Math.PI * i / count;
			double tempX = Math.cos(a) * radius * width;
			double tempY = Math.sin(a) * radius * height;
			getVertices()[i] = new Vector(tempX, tempY);
		}

		findCenter();
	}

	public void changeCount(int change) {
		if ((getCount() + change) >= 3) {
			setCount(getCount() + change);
			double a;
			setVertices(new Vector[count]);

			for (int i = 0; i < count; i++) {
				a = 2 * Math.PI * i / count;
				double tempX = Math.cos(a) * radius * width;
				double tempY = Math.sin(a) * radius * height;
				getVertices()[i] = new Vector(tempX, tempY);
			}
		}
		findCenter();
	}

	public void Transform(Vector position, double rotation) {
		translate((int) position.x, (int) position.y);
		rotate(rotation);
	}

	public void translate(int x, int y) {
		for (int i = 0; i < getCount(); i++) {
			getVertices()[i].x += x;
			getVertices()[i].y += y;
		}
		findCenter();
	}

	public void translateTo(int x5, int y5) {
		for (int i = 0; i < getCount(); i++) {
			getVertices()[i].x = getVertices()[i].x - getxCenter() + x5;
			getVertices()[i].y = getVertices()[i].y - getyCenter() + y5;
		}
		findCenter();
	}

	public void findCenter() {

		double area = 0;
		int xSum = 0, ySum = 0;

		for (int i = 0; i < getCount() - 1; i++) {
			area += getVertices()[i].x * getVertices()[i + 1].y - getVertices()[i + 1].x * getVertices()[i].y;
			xSum += (getVertices()[i].x + getVertices()[i + 1].x) * (getVertices()[i].x * getVertices()[i + 1].y - getVertices()[i + 1].x * getVertices()[i].y);
			ySum += (getVertices()[i].y + getVertices()[i + 1].y) * (getVertices()[i].x * getVertices()[i + 1].y - getVertices()[i + 1].x * getVertices()[i].y);
		}
		area += getVertices()[getCount() - 1].x * getVertices()[0].y - getVertices()[0].x * getVertices()[getCount() - 1].y;
		xSum += (getVertices()[getCount() - 1].x + getVertices()[0].x) * (getVertices()[getCount() - 1].x * getVertices()[0].y - getVertices()[0].x * getVertices()[getCount() - 1].y);
		ySum += (getVertices()[getCount() - 1].y + getVertices()[0].y) * (getVertices()[getCount() - 1].x * getVertices()[0].y - getVertices()[0].x * getVertices()[getCount() - 1].y);

		area = area / 2;

		setxCenter((int) (xSum / (6 * area)));
		setyCenter((int) (ySum / (6 * area)));
	}

	public void rotateTo(double currentAng, int x, int y) {
		ang = Math.atan2(y - getyCenter(), x - getxCenter());
		rotate(ang - currentAng);
	}

	public void rotate(double rot) {
		for (int i = 0; i < getCount(); i++) {
			double Xo = getVertices()[i].x;
			getVertices()[i].x = getxCenter() + ((Xo - getxCenter()) * Math.cos(rot) - (getVertices()[i].y - getyCenter()) * Math.sin(rot));
			getVertices()[i].y = getyCenter() + ((Xo - getxCenter()) * Math.sin(rot) + (getVertices()[i].y - getyCenter()) * Math.cos(rot));
		}
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Vector[] getVertices() {
		return vertices;
	}

	public void setVertices(Vector vertices[]) {
		this.vertices = vertices;
	}

	public int getxCenter() {
		return xCenter;
	}

	public void setxCenter(int xCenter) {
		this.xCenter = xCenter;
	}

	public int getyCenter() {
		return yCenter;
	}

	public void setyCenter(int yCenter) {
		this.yCenter = yCenter;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

}