# AI-Driven Public Facility Utilization System

A DSA-based smart city system that focuses on efficiently organizing,
searching, analyzing, and recommending public facilities using
Data Structures and Algorithms along with AI-driven prioritization.

The system aims to help citizens find suitable and available public
facilities while helping facility managers and municipal administrators
understand and balance facility utilization.

---

## Project Overview

Cities have many public facilities such as:

- Parks
- Libraries
- Sports complexes
- Community halls

However, citizens may not have clear information about current
availability and occupancy. This can result in some facilities becoming
overcrowded while others remain underused.

The proposed system studies how public facility information can be
organized and analyzed using suitable Data Structures and Algorithms.

The system considers:

- Facility location
- Zone
- Capacity
- Current occupancy
- Amenities
- Operating hours
- Citizen demand
- Facility availability

The goal is to improve facility utilization and support better
resource planning.

---

## Basic Workflow

Project Requirements
        ↓
Facility Data Collection
        ↓
Facility & Zone Organization
        ↓
Facility Search
        ↓
Availability Checking
        ↓
Candidate Facility Filtering
        ↓
Utilization / Matching Score
        ↓
Priority Ranking
        ↓
Max Heap / Priority Queue
        ↓
Top Facility Recommendations

---

## DSA Concepts

| DSA Concept | Proposed Use |
|-------------|--------------|
| Binary Tree | Hierarchical organization of facility and zone information |
| AVL Tree | Balanced and efficient searching of facility records |
| Heap | Priority-based processing of facility recommendations |
| Threaded Binary Tree | Efficient traversal of facility records |
| Tree Traversal | Systematic processing of facility information |
| Graph | Represent relationships between facilities, zones and amenities |
| Adjacency List / Matrix | Store connections between facilities, zones and amenities |

The DSA concepts above are currently proposed applications. They will
be implemented and evaluated in later stages of the project.

---

## Initial Graph Model

The public facility system can be represented using a graph.

Facility ── LOCATED_IN ──> Zone

Facility ── HAS_AMENITY ──> Amenity

Facility ── CONNECTED_TO ──> Facility

Zone ── CONTAINS ──> Facility

Amenity ── AVAILABLE_AT ──> Facility

The graph can help represent relationships between facilities,
zones and amenities.

---

## Facility Data Model

Each facility can contain information such as:

- Facility ID
- Facility Name
- Location
- Zone
- Capacity
- Current Occupancy
- Amenities
- Operating Hours
- Availability
- Demand Level

Example:

Facility:
    ID: F101
    Name: City Library
    Zone: Zone A
    Capacity: 200
    Current Occupancy: 120
    Amenities: WiFi, Reading Room
    Availability: Available

---

## Utilization Approach

The proposed system will analyze facility utilization using factors
such as:

Facility Demand
+ Current Availability
+ Capacity
+ Occupancy Level
+ Location / Zone
+ Required Amenities
= Facility Priority / Recommendation Score

The exact scoring method and weights will be finalized during
implementation.

---

## Literature Review

The initial research covered relevant work related to smart city
facility utilization, recommendation systems and graph-based
recommendation.

### 1. Towards Real-Time Analysis of Smart City Data:
A Case Study on City Facility Utilizations

IEEE Xplore, 2016

Focus:
Real-time analysis of smart city and facility utilization data.

### 2. An Overview of Recommender Systems in the Context
of Smart Cities

IEEE Xplore, 2020

Focus:
Recommendation systems and their applications in smart cities.

### 3. A Survey on Knowledge Graph-Based Recommender Systems

IEEE Xplore, 2024

Focus:
Knowledge graphs and recommendation systems.

The literature review helped identify real-time analysis,
recommendation systems and graph-based approaches as relevant
directions for the project.

---

## Current Progress

### Completed

- Problem understanding
- Requirement identification
- DSA Unit 1: Trees
- DSA Unit 2: Graphs
- DSA-to-project mapping
- Initial facility utilization workflow
- Initial graph model
- Literature review
- Preliminary conceptual planning

### Yet to Implement

- Database
- Binary Tree / AVL Tree implementation
- Graph implementation
- Facility utilization algorithm
- Matching / scoring algorithm
- Max Heap recommendation
- Frontend
- Backend
- Testing
- Performance analysis

---

## Future Scope

- Explainable facility recommendations
- Advanced skill/amenity graph
- Dynamic facility recommendations
- Real-time occupancy analysis
- Demand prediction
- Facility utilization forecasting
- Citizen preference-based recommendations
- Smart city resource planning
- Project success / utilization prediction

---

## Project Goal

The main goal is to demonstrate how DSA concepts can be applied to a
real-world smart city problem and develop an efficient system for
public facility utilization.

Instead of only storing facility information, the proposed system
will focus on searching, relationship modelling, prioritization,
ranking and recommendation of suitable public facilities.

---

## Target Users

### Citizens
Can check facility availability and find suitable facilities.

### Facility Managers
Can monitor utilization patterns and support better facility planning.

### Municipal Administrators
Can analyze city-wide facility utilization and support future
resource planning.

---

## Challenges

- Understanding how Trees and Graphs can represent real-world
  facility data.
- Deciding which DSA concepts are genuinely useful.
- Representing relationships between facilities, zones and amenities.
- Designing an efficient facility recommendation approach.
- Handling increasing amounts of facility data.
- Developing an initial conceptual design without unnecessary
  complexity.

---

## Project Status

**Status:** Month 1 – Problem Understanding, Research & Initial Design

**Overall Progress:** 25%

