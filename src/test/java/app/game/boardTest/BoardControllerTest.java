package app.game.boardTest;

import app.game.board.Board;
import app.game.board.BoardController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
@ContextConfiguration(classes = {BoardController.class})
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardController boardController;

    private static Board board;
    private static UUID id;

    @BeforeAll
    public static void setUp() {
        int[][] updatedBoard = {
                {1, 2, 3, 4},
                {5, 6, 7, 8,},
                {9, 10, 0, 12},
                {13, 14, 11, 15},
        };
        id = UUID.randomUUID();
        board = new Board(id, updatedBoard);
    }

    @Test
    void shouldSearchForBoard_whenIdIsGiven() throws Exception {
        //Given
        when(boardController.getBoardById(id))
                .thenReturn(new ResponseEntity<>(board.toString(), HttpStatus.OK));

        //When
        var response = mockMvc.perform(get("/api/game/v1/board/{boardId}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Then
        assertEquals(board.toString(), response);
        verify(boardController, times(1)).getBoardById(id);
    }

    @Test
    void shouldSearchForBoards_whenEndpointIsPrompted() throws Exception {
        //Given
        when(boardController.getAllBoards())
                .thenReturn(board.toString());

        //When
        var response = mockMvc.perform(get("/api/game/v1/board/get-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Then
        assertEquals(board.toString(), response);
        verify(boardController, times(1)).getAllBoards();
    }


    @Test
    void shouldCreateABoard_whenEndpointIsHit() throws Exception {
        //Given
        int[][] updatedBoard = {
                {1, 2, 3, 4},
                {5, 6, 7, 8,},
                {9, 10, 0, 12},
                {13, 14, 11, 15},
        };
        var id = UUID.randomUUID();
        var board = new Board(id, updatedBoard);
        when(boardController.createBoard())
                .thenReturn(board.toString());
        //When
        var response = mockMvc.perform(post("/api/game/v1/board/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        //Then
        assertEquals(board.toString(), response);
        verify(boardController, times(1)).createBoard();
    }

    @ParameterizedTest
    @MethodSource(value = "deleteBoardTestCase")
    void shouldReturnProperStatus_whenBoardExists(ResponseEntity responseEntity,
                                              ResultMatcher resultMatcher) throws Exception {
        //Given
        var id = UUID.randomUUID();
        when(boardController.deleteBoard(id)).thenReturn(responseEntity);
        //When
        mockMvc.perform(delete("/api/game/v1/board/{boardId}/delete", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
        //Then
        verify(boardController, times(1)).deleteBoard(id);
        verifyNoMoreInteractions(boardController);
    }

    private static Stream<Arguments> deleteBoardTestCase() {
        return Stream.of(
                Arguments.of(ResponseEntity.notFound().build(), status().isNotFound()),
                Arguments.of(ResponseEntity.noContent().build(), status().isNoContent())
        );
    }
}
