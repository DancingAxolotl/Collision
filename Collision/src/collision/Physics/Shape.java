package collision.Physics;

public interface Shape {

	public void setSize(double width, double height);

	public void changeWidth(double width);

	public void changeHeight(double height);

	public Vector getPosition();

	public void setPosition(Vector position);

	public boolean collide(Shape other);

	public CollisionInfo collideInfo(Shape other);

	public double getX();

	public double getY();

	public void setX(double x);

	public void setY(double y);

	public void Transform(Vector newPosition, double orientation);

	public void translate(int x, int y);

	public void translateTo(int x, int y);

	public void rotate(double angle);

	public void rotateTo(int x, int y);

	public void render();

	public Poly getShape();

}
