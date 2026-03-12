# Assignment 2: Change and Evolution

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-dark.svg)](https://sonarcloud.io/summary/new_code?id=MohitGedela_Team19-Catan-V2)

**Course:** SFWRENG 2AA4: Software Design I  
**Institution:** McMaster University, Winter 2026  
**Team:** Team 19

---

## Team Members
- Manasvi Bandi
- Nithya Majeti
- Mohit Gedela
- Lohitashwa Madhan 

---

## Overview
<p align="justify">
This project extends a simulator for the game of Settlers of Catan to support human gameplay. Building on the core simulator from Assignment 1, this assignment introduces a human player, command-line input parsing using regular expressions, a step-forward mechanism, trading, and the Robber mechanic. The game state is maintained in an external JSON file to feed a visualizer, keeping the human player informed of all actions. The design was evolved using UML class diagrams and an automaton model to capture each agent's action space within a turn. Unit tests were developed before any changes were made to ensure correctness throughout the evolution of the codebase.
</p>

---

## Requirements to Run
* Java 25 (OpenJDK 25.0.1) 
* Git
* Eclipse (JUnit 5)
* Python3

---

## How to Run

### Part 1: Visualizer Setup

#### 1. Clone the repository and navigate to the visualizer
```bash
git clone https://github.com/MohitGedela/Team19-Catan-V2.git
cd Task3
cd visualize
```

#### 2. Set up a Python virtual environment
```bash
python3.11 -m venv .venv
source .venv/bin/activate
```

#### 3. Install the required packages
```bash
pip install -r requirements.txt
```

#### 4. Clone the Catanatron rendering library
```bash
git clone -b gym-rendering https://github.com/bcollazo/catanatron.git
cd catanatron
```

#### 5. Install Catanatron's dependencies
```bash
pip install -e ".[web,gym,dev]"
```

#### 6. Go back to the visualizer directory
```bash
cd ..
```

#### 7. Run the visualizer

**Single render:**
```bash
python light_visualizer.py base_map.json state.json
```

**Watch mode:**
```bash
python light_visualizer.py base_map.json --watch
```

*Note: It is not aligned with the mappings in the repo provided, so to make it work, please use the outlined mappings:
## Tile-Node Mappings
```java
private int[][] tilesNodes = {
    // Row 1 (3 tiles)
    { 42, 40, 41, 16, 18, 38 },
    { 41, 43, 44, 19, 17, 16 },
    { 44, 45, 46, 47, 20, 19 },

    // Row 2 (4 tiles)
    { 39, 38, 18, 13, 15, 35 },
    { 18, 16, 17,  0,  5, 13 },
    { 17, 19, 20, 21,  1,  0 },
    { 20, 47, 48, 49, 22, 21 },

    // Row 3 (5 tiles)
    { 37, 35, 15, 14, 34, 36 },
    { 15, 13,  5,  4, 12, 14 },
    {  5,  0,  1,  2,  3,  4 },
    {  1, 21, 22, 23,  6,  2 },
    { 22, 49, 50, 51, 52, 23 },

    // Row 4 (4 tiles)
    { 34, 14, 12, 11, 32, 33 },
    { 12,  4,  3,  9, 10, 11 },
    {  3,  2,  6,  7,  8,  9 },
    {  6, 23, 52, 53, 24,  7 },

    // Row 5 (3 tiles)
    { 32, 11, 10, 29, 30, 31 },
    { 10,  9,  8, 27, 28, 29 },
    {  8,  7, 24, 25, 26, 27 },
};
```

---

### Part 2: Running the Java Game

#### Run manually (To see live render create another tab and run part 1. This will allow to see live snapshots of the boards that are generated in the scraped boards folder in Task3/visualize)
```bash
cd Task3
java Demonstrator.java
```

---

## File Structure
```
Team19-Catan-V2/
├── .github/
│   └── workflows/
├── Task1/
│   ├── boundary-testing/    # Boundary test cases
│   └── partition-testing/   # Partition test cases
├── Task2/
│   ├── Automaton Model.jpeg
│   └── UML Class Diagram.png
├── Task3/
│   ├── ParseCommandTests/   # Unit tests for the command parser
│   ├── visualize/           # Python visualizer and state.json
│   └── *.java               # Source files
├── README.md
├── Team19_Assignment2_Report.pdf
└── sonar-project.properties
```
---

## Software & Tools Used
- **Java** – For compiling and running the simulator and demonstrator programs  
- **Eclipse Papyrus** – For UML modeling and code generation  
- **Visual Studio Code** – For editing and navigating Java code  
- **GitHub** – Repository hosting and version control  
- **Python** – For compiling and running the visualizer 

---

## Our Process
- **Project Board:** [View our Kanban Board](https://github.com/users/MohitGedela/projects/5/views/1)
- **Tasks:** Work items created and assigned as GitHub Issues throughout the project
- **Merging:** Features developed on separate branches and merged through pull requests

---

## Assignment checklist

## Technical content
- [x] Every task in the assignment addressed

## Delivery

### Software
- [x] Requirements addressed
- [x] Software properly tested
- [x] Demonstrator implemented and documented

### Management
- [x] SonarQube analysis linked in the README file
- [x] Kanban board maintained and publicly available
- [x] Commits linked to work items
- [x] Deliverable tagged

### Report
- [x] Reflection points elaborated
- [x] Report written
- [x] Report submitted
