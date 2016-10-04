package project16;

public class Corner {
	int corner_id;
	String ballbane;
	public int getCorner_id() {
		return corner_id;
	}
	public void setCorner_id(int corner_id) {
		this.corner_id = corner_id;
	}
	public String getBallbane() {
		return ballbane;
	}
	public void setBallbane(String ballbane) {
		this.ballbane = ballbane;
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
	public int getSecond_pos() {
		return second_pos;
	}
	public void setSecond_pos(int second_pos) {
		this.second_pos = second_pos;
	}
	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
	float koord_x;
	float koord_y;
	int first_post; //binary
	int second_pos; //binary
	int event_id; //foreign key
}
