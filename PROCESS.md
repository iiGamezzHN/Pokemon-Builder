# Day 1
I already have code that I can run. I've already processed two API requests and processed them. The first one is for getting all the pokemon names, and the second one is for getting all the information for a single pokemon. The names are used for a search popup so that you can select a Pokemon to add. This name is then used in the information API request. The response from this request is already handled by GSON so that it's put inside a Pokemon model.
- Contemplating using either ScrollView or ExpandableView for the AddActivity, since it needs a lot of information. Need to get a design for how I was to show this before I can tell which one would be better

# Day 2
- Realised I still need to send a request to the API for the moves, because I want to show the power and accuracy of a move, something which is not included in the information given bij the Pokemon information. Note: The moves a Pokemon can learn are specific to each pokemon.
- Realised I still need to send a request to the API for the items and berries, because I want to show the effects of the items that can be equipped. Note: This information cannot be obtained through Pokemon informatinon because each item/berry can be used on any pokemon.

# Day 3
