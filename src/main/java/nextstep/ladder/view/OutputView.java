package nextstep.ladder.view;

import nextstep.ladder.domain.Ladder;
import nextstep.ladder.domain.Line;
import nextstep.ladder.domain.Players;
import nextstep.ladder.domain.Results;

public class OutputView {

    private static final String BLANK = "     ";
    private static final String HORIZONTAL_LINE = "-----";
    private static final String VERTICAL_LINE = "|";
    private static final String TAB = "\t";

    public static void outputResult(Players players, Ladder ladder, Results results) {
        System.out.println("사다리 결과\n");

        outputPerson(players);
        outputLadder(ladder);
        outputResults(results);
    }
    private static void outputPerson(Players person) {
        person.getPlayers()
                .forEach(p -> System.out.print(p + TAB));

        System.out.println();
    }

    private static void outputLadder(Ladder ladder) {
        for (Line line : ladder.getLines()) {
            outputLines(line);
        }
    }

    private static void outputResults(Results results) {
        results.getResults()
                .forEach(r -> System.out.print(r + TAB));
    }



    private static void outputLines(Line line) {
        for (boolean bool : line.getPoints()) {
            System.out.print(VERTICAL_LINE);
            System.out.print(getLine(bool));
        }
        System.out.println(VERTICAL_LINE);
    }

    private static String getLine(boolean isBoolean) {
        if (isBoolean) {
            return HORIZONTAL_LINE;
        }
        return BLANK;

    }


}
