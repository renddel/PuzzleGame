package app.game.board;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Component
public class BoardCache {
    private static final Map<UUID, Board> BOARD_CACHE = new HashMap<>();

    public Optional<Board> getBoard(UUID id) {
        return ofNullable(BOARD_CACHE.get(id));
    }

    public void insertBoard(Board board) {
        BOARD_CACHE.put(board.getId(), board);
    }

    public List<Board> getAllBoards() {
        return BOARD_CACHE.values()
                .stream()
                .toList();
    }

    public boolean removeBoard(UUID id) {
        return BOARD_CACHE.remove(id) != null;
    }
}
