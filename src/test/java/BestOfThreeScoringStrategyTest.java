import com.dimasukimas.tennisscoreboard.model.common.MatchState;
import com.dimasukimas.tennisscoreboard.model.common.TennisPoints;
import com.dimasukimas.tennisscoreboard.model.common.OngoingMatch;
import com.dimasukimas.tennisscoreboard.model.entity.Player;
import com.dimasukimas.tennisscoreboard.service.scoring.ScoringStrategy;
import com.dimasukimas.tennisscoreboard.service.scoring.ScoringStrategyFactory;
import com.dimasukimas.tennisscoreboard.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class BestOfThreeScoringStrategyTest {
    private OngoingMatch match;
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private ScoringStrategy<OngoingMatch, Long> scoringStrategy;

    @BeforeEach
    void setUpMatch() {

        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Player").executeUpdate();
            session.persist(player1);
            session.persist(player2);
            session.getTransaction().commit();
        }

        match = new OngoingMatch(player1, player2);
        scoringStrategy = ScoringStrategyFactory.getStrategy(match.getScoringStrategyType());
    }

    @Test
    @DisplayName("Game is won if a player wins at 40-0 score")
    void playerWinsGameAtFortyLove() {
        match.setPlayer1Points(TennisPoints.FORTY.getValue());
        match.setPlayer2Points(TennisPoints.LOVE.getValue());

        scoringStrategy.calculateScore(match, match.getPlayer1().getId());

        assertEquals(1, match.getPlayer1Games());
    }

    @Test
    @DisplayName("Game is won if a player wins at 40-30 score")
    void playerWinsGameAtFortyThirty() {
        match.setPlayer1Points(TennisPoints.FORTY.getValue());
        match.setPlayer2Points(TennisPoints.THIRTY.getValue());

        scoringStrategy.calculateScore(match, match.getPlayer1().getId());

        assertEquals(1, match.getPlayer1Games());
    }

    @Test
    @DisplayName("Game is not won if a player wins at 40-40 score")
    void gameIsNotOverAfterDeuce() {
        match.setPlayer1Points(TennisPoints.FORTY.getValue());
        match.setPlayer2Points(TennisPoints.FORTY.getValue());

        scoringStrategy.calculateScore(match, match.getPlayer1().getId());

        assertSame(MatchState.ADVANTAGE, match.getMatchState());
        assertEquals(0, match.getPlayer1Games());
    }

    @Test
    @DisplayName("Game returning to deuce if a player lose at AD-40 score")
    void returnToDeuceWhenAdvantagePlayerLose() {
        match.setMatchState(MatchState.ADVANTAGE);
        match.setPlayer1Points(TennisPoints.ADVANTAGE.getValue());
        match.setPlayer2Points(TennisPoints.FORTY.getValue());

        scoringStrategy.calculateScore(match, match.getPlayer2().getId());

        assertEquals(TennisPoints.FORTY.getValue(), match.getPlayer1Points(), match.getPlayer2Points());
        assertEquals(0, match.getPlayer1Games(), match.getPlayer2Games());
    }

    @Test
    @DisplayName("Game is won if a player wins at AD-40 score")
    void winGameAfterAdvantage() {
        match.setMatchState(MatchState.ADVANTAGE);
        match.setPlayer1Points(TennisPoints.ADVANTAGE.getValue());
        match.setPlayer2Points(TennisPoints.FORTY.getValue());

        scoringStrategy.calculateScore(match, match.getPlayer1().getId());

        assertEquals(0, match.getPlayer1Points(), match.getPlayer2Points());
        assertEquals(1, match.getPlayer1Games());
    }

    @Test
    @DisplayName("Tiebreak begins at 6-6 games")
    void tiebreakBeginningCheck() {
        match.setPlayer1Games(6);
        match.setPlayer2Games(5);
        match.setPlayer1Points(TennisPoints.LOVE.getValue());
        match.setPlayer2Points(TennisPoints.FORTY.getValue());

        scoringStrategy.calculateScore(match, match.getPlayer2().getId());

        assertEquals(6, match.getPlayer2Games());
        assertSame(match.getMatchState(), MatchState.TIEBREAK);
    }

    @Test
    @DisplayName("Game is not over until 7 points at tiebreak")
    void gameNotOverUntilSevenAtTiebreak() {
        match.setPlayer1Games(6);
        match.setPlayer2Games(6);
        match.setPlayer1Points(0);
        match.setPlayer2Points(5);

        scoringStrategy.calculateScore(match, match.getPlayer2().getId());

        assertEquals(6, match.getPlayer2Games());
        assertSame(match.getMatchState(), MatchState.TIEBREAK);
    }

    @Test
    @DisplayName("Tiebreak continues if no 2-point lead at 7 points")
    void tiebreakNotOverAtSevenWithoutTwoPointLead() {
        match.setPlayer1Games(6);
        match.setPlayer2Games(6);
        match.setPlayer1Points(6);
        match.setPlayer2Points(6);

        scoringStrategy.calculateScore(match, match.getPlayer2().getId());

        assertEquals(6, match.getPlayer2Games());
        assertSame(match.getMatchState(), MatchState.TIEBREAK);
    }

    @Test
    @DisplayName("Tiebreak continues if no 2-point lead after 7 points")
    void tiebreakNotOverAfterSevenWithoutTwoPointLead() {
        match.setPlayer1Games(6);
        match.setPlayer2Games(6);
        match.setPlayer1Points(12);
        match.setPlayer2Points(12);

        scoringStrategy.calculateScore(match, match.getPlayer2().getId());

        assertEquals(6, match.getPlayer2Games());
        assertSame(match.getMatchState(), MatchState.TIEBREAK);
    }

    @Test
    @DisplayName("Tiebreak won if player has 2-point lead at 7 points")
    void tiebreakWonAtSevenWithTwoPointLead() {
        match.setPlayer1Games(6);
        match.setPlayer2Games(6);
        match.setPlayer1Points(5);
        match.setPlayer2Points(6);
        match.setMatchState(MatchState.TIEBREAK);

        scoringStrategy.calculateScore(match, match.getPlayer2().getId());

        assertEquals(1, match.getPlayer2Sets());
        assertSame(match.getMatchState(), MatchState.NORMAL);
    }

    @Test
    @DisplayName("Tiebreak won if player has 2-point lead after 7 points")
    void tiebreakWonAfterSevenWithTwoPointLead() {
        match.setPlayer1Games(6);
        match.setPlayer2Games(6);
        match.setPlayer1Points(12);
        match.setPlayer2Points(13);
        match.setMatchState(MatchState.TIEBREAK);

        scoringStrategy.calculateScore(match, match.getPlayer2().getId());

        assertEquals(1, match.getPlayer2Sets());
        assertSame(match.getMatchState(), MatchState.NORMAL);
    }

    @Test
    @DisplayName("Set not won at 6 games if player has no 2-games lead ")
    void setNotWonAtSixGamesIfNoTwoGamesLead() {
        match.setPlayer1Games(5);
        match.setPlayer2Games(5);
        match.setPlayer1Points(TennisPoints.LOVE.getValue());
        match.setPlayer2Points(TennisPoints.FORTY.getValue());

        scoringStrategy.calculateScore(match, match.getPlayer2().getId());

        assertEquals(0, match.getPlayer2Sets());
    }

    @Test
    @DisplayName("Set won at 6 games if player has 2-games lead ")
    void setWonAtSixGamesAndTwoGamesLead() {
        match.setPlayer1Games(4);
        match.setPlayer2Games(5);
        match.setPlayer1Points(TennisPoints.LOVE.getValue());
        match.setPlayer2Points(TennisPoints.FORTY.getValue());

        scoringStrategy.calculateScore(match, match.getPlayer2().getId());

        assertEquals(1, match.getPlayer2Sets());
    }

    @Test
    @DisplayName("Match is finished at 2 sets")
    void matchFinishedAtTwoSets() {
        match.setPlayer2Sets(1);
        match.setPlayer1Sets(0);
        match.setPlayer2Games(5);
        match.setPlayer1Games(3);
        match.setPlayer2Points(TennisPoints.FORTY.getValue());
        match.setPlayer1Points(TennisPoints.LOVE.getValue());

        scoringStrategy.calculateScore(match, match.getPlayer2().getId());

        assertSame(MatchState.FINISHED, match.getMatchState());
    }

}
