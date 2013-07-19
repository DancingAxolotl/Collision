package collision;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import collision.Physics.*;

public class Collider {
	private Shape poly = new Body();
	private ArrayList<Shape> shapes = new ArrayList<Shape>(16);
	private long lastChange = 0;
	private CollisionInfo colInfo;
	private int index = 0;
	private int selectedIndex;

	public void run() {

		setUpDisplay();

		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			input();

			logic();

			poly.render();
			for (Shape polyTheSecond : shapes) {
				polyTheSecond.render();
			}

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}

	private void logic() {
		for (Shape polyTheSecond : shapes) {
			colInfo = poly.collideInfo(polyTheSecond);

			if (colInfo.isOverlap()) {
				poly.setX(polyTheSecond.getX());
				poly.setY(polyTheSecond.getY());

				poly.setX(poly.getShape().getxCenter() + colInfo.getMtd().x);
				poly.setY(poly.getShape().getyCenter() + colInfo.getMtd().y);
				poly.translateTo((int) poly.getPosition().x, (int) poly.getPosition().y);

			}
		}

	}

	private void input() {
		if (Sys.getTime() - lastChange >= 300) {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				Display.destroy();
				System.exit(0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
				shapes.add(index, new Body());
				index++;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				poly.changeHeight(0.05);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				poly.changeHeight(-0.05);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				poly.changeWidth(0.05);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				poly.changeWidth(-0.05);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				poly.setSize(1.0, 1.0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				poly.rotate(10);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				poly.rotate(-10);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				poly.getShape().changeCount(1);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				poly.getShape().changeCount(-1);
			}

			if (Mouse.isButtonDown(0)) {

				replace();

				Mouse.setCursorPosition(poly.getShape().getxCenter(), poly.getShape().getyCenter());
			}

			lastChange = Sys.getTime();

		}

		poly.translateTo(Mouse.getX(), Mouse.getY());
	}

	private void replace() {
		if (selectedIndex >= shapes.size()) {
			selectedIndex = 0;
		} else {
			Shape tempBody = poly;
			poly = shapes.get(selectedIndex);
			shapes.set(selectedIndex, tempBody);
			selectedIndex++;
		}
	}

	public static void main(String[] args) {
		Collider collider = new Collider();
		collider.run();
	}

	private static void setUpDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setTitle("A collision test!");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
}
