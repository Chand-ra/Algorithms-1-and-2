# Algorithms, Part 1 and 2
This is a compilation of my solutions to the various programming assignments in Princeton's introductory course to Data Structures and Algorithms on Coursera, 
Algorithms [Part 1](https://www.coursera.org/learn/algorithms-part1) and [Part 2](https://www.coursera.org/learn/algorithms-part2). The course consists of ten assignments in total (5 in each part) and
to complete an assignment one requires a score 80% or above on Cousera's automated tests.

## Part 1 

### Week 1 - [Percolation](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php) : 94/100
A program to estimate the value of the percolation threshold via Monte Carlo simulation.

### Week 2 - [Deques and Randomized Queues](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php) : 94/100
A generic data type for a deque and a randomized queue. 
The goal of this assignment is to implement elementary data structures using arrays and linked lists, and to get familiar with generics and iterators in java.

### Week 3 - [Collinear Points](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php) : 90/100
Given a set of n distinct points in the plane, design an algorithm to find every (maximal) line segment that connects a subset of 4 or more of the points.

### Week 4 - [8 Puzzle](https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php) : 99/100
A program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.

### Week 5 - [Kd-Trees](https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php) : 91/100
A data type to represent a set of points in the unit square (all points have x- and y-coordinates between 0 and 1) 
using a 2d-tree to support efficient range search (find all of the points contained in a query rectangle) and nearest-neighbor search (find a closest point to a query point).

## Part 2

### Week 1 - [Wordnet](https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php) : 98/100
WordNet is a semantic lexicon for the English language that computational linguists and cognitive scientists use extensively. WordNet groups words into sets of synonyms called synsets. 
For example, { AND circuit, AND gate } is a synset that represent a logical gate that fires only when all of its inputs fire. 
WordNet also describes semantic relationships between synsets. One such relationship is the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). 
For example, the synset { gate, logic gate } is a hypernym of { AND circuit, AND gate } because an AND gate is a kind of logic gate. The goal in this assignment is to create an efficient
implementation of a WordNet data type using certain graph-processing algorithms.

### Week 2 - [Seam Carving](https://coursera.cs.princeton.edu/algs4/assignments/seam/specification.php) : 100/100
In this assignment, we create a data type that resizes a W-by-H image using the seam-carving technique, which is a content aware image resizing technique discovered in 2007 by
Shai Avidan and Ariel Shamir.

### Week 3 - [Baseball Elimination](https://coursera.cs.princeton.edu/algs4/assignments/baseball/specification.php) : 100/100
Given the standings in a sports division at some point during the season, determine which teams have been mathematically eliminated from winning their division.

### Week 4 - [Boggle](https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php) : 98/100
A program to play the word game Boggle.

### Week 5 - [Burrows-Wheeler](https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php) : 100/100
Implementation of the Burrows–Wheeler data compression algorithm. 
This revolutionary algorithm outcompresses gzip and PKZIP, and forms the basis of the Unix compression utility bzip2.
