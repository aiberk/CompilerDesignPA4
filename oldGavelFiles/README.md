# Gavel MiniJava Compiler

```ascii-art

 ██████╗  █████╗ ██╗   ██╗███████╗██╗          ██████╗ ██████╗ ███╗   ███╗██████╗ ██╗██╗     ███████╗██████╗
██╔════╝ ██╔══██╗██║   ██║██╔════╝██║         ██╔════╝██╔═══██╗████╗ ████║██╔══██╗██║██║     ██╔════╝██╔══██╗
██║  ███╗███████║██║   ██║█████╗  ██║         ██║     ██║   ██║██╔████╔██║██████╔╝██║██║     █████╗  ██████╔╝
██║   ██║██╔══██║╚██╗ ██╔╝██╔══╝  ██║         ██║     ██║   ██║██║╚██╔╝██║██╔═══╝ ██║██║     ██╔══╝  ██╔══██╗
╚██████╔╝██║  ██║ ╚████╔╝ ███████╗███████╗    ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║     ██║███████╗███████╗██║  ██║
 ╚═════╝ ╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚══════╝╚══════╝╚═╝  ╚═╝

```

## Important and Helpful JavaCC parsing resources:

Lookaheads: https://www.cs.purdue.edu/homes/hosking/javacc/doc/lookahead.html

Empty token (i.e epsilon) matching: https://javacc.github.io/javacc/faq.html#how-do-i-match-an-empty-sequence-of-tokens

Left factoring syntax in Javacc parsing: https://stackoverflow.com/questions/20287086/left-factoring-removing-left-recursion-javacc

Direct recursion removal: https://stackoverflow.com/questions/40266828/removing-direct-left-recursion-in-javacc

## Inventory Link

[Inventory Spread Sheet Link](https://docs.google.com/spreadsheets/d/1FSM9HCd8LwoLaaAge1KRUtkTMQ7Iv7x4uCAaoqy6kW4/edit?usp=sharing)

## Progress

#### Phase 3: Pretty Print

java -classpath javacc.jar javacc gavel_pretty_print.jj
javac gavel_pretty_print.java
java gavel_pretty_print

#### Phase 1: Lexical Analysis

- added javacc.jar to the directory
- added cal.jj to the directory
- compiled the cal.jj file using the following command
- added pretty print to the project

#### Phase 2: Grammar Analysis

- added gavel_parser.jj to the directory
- added grammer to gavel_parser.jj
- compiled the gavel_parser.jj file using the following command

```bash
java -classpath javacc.jar javacc gavel_parser.jj
```

compile cal with

```bash
javac gavel_parser.java
```

edit cal.jj

## Running project (.txt file path in code)

```bash
java gavel_parser
```

```ascii-art


          _____                    _____                    _____                    _____                    _____
         /\    \                  /\    \                  /\    \                  /\    \                  /\    \
        /::\    \                /::\    \                /::\____\                /::\    \                /::\____\
       /::::\    \              /::::\    \              /:::/    /               /::::\    \              /:::/    /
      /::::::\    \            /::::::\    \            /:::/    /               /::::::\    \            /:::/    /
     /:::/\:::\    \          /:::/\:::\    \          /:::/    /               /:::/\:::\    \          /:::/    /
    /:::/  \:::\    \        /:::/__\:::\    \        /:::/____/               /:::/__\:::\    \        /:::/    /
   /:::/    \:::\    \      /::::\   \:::\    \       |::|    |               /::::\   \:::\    \      /:::/    /
  /:::/    / \:::\    \    /::::::\   \:::\    \      |::|    |     _____    /::::::\   \:::\    \    /:::/    /
 /:::/    /   \:::\ ___\  /:::/\:::\   \:::\    \     |::|    |    /\    \  /:::/\:::\   \:::\    \  /:::/    /
/:::/____/  ___\:::|    |/:::/  \:::\   \:::\____\    |::|    |   /::\____\/:::/__\:::\   \:::\____\/:::/____/
\:::\    \ /\  /:::|____|\::/    \:::\  /:::/    /    |::|    |  /:::/    /\:::\   \:::\   \::/    /\:::\    \
 \:::\    /::\ \::/    /  \/____/ \:::\/:::/    /     |::|    | /:::/    /  \:::\   \:::\   \/____/  \:::\    \
  \:::\   \:::\ \/____/            \::::::/    /      |::|____|/:::/    /    \:::\   \:::\    \       \:::\    \
   \:::\   \:::\____\               \::::/    /       |:::::::::::/    /      \:::\   \:::\____\       \:::\    \
    \:::\  /:::/    /               /:::/    /        \::::::::::/____/        \:::\   \::/    /        \:::\    \
     \:::\/:::/    /               /:::/    /          ~~~~~~~~~~               \:::\   \/____/          \:::\    \
      \::::::/    /               /:::/    /                                     \:::\    \               \:::\    \
       \::::/    /               /:::/    /                                       \:::\____\               \:::\____\
        \::/____/                \::/    /                                         \::/    /                \::/    /
                                  \/____/                                           \/____/                  \/____/

```
