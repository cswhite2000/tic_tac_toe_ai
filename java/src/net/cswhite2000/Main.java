package net.cswhite2000;

public class Main {

  public static void main(String[] args) {

    //    Byte[] board = {
    //      1, 1, 1,
    //      3, 2, 1,
    //      1, 1, 1
    //    };
    //    Maker maker = new Maker();
    //    System.out.println(maker.minimax(board, Maker.COMPUTER));
    //
    //      Byte[] board = {
    //          1, 1, 1,
    //          1, 1, 1,
    //          1, 1, 1
    //      };
    //      Maker maker = new Maker();
    //      System.out.println(maker.getBestMove(board));

    Maker maker = new Maker();
    //    maker.getPossibleBoards().forEach(Main::printBoard);

    //    for (int i = 0; i < maker.getPossibleBoards().size(); i++) {
    //        System.out.println(printBoard(maker.getPossibleBoards().get(i)));
    //    }
    //      System.out.println(maker.getPossibleBoards().size());
    //    for (int i = 0; i < maker.getPossibleBoards().size(); i++) {
    //      System.out.println(printChoice(maker.getOptimalMoves().get(i)));
    //    }
    //      System.out.println(maker.getOptimalMoves().size());
    //  }
    System.out.println("");
    for (int i = 0; i < maker.getPossibleBoards().size(); i++) {
      System.out.print(printBoard(maker.getPossibleBoards().get(i)));
      System.out.println(printChoice(maker.getOptimalMoves().get(i)));
    }
  }

  public static String getRepresentation(byte b) {
    if (b == Maker.EMPTY) {
      return "0.2";
    } else if (b == Maker.COMPUTER) {
      return "0.4";
    } else {
      return "0.8"; // Maker.PLAYER_ONE
    }
  }

  public static String printBoard(Byte[] board) {
    StringBuilder stringBuilder = new StringBuilder();
//    stringBuilder.append('[');
    for (int i = 0; i < 9; i++) {
      stringBuilder.append(getRepresentation(board[i]));
      stringBuilder.append(", ");
    }
//    stringBuilder.append(getRepresentation(board[8]));
//    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  public static String printChoice(Byte[] choices) {
    StringBuilder stringBuilder = new StringBuilder();
//    stringBuilder.append('[');
    for (int i = 0; i < 8; i++) {
      stringBuilder.append(choices[i]);
      stringBuilder.append(", ");
    }
    stringBuilder.append(choices[8]);
//    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}
