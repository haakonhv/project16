package project16;

public class Corner {
	int corner_id;
	int inswing=0; //bin
	int outswing=0; //bin
	int straight=0; //bin
	int right=0;
	int left=0;
	float length;
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	@Override
	public String toString() {
		return "Corner [right=" + right + ", left=" + left + ", length=" + length + ", first_post=" + first_post
				+ ", far_post=" + far_post + ", event_id=" + event_id + "]";
	}
	public int getInswing() {
		return inswing;
	}
	public void setInswing(int inswing) {
		this.inswing = inswing;
	}
	public int getOutswing() {
		return outswing;
	}
	public void setOutswing(int outswing) {
		this.outswing = outswing;
	}
	public int getStraight() {
		return straight;
	}
	public void setStraight(int straight) {
		this.straight = straight;
	}
	float koord_x;
	float koord_y;
	int first_post; //binary
	int far_post; //binary
	int event_id; //foreign key
	public int getCorner_id() {
		return corner_id;
	}
	public void setCorner_id(int corner_id) {
		this.corner_id = corner_id;
	}
	public float getKoord_x() {
		return koord_x;
	}
	public void setKoord_x(float koord_x) {
		this.koord_x = koord_x;
	}
	public float getKoord_y() {
		return koord_y;
	}
	public void setKoord_y(float koord_y) {
		this.koord_y = koord_y;
	}
	public int getFirst_post() {
		return first_post;
	}
	public void setFirst_post(int first_post) {
		this.first_post = first_post;
	}
	public int getFar_post() {
		return far_post;
	}
	public void setFar_post(int far_post) {
		this.far_post = far_post;
	}

	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

}
