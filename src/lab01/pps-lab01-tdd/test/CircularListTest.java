import lab01.tdd.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test suite for testing the CircularList implementation
 */
public class CircularListTest {

    private CircularList circularList;
    private StrategyFactory evenStrategyFactory;
    private StrategyFactory multipleStrategyFactory;
    private StrategyFactory equalsStrategyFactory;


    @BeforeEach
    void beforeEach() {
        this.circularList = new CircularListImpl(new ArrayList<>());
        this.evenStrategyFactory = new EvenStrategyFactory();
        this.multipleStrategyFactory = new MultipleOfStrategyFactory(2);
        this.equalsStrategyFactory = new EqualsStrategyFactory(2);
    }

    @Test
    void testListAddAndSize() {
        this.circularList.add(1);
        assertEquals(1, this.circularList.size());
    }

    @Test
    void testAddMoreElements() {
        this.circularList.add(1);
        this.circularList.add(2);
        this.circularList.add(3);
        assertEquals(3, this.circularList.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(this.circularList.isEmpty());
    }

    @Test
    void testIsNotEmpty() {
        this.circularList.add(1);
        assertFalse(this.circularList.isEmpty());
    }

    @Test
    void testNextIsNull() {
       assertEquals(Optional.empty(), this.circularList.next());
    }

    @Test
    void testNextOneElement() {
        this.circularList.add(1);
        assertEquals(Optional.of(1), this.circularList.next());
    }

    @Test
    void testNextCircularOneElement() {
        this.circularList.add(1);
        this.circularList.next();
        assertEquals(Optional.of(1), this.circularList.next());
    }

    @Test
    void testNextTwoElement() {
        this.circularList.add(1);
        this.circularList.add(2);
        this.circularList.next();
        assertEquals(Optional.of(2), this.circularList.next());
    }

    @Test
    void testNextCircularElement() {
        this.circularList.add(1);
        this.circularList.add(2);
        this.circularList.add(3);
        this.circularList.next();
        this.circularList.next();
        this.circularList.next();
        assertEquals(Optional.of(1), this.circularList.next());
    }

    @Test
    void testPreviousIsNull() {
        assertEquals(Optional.empty(), this.circularList.previous());
    }

    @Test
    void testPreviousOneElement() {
        this.circularList.add(1);
        assertEquals(Optional.of(1), this.circularList.previous());
    }

    @Test
    void testPreviousCircularOneElement() {
        this.circularList.add(1);
        this.circularList.previous();
        this.circularList.previous();
        assertEquals(Optional.of(1), this.circularList.previous());
    }

    @Test
    void testCircularTwoElement() {
        this.circularList.add(1);
        this.circularList.add(2);
        this.circularList.previous();
        assertEquals(Optional.of(1), this.circularList.previous());
    }

    @Test
    void testPreviousCircularElement() {
        this.circularList.add(1);
        this.circularList.add(2);
        this.circularList.add(3);
        this.circularList.previous();
        this.circularList.previous();
        this.circularList.previous();
        assertEquals(Optional.of(3), this.circularList.previous());
    }

    @Test
    void testPreviousNextCircularElement() {
        this.circularList.add(1);
        this.circularList.add(2);
        this.circularList.add(3);
        this.circularList.next();
        this.circularList.next();
        assertEquals(Optional.of(1), this.circularList.previous());
    }

    @Test
    void testReset() {
        this.circularList.add(1);
        this.circularList.add(2);
        this.circularList.next();
        this.circularList.reset();
        assertEquals(Optional.of(1), this.circularList.next());

    }

    @Test
    void testNextEvenStrategy() {
        this.circularList.add(1);
        this.circularList.add(2);
        assertEquals(Optional.of(2),this.circularList.next(this.evenStrategyFactory.createStrategy()));
    }

    @Test
    void testDubleNextEvenStrategy() {
        this.circularList.add(1);
        this.circularList.add(2);
        this.circularList.add(4);
        this.circularList.next(this.evenStrategyFactory.createStrategy());
        assertEquals(Optional.of(4),this.circularList.next(this.evenStrategyFactory.createStrategy()));
    }

    @Test
    void testNotNextEvenStrategy() {
        this.circularList.add(1);
        this.circularList.add(3);
        assertEquals(Optional.empty(),this.circularList.next(this.evenStrategyFactory.createStrategy()));
    }

    @Test
    void testNextMultipleStrategy() {
        this.circularList.add(1);
        this.circularList.add(2);
        assertEquals(Optional.of(2),this.circularList.next(this.multipleStrategyFactory.createStrategy()));
    }

    @Test
    void testDubleNextMultipleStrategy() {
        this.circularList.add(1);
        this.circularList.add(2);
        this.circularList.add(4);
        this.circularList.next(this.multipleStrategyFactory.createStrategy());
        assertEquals(Optional.of(4),this.circularList.next(this.multipleStrategyFactory.createStrategy()));
    }

    @Test
    void testNotNextMultipleStrategy() {
        this.circularList.add(1);
        this.circularList.add(3);
        assertEquals(Optional.empty(),this.circularList.next(this.multipleStrategyFactory.createStrategy()));
    }

    @Test
    void testNextEqualsStrategy() {
        this.circularList.add(1);
        this.circularList.add(2);
        assertEquals(Optional.of(2),this.circularList.next(this.equalsStrategyFactory.createStrategy()));
    }

    @Test
    void testDubleNextEqualsStrategy() {
        this.circularList.add(1);
        this.circularList.add(2);
        this.circularList.add(2);
        this.circularList.next(this.equalsStrategyFactory.createStrategy());
        assertEquals(Optional.of(2),this.circularList.next(this.equalsStrategyFactory.createStrategy()));
    }

    @Test
    void testNotNextEqualsStrategy() {
        this.circularList.add(1);
        this.circularList.add(3);
        assertEquals(Optional.empty(),this.circularList.next(this.equalsStrategyFactory.createStrategy()));
    }

}
