package net.cswhite2000;

import java.util.ArrayList;

public class Maker {

  private ArrayList<Byte[]> possibleBoards;
  private ArrayList<Byte[]> optimalMoves;

  public static final byte PLAYER_ONE = 3;
  public static final byte COMPUTER = 2;
  public static final byte EMPTY = 1;

//  public Maker() {
//    possibleBoards = new ArrayList<>();
//    optimalMoves = new ArrayList<>();
//  }

  public ArrayList<Byte[]> getPossibleBoards() {
    if (possibleBoards == null) {
      generateBoards();
    }
    return possibleBoards;
  }

  public ArrayList<Byte[]> getOptimalMoves() {
    if (optimalMoves == null) {
      generateOptimalMoves();
    }
    return optimalMoves;
  }

  public void generateOptimalMoves() {
    optimalMoves = new ArrayList<>();
    ArrayList<Byte[]> boards = getPossibleBoards();

    for (int i = 0; i < boards.size(); i++) {
      optimalMoves.add(getBestMoveArray(boards.get(i)));
    }
  }

  public Byte[] getBestMoveArray(Byte[] board) {
    int bestMove = getBestMove(board);
    Byte[] returnValue = new Byte[9];
    for (int i = 0; i < 9; i++) {
      returnValue[i] = 0;
    }
    returnValue[bestMove] = 1;
    return returnValue;
  }

  public int getBestMove(Byte[] board) {
    double score = -100000;
    int move = -1;
    for (int i = 0; i < 9; i++) {
      if (board[i] == EMPTY) {
        Byte[] newBoard = board.clone();
        newBoard[i] = COMPUTER;
        double newScore = minimax(newBoard, PLAYER_ONE);
        if (newScore > score) {
          score = newScore;
          move = i;
        }
      }
    }
    return move;
  }

  public double minimax(Byte[] board, byte currentPlayer) {
    boolean done = true;
    for (int i = 0; i < 9; i++) {
      if (board[i] == EMPTY) {
        done = false;
        break;
      }
    }
    if (done || somebodyWon(board)) {
      return scoreBoard(board);
    }

    byte nextPlayer = currentPlayer == PLAYER_ONE ? COMPUTER : PLAYER_ONE;


    if (currentPlayer == COMPUTER) {
      double score = -100000;
      for (int i = 0; i < 9; i++) {
        if (board[i] == EMPTY) {
          Byte[] newBoard = board.clone();
          newBoard[i] = currentPlayer;
          double newScore = minimax(newBoard, nextPlayer);
          if (newScore > score) {
            score = newScore;
          }
        }
      }
      return score;
    } else {
      double score = 100000;
      for (int i = 0; i < 9; i++) {
        if (board[i] == EMPTY) {
          Byte[] newBoard = board.clone();
          newBoard[i] = currentPlayer;
          double newScore = minimax(newBoard, nextPlayer);
          if (newScore < score) {
            score = newScore;
          }
        }
      }
      return score;
    }
  }

  public double scoreBoard(Byte[] board) {
    if (didPlayerWin(board, PLAYER_ONE)) {
      return -10;
    }
    if (didPlayerWin(board, COMPUTER)) {
      return 10;
    }
    //Nobody has won so make a running score;
    double baseScore = -1;
    if (board[4] == COMPUTER) {// being in the center is good
      baseScore = 1;
    } else if (board[4] == PLAYER_ONE) {
      baseScore -= 1;
    }

    //Corners are good
    if (board[0] == COMPUTER) baseScore += 0.1;
    if (board[2] == COMPUTER) baseScore += 0.1;
    if (board[6] == COMPUTER) baseScore += 0.1;
    if (board[8] == COMPUTER) baseScore += 0.1;

    if (board[0] == PLAYER_ONE) baseScore -= 0.09;
    if (board[2] == PLAYER_ONE) baseScore -= 0.09;
    if (board[6] == PLAYER_ONE) baseScore -= 0.09;
    if (board[8] == PLAYER_ONE) baseScore -= 0.09;
    return baseScore;
  }

  public boolean boardsEqual(Byte[] first, Byte[] second) {
    for (int i = 0; i < 9; i++) {
      if (!first[i].equals(second[i])) {
        return false;
      }
    }
    return true;
  }

  public boolean somebodyWon(Byte[] board) {
    return didPlayerWin(board, COMPUTER) || didPlayerWin(board, PLAYER_ONE);
  }

  private boolean didPlayerWin(Byte[] board, byte checkingPlayer) {
    return ((board[0] == checkingPlayer && board[1] == checkingPlayer && board[2] == checkingPlayer) ||
        (board[3] == checkingPlayer && board[4] == checkingPlayer && board[5] == checkingPlayer) ||
        (board[6] == checkingPlayer && board[7] == checkingPlayer && board[8] == checkingPlayer) ||
        (board[0] == checkingPlayer && board[3] == checkingPlayer && board[6] == checkingPlayer) ||
        (board[1] == checkingPlayer && board[4] == checkingPlayer && board[7] == checkingPlayer) ||
        (board[2] == checkingPlayer && board[5] == checkingPlayer && board[8] == checkingPlayer) ||
        (board[0] == checkingPlayer && board[4] == checkingPlayer && board[8] == checkingPlayer) ||
        (board[2] == checkingPlayer && board[4] == checkingPlayer && board[6] == checkingPlayer));
  }

  public boolean keepBoard(Byte[] board) {
    int num1s = 0;
    int numnegs = 0;
    for (int i = 0; i < 9; i++) {
      if (board[i] == COMPUTER) {
        num1s++;
      } else if (board[i] == PLAYER_ONE) {
        numnegs++;
      }
    }
    //1 is the computer so it can't go next
    if (num1s > numnegs) {
      return false;
    }

    if (somebodyWon(board)) {
      return false;
    }

    return true;
  }

  public void generateBoards() {
    possibleBoards = new ArrayList<>();
    Byte[] base = new Byte[9];
    for (int i = 0; i < 9; i++) {
      base[i] = EMPTY;
    }
    possibleBoards.add(base);
    recurse(base, COMPUTER, 0);
    recurse(base, PLAYER_ONE, 0);

    possibleBoards.sort((f, s) -> {
      for (int i = 0; i < 9; i++) {
        int diff = f[i] - s[i];
        if (diff == 0) continue;
        return diff;
      }
      return 0;
    });

    ArrayList<Byte[]> newBoard = new ArrayList<>();

    Byte[] previous = possibleBoards.get(0);
    if (keepBoard(previous)) {
      newBoard.add(previous); // this one has a victory
    }
    for (int i = 1; i < possibleBoards.size(); i++) {
      Byte[] next = possibleBoards.get(i);
      if (!boardsEqual(previous, next)) {
        if (keepBoard(next)) {
          newBoard.add(next);
        }
      }
      previous = next;
    }
    possibleBoards = newBoard;
  }

  private void recurse(Byte[] board, byte pieceToPlace, int currentDepth) {
    possibleBoards.add(board);
    if (currentDepth == 8) {
      return;
    }
    byte nextPiece = pieceToPlace == COMPUTER ? PLAYER_ONE : COMPUTER;
    for (int i = 0; i < 9; i++) {
      if (board[i] == EMPTY) {
        Byte[] newBoard = board.clone();
        newBoard[i] = pieceToPlace;
        recurse(newBoard, nextPiece, currentDepth + 1);
      }
    }
  }
}
