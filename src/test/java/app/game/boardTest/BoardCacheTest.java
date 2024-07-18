package app.game.boardTest;

import app.game.board.Board;
import app.game.board.BoardCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BoardCacheTest {

    @InjectMocks
    private BoardCache boardCache;

    @Mock
    private Board board;

    private UUID boardId;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        boardId = UUID.randomUUID();
        when(board.getId()).thenReturn(boardId);
    }

    @After
    public void tearDown() {
        var boards = boardCache.getAllBoards();
        for (Board board : boards) {
            boardCache.removeBoard(board.getId());
        }
    }

    @Test
    public void shouldReceiveAllBoardsEmpty_whenBoardsEmpty() {
        var boards = boardCache.getAllBoards();
        assertTrue("Board list should be empty initially", boards.isEmpty());
    }

    @Test
    public void shouldNotFindBoard_whenIdNotPresent() {
        var retrievedBoard = boardCache.getBoard(boardId);
        assertFalse("Board should not be present", retrievedBoard.isPresent());
    }

    @Test
    public void shouldRetrieveAllBoards_whenSeveralBoardsPresent() {
        boardCache.insertBoard(board);
        var anotherBoard = new Board(UUID.randomUUID());
        boardCache.insertBoard(anotherBoard);

        var boards = boardCache.getAllBoards();
        assertEquals("There should be two boards in the cache", 2, boards.size());
        assertTrue("Boards should contain the inserted boards",
                boards.contains(board) && boards.contains(anotherBoard));
    }

    @Test
    public void shouldInsertAndRetrieveBoardToCache_whenBoardIsPresent() {
        boardCache.insertBoard(board);
        var retrievedBoard = boardCache.getBoard(boardId);
        assertTrue("Board should be present", retrievedBoard.isPresent());
        assertEquals("Retrieved board should be the same as the inserted one", board, retrievedBoard.get());
    }

    @Test
    public void shouldRemoveBoardFromCache_whenBoardIsPresent() {
        boardCache.insertBoard(board);
        assertTrue("Remove should succeed", boardCache.removeBoard(boardId));
        var retrievedBoard = boardCache.getBoard(boardId);
        assertFalse("Board should no longer be present after removal", retrievedBoard.isPresent());
    }

    @Test
    public void shouldNotRemoveBoard_whenBoardNotPresent() {
        assertFalse("Remove should fail as board is not present", boardCache.removeBoard(boardId));
    }
}