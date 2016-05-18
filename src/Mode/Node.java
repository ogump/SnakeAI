package Mode;


public class Node{	
	private int G = 0;
	private int H = 0;
	private Node father;
	private int x;
	private int y;
	public Node(){}
	public Node(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public int getF() {
		return G+H;
	}

	public int getG() {
		return G;
	}
	public void setG(int g) {
		G = g;
	}
	public int getH() {
		return H;
	}
	public void setH(int h) {
		H = h;
	}
	public Node getFather() {
		return father;
	}
	public void setFather(Node father) {
		this.father = father;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return x+"-"+y;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj)
			return true;
		if (obj instanceof Node) {
			Node antherNode = (Node) obj;
			if (this.x == antherNode.x && this.y == antherNode.y)
				return true;
		}
		return false;
	}
}