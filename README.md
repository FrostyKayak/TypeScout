# TypeScout
TypeScout
TypeScout is a lightweight Java console app that helps users quickly check Pokémon type matchups.
The user enters a Pokémon name, selects a form or variant if applicable, and TypeScout displays what that Pokémon is weak against, resistant to, immune to, and offensively strong against.
This project is intentionally bare bones and serves as a foundation for a more fully featured Pokémon battle analysis tool in the future.

What TypeScout Does
Accepts a Pokémon name as user input
Pulls type data from Bulbapedia using their MediaWiki API
Supports multiple forms and regional variants
Displays defensive weaknesses, resistances, and immunities
Displays offensive strengths based on the Pokémon’s typing

Why This Project Exists
TypeScout was built as a learning and portfolio project to practice:
  Java fundamentals and clean class design
  Working with external APIs
  Parsing JSON data
  Maven project structure and dependency management
  Handling real world data that is not perfectly uniform
Rather than relying on hard coded Pokémon data, TypeScout pulls live information, which makes it more flexible and realistic than a static lookup table.

Technologies Used
Java
Maven
Gson for JSON parsing
Bulbapedia MediaWiki API

Current Limitations
Console based interface only
No caching of results
Type chart is simplified and can be expanded further
No GUI or battle simulation yet
These limitations are intentional. The goal was to build a solid core before adding complexity.

Future Ideas
JavaFX or Swing interface
Smarter form matching from user input like Alolan or Hisuian
Expanded and fully accurate type effectiveness chart
Save recent searches
Integration with move based damage calculations

Disclaimer
This project is for educational and portfolio purposes only.
Pokémon and related content are owned by their respective rights holders.
TypeScout uses publicly available information from Bulbapedia.
