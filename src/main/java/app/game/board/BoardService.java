package app.game.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BoardService {

    private final BoardCache boardCache;

    public String createAndSaveBoard() {
        var board = new Board(UUID.randomUUID());
        boardCache.insertBoard(board);
        return board.toString();
    }

    public String getAllBoardsAsString() {
        return boardCache.getAllBoards()
                .stream()
                .map(Board::toString)
                .reduce("", (accu, curr) -> accu + " \n ----- \n" + curr);
    }

    public Optional<String> getBoard(UUID uuid) {
        return boardCache.getBoard(uuid)
                .map(Board::toString);
    }

    public String updateBoard(UUID id, BoardMovementDirection boardMovementDirection) {
        throw new RuntimeException("Not implemented yet");
    }

    public boolean removeBoard(UUID id) {
        return boardCache.removeBoard(id);
    }
}
