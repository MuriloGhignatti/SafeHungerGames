package states

class HungerGamesStateMachine {
    val currentState: State = WaitingPlayers().start()
}