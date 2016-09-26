package project16;

import java.util.ArrayList;
import java.util.List;

public class Qualifier {

	int id;
	int qualifier_id;
	List<String> values;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQualifier_id() {
		return qualifier_id;
	}
	public void setQualifier_id(int qualifier_id) {
		this.qualifier_id = qualifier_id;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}

}
