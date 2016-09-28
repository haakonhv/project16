package project16;

public class Game {
	int id;
	int home_team_id;
	int away_team_id;
	int matchday;
	int season_id;

	public int getSeason_id() {
		return season_id;
	}

	public void setSeason_id(int season_id) {
		this.season_id = season_id;
	}

	public int getMatchday() {
		return matchday;
	}

	public void setMatchday(int matchday) {
		this.matchday = matchday;
	}

	public int getHome_team_id() {
		return home_team_id;
	}

	public void setHome_team_id(int home_team_id) {
		this.home_team_id = home_team_id;
	}

	public int getAway_team_id() {
		return away_team_id;
	}

	public void setAway_team_id(int away_team_id) {
		this.away_team_id = away_team_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
