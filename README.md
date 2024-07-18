# Puzzle 15 Game API

Welcome to the Puzzle 15 Game API, where you can interact with our service to manage and play the classic 15-puzzle game. This README provides essential information on how to use our API endpoints to create, retrieve, and manage game boards.

**API Endpoints
Below is a description of available endpoints that allow you to interact with the game boards.**

**1. Get a Specific Game Board
   Endpoint: GET /api/game/v1/board/{boardId}**

Retrieve the state of a specific game board by its unique identifier.

_Parameters:_

boardId (path parameter): Unique identifier of the game board.

**2. Get All Boards
   Endpoint: GET /api/game/v1/board/get-all**

Retrieve all existing game boards along with their details.

**3. Create a New Game Board
   Endpoint: POST /api/game/v1/board/create**

Create a new game board with a randomized initial state.

**4. Delete a Specific Game Board Endpoint: DELETE /api/game/v1/board/{boardId}/delete**

Delete a specific game board by its unique identifier.

_Parameters:_

boardId (path parameter): Unique identifier of the game board to be deleted.