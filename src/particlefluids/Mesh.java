package particlefluids;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.PickSegment;
import javax.vecmath.Point3d;
import javax.vecmath.Point3i;
import javax.vecmath.Tuple3i;
import javax.vecmath.Vector3d;

import org.j3d.geom.IntersectionUtils;

import com.jogamp.newt.event.NEWTEventFiFo;
import com.sun.j3d.utils.behaviors.picking.Intersect;
import com.sun.j3d.utils.picking.PickIntersection;

import pqp.PQP_Model;

public class Mesh {
	
	private int nVertices;
	private int nFaces;

	private List<Point3d> vertices = new ArrayList<>();
	private List<Tuple3i> faces = new ArrayList<>();
	/** Unit Normal of each face */
	private List<Vector3d> normals = new ArrayList<>();
	
	//private float[] vFlatten;
	//private float[] indsFlatten;
	
	/**
	 * Construct a mesh based on vertices and faces. The mesh needs to be orientable and the 
	 * orientation of each face is towards the exterior of the mesh.
	 * @param vertices
	 * @param faces
	 */
	Mesh(List<Point3d> vertices, List<Tuple3i> faces) {
		this.vertices = vertices;
		this.faces = faces;
		
		nVertices = vertices.size();
		nFaces = faces.size();
		
		for (Tuple3i face : faces) {
			Vector3d v1 = new Vector3d();
			v1.sub(vertices.get(face.x), vertices.get(face.y));
			Vector3d v2 = new Vector3d();
			v2.sub(vertices.get(face.x), vertices.get(face.z));
			Vector3d normal = new Vector3d();
			normal.cross(v1, v2);
			normal.normalize();
			normals.add(normal);
		}
	}
	
	/**
	 * Naive implementation of checking if the segment intersects the mesh.
	 * @return the distance between p1 and intersection. -1 if no intersection found.
	 */
	@SuppressWarnings("deprecation")
	public Collision segmentIntersects(Point3d p1, Point3d p2) {
		PickSegment seg = new PickSegment(p1, p2);
		double minDist = Double.MAX_VALUE;
		Vector3d normal = new Vector3d(); // normal of the triangle that collides;
		for (int i = 0; i < faces.size(); i++) {
			Tuple3i face = faces.get(i);
			//IntersectionUtils intersect = new IntersectionUtils();
			Point3d[] tri = new Point3d[3];
			tri[0] = vertices.get(face.x);
			tri[1] = vertices.get(face.y);
			tri[2] = vertices.get(face.z);
			double[] dist = new double[1];
			dist[0] = 0;
			boolean intersect = Intersect.segmentAndTriangle(seg, tri, 0, dist);
			if (intersect && minDist > dist[0]) {
				minDist = dist[0];
				normal = normals.get(i);
			}
		}
		return (minDist == Double.MAX_VALUE ) ? null: new Collision(p1, p2, minDist, normal);
		
	}
	
	public static Mesh CubeMesh(Point3d lowV, Point3d highV) {
		List<Point3d> verts = new ArrayList<>();
		verts.add(lowV);
		verts.add(new Point3d(lowV.x, lowV.y, highV.z));
		verts.add(new Point3d(lowV.x, highV.y, lowV.z));
		verts.add(new Point3d(lowV.x, highV.y, highV.z));
		verts.add(new Point3d(highV.x, lowV.y, lowV.z));
		verts.add(new Point3d(highV.x, lowV.y, highV.z));
		verts.add(new Point3d(highV.x, highV.y, lowV.z));
		verts.add(highV);
		
		List<Tuple3i> faces = new ArrayList<>();
		faces.add(new Point3i(0, 1, 2));
		faces.add(new Point3i(2, 1, 3));
		faces.add(new Point3i(0, 4, 5));
		faces.add(new Point3i(0, 5, 1));
		faces.add(new Point3i(0, 2, 4));
		faces.add(new Point3i(2, 6, 4));
		faces.add(new Point3i(1, 5, 7));
		faces.add(new Point3i(1, 7, 3));
		faces.add(new Point3i(2, 7, 6));
		faces.add(new Point3i(2, 3, 7));
		faces.add(new Point3i(4, 7, 5));
		faces.add(new Point3i(4, 6, 7));
		
		return new Mesh(verts, faces);
	}
	
}


