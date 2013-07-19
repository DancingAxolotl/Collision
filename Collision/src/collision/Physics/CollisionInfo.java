package collision.Physics;

public class CollisionInfo {
	private double LengthSquared = 0.0;
	private boolean overlap = false;
	private Vector mtd = new Vector(0, 0);

	public double getLengthSquared() {
		return LengthSquared;
	}

	public void setLengthSquared(double lengthSquared) {
		this.LengthSquared = lengthSquared;
	}

	public boolean isOverlap() {
		return overlap;
	}

	public void setOverlap(boolean overlap) {
		this.overlap = overlap;
	}

	public Vector getMtd() {
		return mtd;
	}

	public void setMtd(Vector mtd) {
		this.mtd = mtd;
	}

}
