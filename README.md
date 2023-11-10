# Four In A Row bot

This project aims to create an algorithmic bot to play against.

It is one of the first projects created and all logic and algorithms are made by myself (Ludvig Bolin) from scratch.

Note that parts of the program is yet to be fully functioning. For example there exists instances where the board will not recognise a win or the computer cannot understand some threat scenarios.

## Initial strategy
The strategy used originated in a heat map approach of understanding the board. 
This is done by calculating the potential threats that a user can do versus the opportunities that 
the bot can create. This is illustrated below:

> | 4 4 4 4 4 4 4 |\
> | 4 4 4 4 4 4 4 |\
> | 5 4 4 4 5 4 5 |\
> | 5 5 4 4 5 6 7 |\
> | 4 5 5-1 6 8 4 |\
> | 5 7 8-1-1 8 7 |\
> | 4 4-1-1-1-1 4 |

This would indicate that positions marked `-1` means positions already filled and then positions are graded from worst to best.
Further only valid positions will be taken into account (as gravity is active). 
Thereby we can expect the bot to place its marker on the third or sixth column. In this case, by random choice, it picked the third column.

## Implementation

### Calculating the HeatBoard
The program can be divided into different parts and each will be explained below.
...
### Displaying the results
...