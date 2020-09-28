Image Component Labeling

Yaroslav Babych

Date: 9/27/2020

CIS 256: Project 2.

This program generates random binary image, where a 0 pixel represents image background,
while a 1 represents a pixel on an image component.
 
Two pixels are adjacent if one is to the left, above, right, or below the other.
Two component pixels that are adjacent are pixels of the same image component.

The objective of component labeling is to label the component pixels so that two pixels get the same label
if and only if they are pixels of the same image component.

The components are determined by scanning the pixels by rows, from top to bottom, and within each row by columns, from left to right. 

When an unlabeled component pixel is encountered, it is given a component identifier/label (new color). 
Labels are assigned starting with 2, because 1 denotes the foreground and 0 denotes background.

After the search is finished, program prints result table to the console 
and creates two images in the same directory - "before_search.png" and "after_search.png".