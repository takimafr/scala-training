package training

import annotation.tailrec

object exo06_players {

  object Player {

    def max(player1: Player, player2: Player) = if (player1 > player2) player1 else player2
    def min(player1: Player, player2: Player) = if (player1 < player2) player1 else player2
  }

  case class Player(name: String, score: Int) extends Ordered[Player] {
    override def compare(that: Player) = this.score - that.score
  }

  class Game(players: Player*) {

    require(players.length >= 2, "A game must have at least 2 players")

    def foldLeft[T](acc: T)(f: (T, Player) => T): T = {

      @tailrec
      def foldLeftRec(acc: T, i: Int): T = {
        if (i == players.length) acc
        else foldLeftRec(f(acc, players(i)), i + 1)
      }

      foldLeftRec(acc, 0)
    }
    
    import Player._

    def reduce(f: (Player, Player) => Player) = foldLeft(players(0))(f)

    def winner: Player = reduce(max(_, _))

    def loser: Player = reduce(min(_, _))
  }

  val game = new Game(Player("Steph", 100), Player("Olivier", 90), Player("Gianni", 80), Player("Fabrice", 2))
                                                  //> game  : training.exo06_players.Game = training.exo06_players$Game@61f3318a
  game.winner                                     //> res0: training.exo06_players.Player = Player(Steph,100)
  game.loser                                      //> res1: training.exo06_players.Player = Player(Fabrice,2)
}