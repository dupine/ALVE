# ALVE (Artificial Life Virtual Enviroment)
<p align="center">
  <img src="images/logo.png" alt="Logo" width="200">
</p>


## Overview

The goal of this project is to simulate the life and evolution of intelligent entities capable of surviving based on their needs and the surrounding environment. The entire simulation takes place in a 2D top-down environment and includes a legend for specific information and visual references.

## Structure of the Project
The structure of the project follows a classic MVC (Model-View-Controller) pattern. Therefore, the application is divided into three main parts:
- **Model**: Contains the data and logic of the application:
  - Entity: the core of the simulation, these are the basic organisms populating the environment.
  - StatiGenome: represents the genome of an organism, containing common information for all organisms, such as size, speed, energy, etc.
  - Food: represents the food in the environment, including its position and the amount of available energy.
  - World: the `World` class manages everything related to the environment, such as adding or removing entities, searching for nearby food, handling reproduction, etc.
  - GridCellKey: the world is divided into fixed-size logical cells used as keys in a hashmap, with a list of food as the value. Cells are created only where food exists.
  - NeuralNetwork (test version 1): 
    - Neurons: class to represent the neurons of the network with weights (connections) and biases (distortions).
    - Layers: class to represent the layers of the network, containing the list of neurons in the layer.
    - StatUtil: a set of functions that might be useful for the neural network (Sigmoid, Sigmoid Derivative, BackPropagation, etc.).
    - NeuralNetwork: the class that manages the neural network, containing methods for training and executing the network.

- **View**: Contains the graphical representation of the application:
  - SimulationView: the class that encapsulates and manages all elements of the view.
  - Panes: all panels containing specific information, such as the control panel, entity panel, and neural network panel.
  - WorldCanvas: the class that manages the simulation canvas, containing methods for rendering entities, food, entity vision, and handling events.

- **Controller**: Contains the logic that connects the model and the view:
  - SimulationEngine: the core of the simulation, managing the simulation lifecycle by updating the environment and entities at each tick.
  - SimulationController: manages the animation loop (game loop) and interactions between the simulation engine (`SimulationEngine`), the model (`World`), and the view (`SimulationView`). It also handles user interactions with the view.
    - SimulationControllerListener: the interface that must be implemented by the class that wants to receive events from the controller, such as mouse clicks or key presses.

## Features

The initial version of the program is a simple random movement simulation. Entities do not have a decision-making system and wander aimlessly. Using a Spatial HashMap algorithm, they can detect nearby food sources within a certain range and move toward them. If they fail to find food within a predefined number of ticks, they die.

<p align="center">
  <img src="/images/screenshots/SpatialHashMapExample.png" alt="Spatial HashMap Example" width="500">
</p>

Each entity has a genome that contains information about its size, speed, energy, and other parameters. The genome is used to determine the entity's characteristics and behavior. The simulation also includes a simple reproduction system where entities can reproduce if they have enough energy. The child has a mutated genome of the parent.
<p align="center">
  <img src="/images/screenshots/entityYellow.png" alt="Yellow entity" width="250">
  <img src="/images/screenshots/entityPane.png" alt="Entity pane" width="250">
</p>

They also have a neural network that can be used for decision-making. The neural network is a simple feedforward network with an input layer, hidden layer, and output layer. The input layer receives information about the entity's state, such as its position, energy level, and nearby food sources. The hidden layer processes this information, and the output layer generates actions for the entity, such as moving in a specific direction or reproducing.  


## TODO
- [ ] Reproduction system
- [ ] Fitness Evaluation and Decision-Making Neural Network
- [ ] NEAT (NeuroEvolution of Augmenting Topologies)
- [ ] UI Improvements
- [ ] Initial Development of Speciation System
- [ ] Biome Implementation
- [ ] Project Documentation (Algorithms, Technologies Used, and Biological Notes)