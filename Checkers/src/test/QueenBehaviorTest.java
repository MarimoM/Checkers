package test;

import behaviour.QueenBehavior;
import behaviour.SimpleBehavior;
import components.Board;
import components.Draught;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class QueenBehaviorTest {
    private Draught testDraught = null;
    private Draught testEnemy = null;

    void Setup() {
        Draught draught = new Draught(0, 0, true, new QueenBehavior());
        Draught enemy = new Draught(100, 100, false, new SimpleBehavior());

        Board.allDraughts.add(draught);
        Board.allDraughts.add(enemy);

        testDraught = draught;
        testEnemy = enemy;
    }

    @Test
    void shouldSetEnemy_setEnemy_ReturnOne(){
        Setup();
        testDraught.setEnemies();
        Assert.assertEquals(1, testDraught.getEnemies().size());
    }

    @Test
    void shouldCheckDestination_checkDestination_ReturnTrue() {
        Setup();
        int destinationX = 150;
        int destinationY = 150;
        boolean actual = testDraught.checkDestination(destinationX, destinationY, testEnemy.getX(), testEnemy.getY());
        Assert.assertTrue(actual);
    }

    @Test
    void shouldCheckDirection_checkDirection_ReturnTrue() {
        Setup();
        int clickX = 150;
        int clickY = 150;
        boolean actual = testDraught.checkDirection(clickX, clickY);
        Assert.assertTrue(actual);
    }

    @Test
    void shouldCheckDirection_checkDirection_ReturnFalse() {
        Setup();
        int clickX = -10;
        int clickY = -10;
        boolean actual = testDraught.checkDirection(clickX, clickY);
        Assert.assertTrue(actual);
    }
}