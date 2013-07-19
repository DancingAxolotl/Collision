package collision.Physics;

public class Algorithm1 {

	private Poly firstPoly, secondPoly;
	private CollisionInfo info = new CollisionInfo();
	private Vector axis = new Vector(0, 0);

	public CollisionInfo collideInfo(Poly poly1, Poly poly2) {
		firstPoly = poly1;
		secondPoly = poly2;

		info.setLengthSquared(-1);
		for (int j = firstPoly.getCount() - 1, i = 0; i < firstPoly.getCount(); j = i, i++) {
			Vector v0 = firstPoly.getVertices()[j];
			Vector v1 = firstPoly.getVertices()[i];

			Vector edge = new Vector(0, 0);
			edge.x = v1.x - v0.x;
			edge.y = v1.y - v0.y;
			this.axis = edge.perp();

			if (separatedByAxis(axis, firstPoly, secondPoly)) {
				info.setOverlap(false);
				return info;
			}
		}

		for (int j = secondPoly.getCount() - 1, i = 0; i < secondPoly.getCount(); j = i, i++) {
			Vector v0 = secondPoly.getVertices()[j];
			Vector v1 = secondPoly.getVertices()[i];

			Vector edge2 = new Vector(0, 0);
			edge2.x = v1.x - v0.x;
			edge2.y = v1.y - v0.y;

			this.axis = edge2.perp();

			if (separatedByAxis(axis, firstPoly, secondPoly)) {
				info.setOverlap(false);
				return info;
			}
		}
		info.setOverlap(true);
		return info;
	}

	public boolean collide(Poly poly1, Poly poly2) {
		firstPoly = poly1;
		secondPoly = poly2;

		for (int j = firstPoly.getCount() - 1, i = 0; i < firstPoly.getCount(); j = i, i++) {
			Vector v0 = firstPoly.getVertices()[j];
			Vector v1 = firstPoly.getVertices()[i];

			Vector edge = new Vector(0, 0);
			edge.x = v1.x - v0.x;
			edge.y = v1.y - v0.y;

			axis = edge.perp();

			if (separatedByAxis(axis, firstPoly, secondPoly))
				return false;
		}

		for (int j = secondPoly.getCount() - 1, i = 0; i < secondPoly.getCount(); j = i, i++) {
			Vector v0 = secondPoly.getVertices()[j];
			Vector v1 = secondPoly.getVertices()[i];

			Vector edge2 = new Vector(0, 0);
			edge2.x = v1.x - v0.x;
			edge2.y = v1.y - v0.y;

			axis = edge2.perp();

			if (separatedByAxis(axis, firstPoly, secondPoly))
				return false;
		}
		return true;
	}

	public void calculateInterval(Vector axis2, Poly poly) {
		poly.setMax((float) poly.getVertices()[0].dot(axis2));
		poly.setMin((float) poly.getVertices()[0].dot(axis2));

		for (int i = 1; i < poly.getCount(); i++) {
			float d = (float) poly.getVertices()[i].dot(axis2);
			if (d < poly.getMin())
				poly.setMin(d);
			else if (d > poly.getMax())
				poly.setMax(d);
		}
	}

	public boolean intervalsSeparated(float mina, float maxa, float minb, float maxb) {
		return (mina > maxb) || (minb > maxa);
	}

	public boolean separatedByAxis(Vector axis3, Poly poly1, Poly poly) {
		float mina, maxa;
		float minb, maxb;
		calculateInterval(axis3, poly1);
		mina = poly1.getMin();
		maxa = poly1.getMax();
		calculateInterval(axis3, poly);
		minb = poly.getMin();
		maxb = poly.getMax();

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

		if (sep_length_squared < info.getLengthSquared() || info.getLengthSquared() < 0.0) {
			info.setLengthSquared(sep_length_squared);
			info.setMtd(sep);
		}
		return false;

	}

}