package app.game.board;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@Getter
@Setter
public class Board {
    private static final int SIZE = 4;
    private final UUID id;
    private final int[][] board;
    private boolean isGameCompleted;

    // For testing purposes
    public Board(UUID id, int[][] board) {
        this.id = id;
        this.board = board;
    }

    public Board(UUID id) {
        this.id = id;
        this.board = initializeBoardTiles();
    }

    private int[][] initializeBoardTiles() {
        var tempBoard = new int[SIZE][SIZE];

        var tileValues = range(0, SIZE * SIZE)
                .boxed()
                .collect(toList());
        shuffle(tileValues);

        int tileValuesIndex = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tempBoard[i][j] = tileValues.get(tileValuesIndex);
                tileValuesIndex++;
            }
        }
        return tempBoard;
    }

    public void determineIsGameFinished() {
        if (board[SIZE-1][SIZE-1] != 0) {
            return;
        }

        var expectedValue = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(board[i][j] == 0) {
                    isGameCompleted = true;
                }
                if (board[i][j] != expectedValue++) {
                    return;
                }
            }
        }
    }

    @Override
    public String toString() {
        return STR."""
                \{board[0][0]} | \{board[0][1]} | \{board[0][2]} | \{board[0][3]}
                \{board[1][0]} | \{board[1][1]} | \{board[1][2]} | \{board[1][3]}
                \{board[2][0]} | \{board[2][1]} | \{board[2][2]} | \{board[2][3]}
                \{board[3][0]} | \{board[3][1]} | \{board[3][2]} | \{board[3][3]}

                board id: \{id}
                is game finished: \{isGameCompleted}
                """;
    }
}
