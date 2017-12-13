# ScalaFoodImgRec
Scala Final Project - Food Image Recognition
[![CircleCI](https://circleci.com/gh/ToughJellyfish/ScalaFoodImgRec.svg?style=svg)](https://circleci.com/gh/ToughJellyfish/ScalaFoodImgRec)
There are two parts on this project: core and rest.
Core contains tensorflow and Spark; and rest contain play framework. 
Spark MapReduce analyze the results generated from play and tensorflow image recognition functions.
We used 20 categories of foods which contains 1000 images each. new20retrained_graph.pb and new20retrained_label.txt are used in this for recognition. 
userResults are test image results. Spark can be used to calcuate the results, accuracy, and average probability. 
