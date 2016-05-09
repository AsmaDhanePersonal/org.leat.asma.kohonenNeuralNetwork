# Kohonen Neural Network
A Plug-in for OMNet++ simulator. 
--------------------------------------------------------
The network topology is defined by the number of neurons. 
The learning algorithm of the Kohonen Network uses euclidean distance as a metric 
 * Step1: Load input vector
 * Step2: Get best matching unit by calculating the minimal distance between a node's weight vector and the input vector
 * Step 3: The winning node learns the data (weight modification)
 * Step 4: Modify the neighbours weights(the learning fades as a gaussian func)
 * Repeat until number of iteration reached (no more learning vectors)

This plug-in is still in development process and in not tested yet on OMNet++ . 

TODO: everything :p 


Copyright (c) 2016, Asma DHANE


