## Day 1
I already have code that I can run. I've already processed two API requests and processed them. The first one is for getting all the pokemon names, and the second one is for getting all the information for a single pokemon. The names are used for a search popup so that you can select a Pokemon to add. This name is then used in the information API request. The response from this request is already handled by GSON so that it's put inside a Pokemon model.
- Contemplating using either ScrollView or ExpandableView for the AddActivity, since it needs a lot of information. Need to get a design for how I was to show this before I can tell which one would be better

## Day 2
- Realised I still need to send a request to the API for the moves, because I want to show the power and accuracy of a move, something which is not included in the information given bij the Pokemon information. Note: The moves a Pokemon can learn are specific to each pokemon.
- Realised I still need to send a request to the API for the items and berries, because I want to show the effects of the items that can be equipped. Note: This information cannot be obtained through Pokemon informatinon because each item/berry can be used on any pokemon.

## Day 3
- Might have to use the search popup for selecting moves too. I think it had options to show more than just names.
- Will need API request for move info
- Will need API request for berry names, which I need to use for API request for item information
- In API response for items: attributes 0: name needs to be "holdable-active", url needs to be "https://pokeapi.co/api/v2/item-attribute/7/"
- Need to use Fragments for a better looking layout

## Day 4
- (Begin of the day) Fragments suck
- Both Renske and Natashja had trouble giving advice on fragments. Decided with Natashja's help to move both the requests into the Fragment itself (without callback).
- (End of the day) Both requests now work inside the fragment itself without callback and the app doesn't crash anymore :)

## Day 5
- Decided to stop using fragments and TabLayout (now for atleast), as it took too much time to get it working properly. But I will keep using that idea for navigation
- Added navigation buttons to normal activities (replacement for TabLayout). Height will be 50dp, as that was about the height the TabLayout had and looks good
- Added ListView and ListView adapter to Pokedex. It now shows all pokemon names.
- Made the overall layout better looking. Like white text for tabs, no transition animations when changing activities and some margins for text so that it's more readable.

## Weekend
- Added all needed information to SearchActivity in an ok looking layout
- Built the SQLite database for saving pokemon and added 1 example
- Completed the insert method for the database (22 items per pokemon)
- Created a SavedPokemon class to hold the data of the saved pokemon
- Made a ListAdapter class to show saved pokemon for the database in the ListActivity

## Day 6
- Decided to use input type phone for edittexts in SearchActivity. This only shows numbers for input and lets you automatically go to the next edittext
