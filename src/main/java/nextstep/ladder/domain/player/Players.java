package nextstep.ladder.domain.player;

import java.util.Collections;
import java.util.List;

import nextstep.ladder.domain.line.Line;
import nextstep.ladder.util.CustomCollectionUtils;

public class Players {

	private final List<Player> players;

	private Players(List<Player> players) {
		this.players = players;
	}

	public static Players ofPlayers(List<Player> players) {
		validatePlayerNumIsLargerThanZero(players);
		validatePlayerNameDistinct(players);
		validatePlayerPositionNotExceedSize(players);
		return new Players(players);
	}

	private static void validatePlayerNumIsLargerThanZero(List<Player> players) {
		if (CustomCollectionUtils.isNull(players) || CustomCollectionUtils.isEmpty(players)) {
			throw new IllegalArgumentException(
				"Players count should be larger than one. Otherwise, you can't play this game XD");
		}
	}

	private static void validatePlayerNameDistinct(List<Player> players) {
		if (getDistinctNameCount(players) != players.size()) {
			throw new IllegalArgumentException("please double check whether player name is not distinct.");
		}
	}

	private static void validatePlayerPositionNotExceedSize(List<Player> players) {
		if (players.stream().anyMatch(player -> ! player.validatePosition(players.size()))) {
			throw new IllegalArgumentException("please check your player position. It cannot exceed overall size.");
		}
	}

	public int getSize() {
		return players.size();
	}

	public static Long getDistinctNameCount(List<Player> players) {
		return players.stream()
			.map(Player::getName)
			.distinct()
			.count();
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	public String printPlayers() {
		StringBuilder builder = new StringBuilder();
		players.forEach(player -> builder.append(String.format("%-6s", player.getName())));
		return builder.toString();
	}

	public Players determinePlayersPositionResult(Line line) {
		this.players.forEach(player -> {
			//TODO: code smell이 나는데 뭔가 어떻게 바꿔야 할 지 생각이 나지를 않는다.
			int newPoint = line.getPoints().movePosition(player.getPosition());
			player.updatePosition(newPoint);
		});
		return this;
	}

	public Player playerPrizeMapFactory(int prizeIndex) {
		return players.stream()
			.filter(player -> player.getPosition() == prizeIndex)
			.reduce((a, b) -> b)
			.orElseThrow(() -> new IllegalArgumentException("no user"));
	}

	public Player findPlayerByName(String playerName) {
		return players.stream()
			.filter(player -> player.getName().equals(playerName))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
	}
}
