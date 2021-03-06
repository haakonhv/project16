package project16;

import java.util.ArrayList;

public class Event {
	int id;
	int value;
	int teamid;
	int playerid;
	float xstart;
	float ystart;
	int number;
	int gameid;
	ArrayList<Qualifier> qualifierList;
	int period;
	int minute;

	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getTeamid() {
		return teamid;
	}
	public void setTeamid(int teamid) {
		this.teamid = teamid;
	}
	public int getPlayerid() {
		return playerid;
	}
	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}
	public float getXstart() {
		return xstart;
	}
	public void setXstart(float xstart) {
		this.xstart = xstart;
	}
	public float getYstart() {
		return ystart;
	}
	public void setYstart(float ystart) {
		this.ystart = ystart;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public int getGameid() {
		return gameid;
	}
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	@Override
	public String toString() {
		return "Event [id=" + id + ", value=" + value + ", teamid=" + teamid + ", playerid=" + playerid + ", xstart="
				+ xstart + ", ystart=" + ystart + ", number=" + number + "]";
	}
	public ArrayList<Qualifier> getQualifierList() {
		return qualifierList;
	}
	public void setQualifierList(ArrayList<Qualifier> qualifierList) {
		this.qualifierList = qualifierList;
	}



}
