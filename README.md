# Pokemon-Builder

## Goals

This app aims to help you remember and document all the different variations of Pokemon that you've built.

## Problem
	
Within the Pokemon games you are able to 'custom build' Pokemon as you see fit. This means that you give a
pokemon specific boosts in certain areas, items and moves. When you do this a lot, it becomes increasingly
difficult to track what builds you have already made, especially if you have multiple builds for specific
Pokemon. Right now the solutions are to either use a Desktop program that only saves your builds on one device 
(so you can't sync it to multiple accounts), or to manually save everything in an online document form like
google docs.

## Solution

### Description and visualization

In this app you will be able to recreate the build from your Pokemon and save them so that it is easier to keep
track of all your different builds.

### Features

Main features:
- See a list of your built Pokemon
- See the details for all these built Pokemon
- Select a Pokemon from all 900+ possible ones
- Select item and boosts for your Pokemon
- Browse the moves that this pokemon can learn to select 4 of them

Minimum viable product:
- See a list of your built Pokemon
- See the details for all these built Pokemon
- Select a Pokemon from all 900+ possible ones
- Select item and boosts for your Pokemon
- Browse the moves that this pokemon can learn to select 4 of them

Optional part:
- Browse the Pokedex (all 900+ pokemon and their background details)
- Show the types of the pokemon that you've built
- Show the detail attack power and accuracy when selecting the attacks
- Show which types you will hit as super effective with your current moves
- (Quick) select a team to see what types a team hits as super effective
- Something regarding the evolutions of Pokemon

## Prerequisites
### Data Source

The data is acquired through the PokeAPI, which according to their website, has recieved 431,313,100 calls. 

Information can be found at the [PokeAPI](https://pokeapi.co/) website.  website.

### External components

- SQLite: For saving and retrieving the build of Pokemon
- [GSON](https://github.com/google/gson): For automatically converting the JSON response from the API to a Java Model
- [Retrofit](http://square.github.io/retrofit/): For easy to manipulate API requests
- Picasso: For handling the images in the app
- [Active Android](http://www.activeandroid.com/): For replacing SQL statements with easier and smaller code

### Review of similar apps/vizualizations

Pokemon Showdown (computer application): This application allows you to make teams of 6 with builds of Pokemon,
which you can then use to battle other players online (if you want). I personally use it to make the build for
Pokemon before I make them inside the game, after which I take that build and put in Google Docs. It has great
personalization, but it doesn't have an app or account that you can synchronize on multiple devices, so it's 
really difficult to check all my builds if I'm out with a friend.

PokemonTeambuilder (app): This is a nice looking app that let's you browse all the current Pokemon, see their
statistics, and let's you save them in teams of 6. Sadly, it has some flaws: You cannot personalise the
Pokemon to whatever fits your build, the main descriptions are in English while all the attacks are in German,
you can only see the teams individually and not all pokemon all at once, and the app crashes when trying to
access some parts of the menu.

### Hardest parts

I think the hardest part is going to be to make a good design for all the information so that it fits, looks
and controls intuitive. It probably doesn't look like there's much to the game if you play it casually, but if 
you go deeper there's really a lot of information that influences all the battles and makes a lot of difference.

Another hard part is that I want to try to use a lot of libraries to make my code easier to write and read. For
example GSON to 'automatically' makes Java models, and Retrofit for easier SQL code. By doing this I want to
get more experience trying to write professional looking code.

