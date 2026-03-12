# Assignment 2: Change and Evolution

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-dark.svg)](https://sonarcloud.io/summary/new_code?id=MohitGedela_Team19-Catan-V2)

**Course:** SFWRENG 2AA4: Software Design I  
**Institution:** McMaster University, Winter 2026  

---

## Team Members
- Manasvi Bandi
- Nithya Majeti
- Mohit Gedela
- Lohitashwa Madhan 

---

## Summary
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
