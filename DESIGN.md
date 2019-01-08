# Pokemon-Builder Design

## Advanced sketches

![](doc/Design%20PokeBuilder.png)

You can add pokemon to your list from the main menu, your list, and from an individual pokemon in the pokedex.

You can edit a pokemon in your list which will send you to the "add pokemon" activity with all your previous choices filled in.

The indications behind the names in Pokemon List Overview show what areas of the pokemon have recieved a boost. This is great when you have multiple of the same pokemon.

The Add Activity gives you a popup to search for the pokemon you want to add. And pokemon can only be added if everything is filled in and if there are 4 different moves.

The Pokedex Activity let's you scroll through all the pokemon. Since there are 900+ of them, there's also a sort button to search with different criteria.

The SQLite database gets input from the add activity, and sends that information to the pokemon list and pokemon details activities.

The API sends the response to the search list popup, the pokedex overview and the pokedex details.

## Diagrams

| PokemonDatabase |
| --- |
| Name |
| Moves |
| ID | 
| Sprites | 
| Abilities | 
| --- |
| addPokemon() |
| deletePokemon() |
