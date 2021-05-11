package game;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void testToString() {
        Result testResult = new Result("Test", 0, 0);
        assertEquals("Result{name='Test', state=0, time=0.0, date='"
                + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.")) + "'}",
                testResult.toString());
    }

    @Test
    void compareTo() {
        Result testResult1 = new Result("Test", 0, 0);
        Result testResult2 = new Result("Test", 1, 0);
        Result testResult3 = new Result("Test", 1, 1.0);
        Result testResult4 = new Result("Test", 1,1.0);
        assertEquals(-1,testResult1.compareTo(testResult2));
        assertEquals(1,testResult2.compareTo(testResult3));
        assertEquals(0,testResult3.compareTo(testResult4));
    }
}