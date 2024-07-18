package app.game.board;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping(value = "/v1/board/create")
    @ResponseStatus(CREATED)
    public String createBoard() {
        return boardService.createAndSaveBoard();
    }

    @GetMapping(value = "/v1/board/get-all")
    public String getAllBoards() {
        return boardService.getAllBoardsAsString();
    }

    @GetMapping("/v1/board/{id}")
    public ResponseEntity<String> getBoardById(@PathVariable UUID id) {
        return boardService.getBoard(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @PutMapping("/v1/board/{id}/move/{boardMovementDirection}")
    public String updateBoard(@PathVariable UUID id,
                              @PathVariable BoardMovementDirection boardMovementDirection) {
        return boardService.updateBoard(id, boardMovementDirection);
    }

    @DeleteMapping("/v1/board/{id}/delete")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") UUID id) {
        var responseCode = boardService.removeBoard(id) ? NO_CONTENT : NOT_FOUND;
        return status(responseCode).build();
    }
}
