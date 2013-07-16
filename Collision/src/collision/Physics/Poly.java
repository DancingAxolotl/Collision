package collision.Physics;

import java.util.Random;

import org.lwjgl.opengl.GL11;

public class Poly {

	public int count = 0;
	public Vector vertices[];
	public double radius;
	public float r, g, b;
	public double width, height;

	public Poly(int count, double radius, double width, double height) {
		Random random = new Random();
		this.radius = radius;
		this.count = count;
		this.width = width;
		this.height = height;
		double a;
		vertices = new Vector[this.count];

		for (int i = 0; i < this.count; i++) {
			a = 2 * Math.PI * i / this.count;
			double tempX = Math.cos(a) * radius * width;
			double tempY = Math.sin(a) * radius * height;
			vertices[i] = new Vector(tempX, tempY);
		}
		r = (float) random.nextDouble();
		g = (float) random.nextDouble();
		b = (float) random.nextDouble();
	}

	public void changeSize(double width, double height) {
		this.width = width;
		this.height = height;
		double a;
		vertices = new Vector[this.count];

		for (int i = 0; i < this.count; i++) {
			a = 2 * Math.PI * i / this.count;
			double tempX = Math.cos(a) * radius * width;
			double tempY = Math.sin(a) * radius * height;
			vertices[i] = new Vector(tempX, tempY);
		}
		
		rotate(lastRotation);
	}

	public void changeCount(int change) {
		if ((count + change) >= 3) {
			count += change;
			double a;
			vertices = new Vector[this.count];

			for (int i = 0; i < this.count; i++) {
				a = 2 * Math.PI * i / this.count;
				double tempX = Math.cos(a) * radius * width;
				double tempY = Math.sin(a) * radius * height;
				vertices[i] = new Vector(tempX, tempY);
			}
		}
		findCenter();
	}

	public void Transform(Vector position, double rotation) {
		translate((int) position.x, (int) position.y);
		rotate(rotation);
	}

	public void translate(int x, int y) {
		for (int i = 0; i < count; i++) {
			vertices[i].x += x;
			vertices[i].y += y;
		}
		findCenter();
	}

	public void translateTo(int x5, int y5) {
		for (int i = 0; i < count; i++) {
			vertices[i].x = vertices[i].x - xCenter + x5;
			vertices[i].y = vertices[i].y - yCenter + y5;
		}
		findCenter();
	}

	public int xCenter, yCenter;

	public void findCenter() {

		double area = 0;
		int xSum = 0, ySum = 0;

		for (int i = 0; i < count - 1; i++) {
			area += vertices[i].x * vertices[i + 1].y - vertices[i + 1].x * vertices[i].y;
			xSum += (vertices[i].x + vertices[i + 1].x) * (vertices[i].x * vertices[i + 1].y - vertices[i + 1].x * vertices[i].y);
			ySum += (vertices[i].y + vertices[i + 1].y) * (vertices[i].x * vertices[i + 1].y - vertices[i + 1].x * vertices[i].y);
		}
		area += vertices[count - 1].x * vertices[0].y - vertices[0].x * vertices[count - 1].y;
		xSum += (vertices[count - 1].x + vertices[0].x) * (vertices[count - 1].x * vertices[0].y - vertices[0].x * vertices[count - 1].y);
		ySum += (vertices[count - 1].y + vertices[0].y) * (vertices[count - 1].x * vertices[0].y - vertices[0].x * vertices[count - 1].y);

		area = area / 2;

		xCenter = (int) (xSum / (6 * area));
		yCenter = (int) (ySum / (6 * area));
	}

	double ang;
	private double lastRotation;

	public void rotateTo(double currentAng, int x, int y) {
		ang = Math.atan2(y - yCenter, x - xCenter);
		rotate(ang - currentAng);
	}

	public void rotate(double rot) {
		this.lastRotation = rot;
		for (int i = 0; i < count; i++) {
			double Xo = vertices[i].x;
			vertices[i].x = xCenter + ((Xo - xCenter) * Math.cos(rot) - (vertices[i].y - yCenter) * Math.sin(rot));
			vertices[i].y = yCenter + ((Xo - xCenter) * Math.sin(rot) + (vertices[i].y - yCenter) * Math.cos(rot));
		}
	}

