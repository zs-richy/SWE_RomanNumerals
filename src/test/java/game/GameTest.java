package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game testGame;

    @Test
    void getFieldXY() {
        testGame = new Game();
        assertEquals("I", testGame.getFieldXY(0,0));
    }

    @Test
    void canMove() {
        testGame = new Game();
        assertTrue(testGame.canMove(Direction.UP));
        assertTrue(testGame.canMove(Direction.RIGHT));
        assertTrue(testGame.canMove(Direction.DOWN));
        assertTrue(testGame.canMove(Direction.LEFT));
        testGame.setCurrentX(6);
        testGame.setCurrentY(6);
        assertFalse(testGame.canMove(Direction.DOWN));
    }

    @Test
    void move() {
        testGame = new Game();
        testGame.move(Direction.UP);
        assertEquals(2,testGame.getCurrentY());
        testGame.move(Direction.DOWN);
        assertEquals(3, testGame.getCurrentY());
        testGame.move(Direction.LEFT);
        assertEquals(2, testGame.getCurrentX());;
        testGame.move(Direction.RIGHT);
        assertEquals(3, testGame.getCurrentX());
    }

    @Test
    void updateState() {
        testGame = new Game();
        testGame.move(Direction.UP);
        testGame.move(Direction.DOWN);
        assertEquals("", testGame.getState());
    }

    @Test
    void isCorrectState() {
        testGame = new Game();
        testGame.setState("I");
        assertTrue(testGame.isCorrectState());
    }

    @Test
    void updateWinCondition() {
        testGame = new Game();
        testGame.setStateCounter(40);
        testGame.updateWinCondition();
        assertTrue(testGame.isWon());
    }

    @Test
    void setLoseCondition() {
        testGame = new Game();
        testGame.updateLoseCondition();
        assertTrue(testGame.isLost());
    }

    @Test
    void calculateResult() {
        testGame = new Game();
        testGame.setStartTimer();
        testGame.setEndTime(testGame.getStartTime());
        testGame.calculateResult();
        assertEquals(0, testGame.getResult().getTime());
        assertEquals(0, testGame.getResult().getState());
    }
}