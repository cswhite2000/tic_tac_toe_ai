from sklearn.neural_network import MLPClassifier
from joblib import dump, load
# import pandas as pd

clf = load("best.joblib")
# data = pd.read_csv("tictactoe.csv")
# x = data.iloc[:, :9]
# y = data.iloc[:, 9:]



def get_move(board):
    mutated = []
    for i in range(9):
        if board[i] == 0:
            mutated.append(0.2)
        elif board[i] == 1:
            mutated.append(0.8)
        elif board[i] == 2:  # 2 is computer
            mutated.append(0.4)
    probabilities = clf.predict_proba([mutated])[0]
    # print(probabilities)
    heighest = 0
    index = 0
    for i in range(9):
        if probabilities[i] > heighest and board[i] == 0:
            index = i
            heighest = probabilities[i]
    return index


def print_board(board):
    print()
    print(board[0], "|", board[1], "|", board[2])
    print("---------")
    print(board[3], "|", board[4], "|", board[5])
    print("---------")
    print(board[6], "|", board[7], "|", board[8])


def did_win(board, player):
    return (board[0] == player and board[1] == player and board[2] == player) or (
                board[3] == player and board[4] == player and board[5] == player) or (
                       board[6] == player and board[7] == player and board[8] == player) or (
                       board[0] == player and board[3] == player and board[6] == player) or (
                       board[1] == player and board[4] == player and board[7] == player) or (
                       board[2] == player and board[5] == player and board[8] == player) or (
                       board[0] == player and board[4] == player and board[8] == player) or (
                       board[2] == player and board[4] == player and board[6] == player)


def move_not_valid(board, move):
    return move < 0 or move > 8 or board[move] != 0
#
#
# print(get_move([0, 0, 0, 0, 1, 0, 0, 0, 0]))
# print_board([0, 0, 0, 0, 1, 0, 0, 0, 0])

playing = True

while playing:

    board = [0, 0, 0, 0, 0, 0, 0, 0, 0]

    current_player = int(input("Do you want to go first or second? (enter 1 or 2): "))

    print_board(board)
    for i in range(9):
        if current_player == 1:
            move = -1
            while move_not_valid(board, move):
                move = int(input("enter your move: ")) - 1
            board[move] = 1
            current_player = 2
        else:
            move = get_move(board)
            board[move] = 2
            current_player = 1
        print_board(board)
        if did_win(board, 1):
            print("You won!")
            break
        elif did_win(board, 2):
            print("You lost!")
            break
        elif i == 8:
            print("Tie Game!")
            break
    answer = input("play again? (y/n): ")
    if answer != "y":
        playing = False
