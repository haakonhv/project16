package project16;

public class Player {

	@Override
	public String toString() {
		return "Player [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", real_position="
				+ real_position + ", country=" + country + ", birth_year=" + birth_year + ", height=" + height + "]";
	}

	int id;
	String first_name;
	String last_name;
	String real_position;
	String country;
	int birth_year;
	int height;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getReal_position() {
		return real_position;
	}

	public void setReal_position(String real_position) {
		this.real_position = real_position;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(int birth_year) {
		this.birth_year = birth_year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
