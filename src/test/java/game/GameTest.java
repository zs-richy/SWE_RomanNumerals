package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game testGame;

    @Test
    void getFieldByCoord() {
        testGame = new Game();
        assertEquals("I", testGame.getFieldByCoord(0,0));
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
        assertFalse(testGame.canMove(Direction.RIGHT));
        testGame.setCurrentX(0);
        testGame.setCurrentY(0);
        assertFalse(testGame.canMove(Direction.LEFT));
        assertFalse(testGame.canMove(Direction.UP));
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
        testGame.setCurrentX(6);
        testGame.setCurrentY(6);
        testGame.move(Direction.RIGHT);
        assertEquals(6,testGame.getCurrentX());
        testGame.move(Direction.DOWN);
        assertEquals(6,testGame.getCurrentY());
        testGame.setCurrentX(0);
        testGame.setCurrentY(0);
        testGame.move(Direction.LEFT);
        assertEquals(0,testGame.getCurrentX());
        testGame.move(Direction.UP);
        assertEquals(0,testGame.getCurrentY());
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
        testGame.setState("XL");
        testGame.setStateCounter(39);
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
        assertFalse(testGame.isLost());
        testGame.updateLoseCondition();
        assertTrue(testGame.isLost());
    }

    @Test
    void isWon() {
        testGame = new Game();
        assertFalse(testGame.isWon());
        testGame.setStateCounter(39);
        testGame.updateWinCondition();
        assertFalse(testGame.isWon());
        testGame.setStateCounter(40);
        testGame.updateWinCondition();
        assertTrue(testGame.isWon());
    }

}