package collision.Physics;

import java.util.Random;

public class Body {
	public Poly poly;
	public Vector position = new Vector(0, 0);;

	public double orientation;
	public double width, height;

	public Body(int x, int y, int count, int orientation, double width, double height) {
		Random r = new Random();
		this.width = width;
		this.height = height;
		position.x = x;
		position.y = y;

		orientation *= Math.PI;
		float rad = r.nextFloat() * 300 * 0.1f + 300 * 0.1f;

		poly = new Poly(count, rad, width, height);

		poly.Transform(position, orientation);
		poly.translate(0, 0);

	}

	public Body() {
		Random r = new Random();

		position.randomize(new Vector(800 * 0.8f, 600 * 0.8f), new Vector(800 * 0.05f, 600 * 0.05f));

		orientation = r.nextDouble() * Math.PI;
		int count = r.nextInt(3) + 3;
		float rad = r.nextFloat() * 300 * 0.1f + 300 * 0.1f;
		width = r.nextDouble();
		height = r.nextDouble();

		poly = new Poly(count, rad, width, height);

		poly.Transform(position, orientation);
		poly.translate(0, 0);

	}
	
	public void setSize(double width, double height){
		this.width = width;
		this.height = height;
		poly.changeSize(this.width, this.height);
	}
	
	public void changeWidth(double width){
		this.width += width;
		poly.changeSize(this.width, this.height);
	}
	
	public void changeHeight(double height){
		this.height += height;
		poly.changeSize(this.width, this.height);
	}

	public boolean collide(Body body) {
		Poly otherPoly = body.poly;
		return poly.collide(otherPoly);
	}

	public CollisionInfo collideInfo(Body other) {
		Poly otherPoly = other.poly;
		return poly.collideInfo(otherPoly);
	}

	public double getX() {
		return position.x;
	}

	public double getY() {
		return position.y;
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
		poly.render();
	}

}