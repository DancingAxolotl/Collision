package collision;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import collision.Physics.*;

public class Collider {

	private static Body poly = new Body();
	private static Body polyTheSecond = new Body();
	private long lastChange;
	private CollisionInfo colInfo;
	private boolean secondSelected = false;

	public void run() {

		setUpDisplay();

		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			input();
			
			if (!secondSelected) {
				colInfo = poly.collideInfo(polyTheSecond);

				if (colInfo.overlap) {
					poly.position.x = polyTheSecond.poly.xCenter;
					poly.position.y = polyTheSecond.poly.yCenter;

					poly.position.x = poly.poly.xCenter + colInfo.mtd.x;
					poly.position.y = poly.poly.yCenter + colInfo.mtd.y;
					poly.translateTo((int) poly.position.x, (int) poly.position.y);

				}
			} else {
				colInfo = polyTheSecond.collideInfo(poly);

				if (colInfo.overlap) {
					polyTheSecond.position.x = poly.poly.xCenter;
					polyTheSecond.position.y = poly.poly.yCenter;

					polyTheSecond.position.x = polyTheSecond.poly.xCenter + colInfo.mtd.x;
					polyTheSecond.position.y = polyTheSecond.poly.yCenter + colInfo.mtd.y;
					polyTheSecond.translateTo((int) polyTheSecond.position.x, (int) polyTheSecond.position.y);

				}
			}

			poly.render();
			polyTheSecond.render();

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}

	private void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			Display.destroy();
			System.exit(0);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (Sys.getTime() - lastChange >= 300) {
				if (secondSelected) {
					polyTheSecond.changeHeight(0.05);
				} else {
					poly.changeHeight(0.05);
				}
				lastChange = Sys.getTime();
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if (Sys.getTime() - lastChange >= 300) {
				if (secondSelected) {
					polyTheSecond.changeHeight(-0.05);
				} else {
					poly.changeHeight(-0.05);
				}
				lastChange = Sys.getTime();
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (Sys.getTime() - lastChange >= 300) {
				if (secondSelected) {
					polyTheSecond.changeWidth(0.05);
				} else {
					poly.changeWidth(0.05);
				}
				lastChange = Sys.getTime();
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (Sys.getTime() - lastChange >= 300) {
				if (secondSelected) {
					polyTheSecond.changeWidth(-0.05);
				} else {
					poly.changeWidth(-0.05);
				}
				lastChange = Sys.getTime();
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			if (Sys.getTime() - lastChange >= 300) {
				if (secondSelected) {
					polyTheSecond.setSize(1.0, 1.0);
				} else {
					poly.setSize(1.0, 1.0);
				}
				lastChange = Sys.getTime();
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (Sys.getTime() - lastChange >= 300) {
				if (secondSelected) {
					polyTheSecond.rotate(10);
				} else {
					poly.rotate(10);
				}
				lastChange = Sys.getTime();
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (Sys.getTime() - lastChange >= 300) {
				if (secondSelected) {
					polyTheSecond.rotate(-10);
				} else {
					poly.rotate(-10);
				}
				lastChange = Sys.getTime();
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (Sys.getTime() - lastChange >= 300) {
				if (secondSelected) {
					polyTheSecond.poly.changeCount(1);
				} else {
					poly.poly.changeCount(1);
				}
				lastChange = Sys.getTime();
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (Sys.getTime() - lastChange >= 300) {
				if (secondSelected) {
					polyTheSecond.poly.changeCount(-1);
				} else {
					poly.poly.changeCount(-1);
				}
				lastChange = Sys.getTime();
			}
		}
		if (!secondSelected) {
			poly.translateTo(Mouse.getX(), Mouse.getY());
		} else {
			polyTheSecond.translateTo(Mouse.getX(), Mouse.getY());
		}
		if (Mouse.isButtonDown(0)) {
			if (Sys.getTime() - lastChange >= 300) {
				if (!secondSelected) {
					Mouse.setCursorPosition(polyTheSecond.poly.xCenter, polyTheSecond.poly.yCenter);
					secondSelected = true;
				} else {
					Mouse.setCursorPosition(poly.poly.xCenter, poly.poly.yCenter);
					secondSelected = false;
				}
				lastChange = Sys.getTime();
			}
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
