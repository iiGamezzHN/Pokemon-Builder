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

<table><tr><td>

| Pokemon Class |
| --- |
| Name |
| Moves |
| ID | 
| Sprites | 
| Abilities |
| Height |
| Weight |
| Abilities |
| Types |
| etc... |


</td><td>

| Pokedex Class |
| --- |
| Name |
| ID | 
| Sprites | 
| Abilities |
| Height |
| Weight |
| Regions |

</td></tr> </table>

<table>
<tr><th> PokemonDatabase </th></tr>
<tr><td>

| Name | Moves | ID |  Sprites |  Abilities |  etc... |
| ---- | ----- | --- | ------- | ---------- | ------- |
| String | Array(names) | int | Array(url) | Array(names) | ... |

</td></tr> 
</table>

## API's and external components
The API that's used is [PokeAPI](https://pokeapi.co/), which provides information on Pokemon, moves, items, abilities, etc. The information can be a multitude of types like: string, int, JSONArray, JSONObject. The API will be called at least 2 times: once to get just the names of all the pokemon for the search list popup, and once to get all the specific information of the pokemon that was selected in the search list popup.

A framework that I'll use is one for getting a popup screen to search through an array. Which I'll use for a list of all Pokemon, and when you click a Pokemon I'll use the name to filter the data for that exact Pokemon to add him to the database.

To make API calls easier and cleaner, I want to try to use [Retrofit](http://square.github.io/retrofit/), which should be able to do that.

For handling images I will use Picasso. Mainly to resize the sprites so I can use them where I want to have a small picture of the Pokemon.

To store the information on saved pokemon I will use SQLite to create a database on the phone itself in which to store the information.

In an effort to try make SQL statements easier and cleaner, I want to use [Active Android](http://www.activeandroid.com/), which should shorten statement a lot.

## Transforming Data

The API response with just the names will be transformed into an array, so that it's searchable with the popup.

I will be using [GSON](https://github.com/google/gson) to 'automatically' transform the data from the API for the specific pokemon to the Pokemon Object. This Object contains all the information a single Pokemon can have. This makes it easier because the response has a lot of information, and this will enable me to go through the entire response and assign each part where it belongs with it's own type (String, int, etc).
