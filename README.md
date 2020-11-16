# qbg-xplat
### (QuickBounceGame Multi-plat)
OpenSource version of the simple QBG game, trying to make it work on all platforms using IntelliJ and kotlin.

### Game Info

The game started a couple years ago as a little java project I built with [LWJGL](https://www.lwjgl.org/) and some simple utilities. It was basically a game jam game, thrown together in a few days.

Now I'm trying to improve the game and open-source it. This is the initial pass at porting the game to the [JMonkeyEngine](https://jmonkeyengine.org/) library but because I'm also crazy I'm not using their custom Netbeans distro and instead just using [Intellij](https://www.jetbrains.com/idea/).

#### Simple Storyline

Right now the story is very simple. You're a human, some orks invade, 2020 happens but with orks.

#### Gameplay

I'm still finishing up porting the gameplay right now, but the main idea is that each team has Mana as a resource that regenerates over time, and can be used to summon units. The fight takes place over an arena with 8+ "lanes", you as players summon units to these lanes to fight. The units move toward the opposite side of the board, and if they encounter enemies they deal damage to each other and then "bounce" backwards a little bit, depending on weight of each unit and their respective power. If a unit takes enough damage, it dies. If a unit makes it to the opposite end of the board, they're removed and the player on that side takes damage. If a player takes enough damage, they lose the fight.

There's a super short, simple, hard-coded campaign that should be ready soon. If the game is fun after I've ported it, or if there's interest, I'd like to make the game something akin to a Total War game, with a much bigger (maybe even procedurally-generated like a Civilization game) campaign map, more detailed art obviously, with plenty of teams and different units and strategies.

The game will have multiplayer, and hopefully it's fun.

#### Release Goals

I'm aiming to release the game on iOS and Android, and also on Steam.

### Contributing

You can pull the code and submit PRs. If you do, please name your branch `[initials]/[issue #]-[description]`, for example `cb/123-short-issue-desc`. 

Project is still relatively young so there's lots of work to do with Github Actions to do builds, adding issues to the tracker etc. I'm in desperate need of an artist that isn't me, so if you know any artists willing to do some art please have them contact me.

If you like the game you can always subscribe to <a href="https://www.patreon.com/bePatron?u=884446" data-patreon-widget-type="become-patron-button">my Patreon</a> for this project and others.


