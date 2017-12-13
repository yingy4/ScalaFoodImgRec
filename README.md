# ScalaFoodImgRec
Scala Final Project - Food Image Recognition
[![CircleCI](https://circleci.com/gh/ToughJellyfish/ScalaFoodImgRec.svg?style=svg)](https://circleci.com/gh/ToughJellyfish/ScalaFoodImgRec)

Fei Shen - shen.fei1@husky.neu.edu 

Neola Pinto - pinto.ne@husky.neu.edu 

Rachna Balani - balani.r@husky.neu.edu

There are two parts on this project: core and rest.

Core contains tensorflow and Spark; and rest contain play framework. 

Spark MapReduce analyze the results generated from play and tensorflow image recognition functions.

We used 20 categories of foods which contains 1000 images each. new20retrained_graph.pb and new20retrained_label.txt are used in this for recognition. 

userResults are test image results. Spark can be used to calcuate the results, accuracy, and average probability. 

Project Pipeline:

1. Image Filtering and Processing

2. TensorFlow Inception V3 Retrain Model for image recognition 

3. Play Front-End for single food image recognition 

4. Create and test on Large Dataset and user image data frequency analysis- Spark MapReduce
