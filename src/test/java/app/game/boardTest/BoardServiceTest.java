package app.game.boardTest;

import app.game.board.Board;
import app.game.board.BoardCache;
import app.game.board.BoardMovementDirection;
import app.game.board.BoardService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BoardServiceTest {

    @Mock
    private BoardCache boardCache;

    @InjectMocks
    private BoardService boardService;

    @Captor
    private ArgumentCaptor<Board> boardCaptor;

    private UUID boardId;
    private Board board;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        boardId = UUID.randomUUID();
        board = mock(Board.class);
        when(board.getId()).thenReturn(boardId);
        when(board.toString()).thenReturn("Test Board");
    }

    @Test
    public void shouldCreateAndSave_whenGivenAboard() {
        var board = boardService.createAndSaveBoard();

        verify(boardCache).insertBoard(boardCaptor.capture());
        var capturedBoard = boardCaptor.getValue();
        assertNotNull(capturedBoard.getId());
        assertEquals("Captured board is malformed", 4, capturedBoard.getBoard().length);
    }

    @Test
    public void shouldGetAllBoards_whenGettingAllBoards() {
        when(boardCache.getAllBoards()).thenReturn(List.of(board));

        var result = boardService.getAllBoardsAsString();
        assertNotNull("Result should not be null", result);
        assertTrue("Result should contain the board string", result.contains("Test Board"));
    }

    @Test
    public void shouldRetrieveABoard_whenRetrievingBoardById() {
        when(boardCache.getBoard(boardId)).thenReturn(Optional.of(board));

        var board = boardService.getBoard(boardId);
        assertTrue("Board should be found", board.isPresent());
        assertEquals("Board details should match", "Test Board", board.get());
    }

    @Test
    public void shouldRemoveBoard_whenRemovingById() {
        when(boardCache.removeBoard(boardId)).thenReturn(true);

        boolean result = boardService.removeBoard(boardId);
        assertTrue("Board should be removed successfully", result);
        verify(boardCache).removeBoard(boardId);
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateBoard() {
        boardService.updateBoard(boardId, BoardMovementDirection.UP);
    }
}