	public void render() {
		GL11.glColor3f(r, g, b);

		GL11.glBegin(GL11.GL_TRIANGLE_FAN);

		for (int i = 0; i < count; i++) {
			GL11.glVertex2d((vertices[i].x), (vertices[i].y));
		}

		GL11.glEnd();
	}

	private CollisionInfo info = new CollisionInfo();

	public CollisionInfo collideInfo(Poly poly) {

		info.LengthSquared = -1;
		for (int j = count - 1, i = 0; i < count; j = i, i++) {
			Vector v0 = vertices[j];
			Vector v1 = vertices[i];

			Vector edge = new Vector(0, 0);
			edge.x = v1.x - v0.x;
			edge.y = v1.y - v0.y;
			this.axis = edge.perp();

			if (separatedByAxis(axis, poly)) {
				info.overlap = false;
				return info;
			}
		}

		for (int j = poly.count - 1, i = 0; i < poly.count; j = i, i++) {
			Vector v0 = poly.vertices[j];
			Vector v1 = poly.vertices[i];

			Vector edge2 = new Vector(0, 0);
			edge2.x = v1.x - v0.x;
			edge2.y = v1.y - v0.y;

			this.axis = edge2.perp();

			if (separatedByAxis(axis, poly)) {
				info.overlap = false;
				return info;
			}
		}
		info.overlap = true;
		return info;
	}

	private Vector axis = new Vector(0, 0);

	public boolean collide(Poly poly) {
		for (int j = count - 1, i = 0; i < count; j = i, i++) {
			Vector v0 = vertices[j];
			Vector v1 = vertices[i];

			Vector edge = new Vector(0, 0);
			edge.x = v1.x - v0.x;
			edge.y = v1.y - v0.y;

			axis = edge.perp();

			if (separatedByAxis(axis, poly))
				return false;
		}

		for (int j = poly.count - 1, i = 0; i < poly.count; j = i, i++) {
			Vector v0 = poly.vertices[j];
			Vector v1 = poly.vertices[i];

			Vector edge2 = new Vector(0, 0);
			edge2.x = v1.x - v0.x;
			edge2.y = v1.y - v0.y;

			axis = edge2.perp();

			if (separatedByAxis(axis, poly))
				return false;
		}
		return true;
	}

	private float min, max;

	public void calculateInterval(Vector axis2) {
		max = (float) vertices[0].dot(axis2);
		min = (float) vertices[0].dot(axis2);

		for (int i = 1; i < count; i++) {
			float d = (float) vertices[i].dot(axis2);
			if (d < min)
				min = d;
			else if (d > max)
				max = d;
		}
	}

	public boolean intervalsSeparated(float mina, float maxa, float minb, float maxb) {
		return (mina > maxb) || (minb > maxa);
	}

	private float mina, maxa;
	private float minb, maxb;

	public boolean separatedByAxis(Vector axis3, Poly poly) {
		calculateInterval(axis3);
		mina = min;
		maxa = max;
		poly.calculateInterval(axis3);
		minb = poly.min;
		maxb = poly.max;

		double d0 = maxb - mina;
		double d1 = minb - maxa;

		if (d0 < 0.0 || d1 > 0.0) {
			return true;
		}

		double overlap = (d0 < -d1) ? d0 : d1;

		double axis_length_squared = axis3.dot(axis3);
		assert (axis_length_squared > 0.00001);

		Vector sep = new Vector(0, 0);
		sep.x = axis3.x * (overlap / axis_length_squared);
		sep.y = axis3.y * (overlap / axis_length_squared);

		double sep_length_squared = sep.dot(sep);

		if (sep_length_squared < info.LengthSquared || info.LengthSquared < 0.0) {
			info.LengthSquared = sep_length_squared;
			info.mtd = sep;
		}
		return false;

	}

}