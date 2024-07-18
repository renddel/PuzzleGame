package app.game.boardTest;

import app.game.board.Board;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class BoardTest {
    private UUID testId;
    private Board board;

    @Before
    public void setUp() {
        testId = UUID.randomUUID();
        board = new Board(testId);
    }

    @Test
    public void shouldConstructBoardWithId_whenObjectInitialized() {
        assertNotNull("Board should be correctly instantiated with ID", board);
        assertEquals("Board ID should match the provided ID", testId, board.getId());
    }

    @Test
    public void shouldBuildCorrectGrid_whenTilesInitialized() {
        int[][] tiles = board.getBoard();
        assertNotNull("Tiles should not be null", tiles);
        assertEquals("Tiles should be of size 4x4", 4, tiles.length);
        assertEquals("Each row of tiles should be of size 4", 4, tiles[0].length);
    }

    @Test
    public void shouldDetermineThatGameNotFinished_whenGameIsNotFinished() {
        int[][] notFinished = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 0, 15}
        };
        board = new Board(testId, notFinished);
        board.determineIsGameFinished();
        assertFalse("Game should not be marked as finished", board.isGameCompleted());
    }

    @Test
    public void shouldDetermineThatGameFinished_whenGameIsFinished() {
        int[][] finishedBoard = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        board = new Board(testId, finishedBoard);
        board.determineIsGameFinished();
        assertTrue("Game should be marked as finished", board.isGameCompleted());
    }
}