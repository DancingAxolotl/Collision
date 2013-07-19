package collision.Physics;

import java.util.Random;

import org.lwjgl.opengl.GL11;

public class Body implements Shape {
	private Poly poly;
	private Vector position = new Vector(0, 0);
	private Algorithm1 algorithm1 = new Algorithm1();

	private double orientation;
	private double width, height;

	public Body(int x, int y, int count, int orientation, double width, double height) {
		Random r = new Random();
		this.width = width;
		this.height = height;

		getPosition().x = x;
		getPosition().y = y;

		orientation *= Math.PI;
		float rad = r.nextFloat() * 300 * 0.1f + 300 * 0.1f;

		poly = new Poly(count, rad, width, height);

		poly.Transform(getPosition(), orientation);
		poly.translate(0, 0);

	}

	public Body(int count, int[] x, int[] y, int xPos, int yPos) {
		this.width = 1;
		this.height = 1;
		getPosition().x = xPos;
		getPosition().y = yPos;

		poly = new Poly(count, x, y);

	}

	public Body() {
		Random r = new Random();

		getPosition().randomize(new Vector(800 * 0.8f, 600 * 0.8f), new Vector(800 * 0.05f, 600 * 0.05f));

		orientation = r.nextDouble() * Math.PI;
		int count = r.nextInt(3) + 3;
		float rad = r.nextFloat() * 300 * 0.1f + 300 * 0.1f;
		width = r.nextDouble() * 5;
		height = r.nextDouble() * 5;

		poly = new Poly(count, rad, width, height);

		poly.Transform(getPosition(), orientation);
		poly.translate(0, 0);

	}

	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
		poly.changeSize(this.width, this.height);
	}

	public void changeWidth(double width) {
		this.width += width;
		poly.changeSize(this.width, this.height);
	}

	public void changeHeight(double height) {
		this.height += height;
		poly.changeSize(this.width, this.height);
	}

	public boolean collide(Shape other) {
		return algorithm1.collide(getShape(), other.getShape());
	}

	public CollisionInfo collideInfo(Shape other) {
		return algorithm1.collideInfo(getShape(), other.getShape());
	}

	public double getX() {
		return poly.getxCenter();
	}

	public double getY() {
		return poly.getyCenter();
	}

	public void Transform(Vector position2, double orientation2) {
		poly.Transform(position2, orientation2);
	}

	public void translate(int x, int y) {
		poly.translate(x, y);
	}

	public void translateTo(int x, int y) {
		poly.translateTo(x, y);
	}

	public void rotate(double angle) {
		poly.rotate(Math.toRadians(angle));
	}

	public void rotateTo(int x, int y) {
		poly.rotateTo(orientation, x, y);
		orientation = poly.ang;
	}

	public void render() {
		GL11.glColor3f(poly.getColor().x, poly.getColor().y, poly.getColor().z);

		GL11.glBegin(GL11.GL_TRIANGLE_FAN);

		for (int i = 0; i < poly.getCount(); i++) {
			GL11.glVertex2d((poly.getVertices()[i].x), (poly.getVertices()[i].y));
		}

		GL11.glEnd();
	}

	public Poly getShape() {
		return poly;
	}

	public void setX(double x) {
		getPosition().x = x;
	}

	public void setY(double y) {
		getPosition().y = y;
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}
}