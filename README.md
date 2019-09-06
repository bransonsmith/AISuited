# NLHEAI
Pit Texas Hold Em AIs against each other!!!

Pull down the code.
You'll implement a Player. (Extend the abstract Player class).
Player will get an object of all of the information a player at the table would know,
        the the player returns the decision they make (Fold, Check, Call, Raise).
        This loop happens over and over again until one of the players wins the game.

To interact with the app, run the project from ManualInterface.java
The application will start and you should see the game in a new window.
Click in your console... now repeatedly hit enter to progress the game forward one event at a time.

----------------------------------------------------------------------------------------------------------------    
# To implement a Player:
        1. Create a new class in the Players package that extends Player.java
        2. Implement the getDecision method.
                - You'll receive a context object that includes all of the information a player would have
                at the time they need to make their decision (This may not be fully implemented yet... see Issues^). 
                - You then return a decision object with a decision type (Fold, Check, Call, Raise) and an amount of chips if      applicable.
                
        There are some simple examples in the Player package, and one below
----------------------------------------------------------------------------------------------------------------    
----------------------------------------------------------------------------------------------------------------     
        Example: A player that will always go all in any time they make a decision. 
                        (They choose to raise, with the amount of their own chips)
----------------------------------------------------------------------------------------------------------------
        package Players;

        import GameRunning.Decisions.Choices;
        import GameRunning.Decisions.DecisionContext;
        import GameRunning.Decisions.HEDecision;

        public class AlwaysAllIn extends Player {

                public AlwaysAllIn(String _name, String ownerName) {
                        super(_name, ownerName);
                }

                @Override
                public HEDecision getDecision(DecisionContext decisionContext) {
                        return new HEDecision(Choices.Raise, decisionContext.getMyInfo().getChips());
                }
        }
 ----------------------------------------------------------------------------------------------------------------
