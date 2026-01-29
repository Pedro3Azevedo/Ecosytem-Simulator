# 🌿 Ecosystem Simulation - Natural Systems Modeling & Simulation

A dynamic ecosystem simulation game implementing predator-prey dynamics, cellular automata, boid behavior, and emergent systems using Processing. Players must protect an endangered fish species from invasive predators while managing a fuel-powered boat through a complex ecosystem.

**Course**: Modelação e Simulação de Sistemas Naturais (MSSN) - Modeling & Simulation of Natural Systems  
**Institution**: Instituto Superior de Engenharia de Lisboa (ISEC)  
**Degree**: Licenciatura em Engenharia Informática e Multimédia (Informatics Engineering & Multimedia)  
**Authors**: Pedro Azevedo (A47094) & Ricardo Pedro (A48960)  
**Instructor**: Engenheiro Arnaldo Abrantes  
**Date Completed**: January 30, 2022

---

## 📋 Project Overview

**Ecosystem Simulation** is an interactive ecology-based game that demonstrates complex systems theory through a realistic ecosystem simulation. The project implements:

- **Cellular Automata** for terrain generation and ecosystem environment
- **Boid Behavior** for realistic animal movement and interactions
- **Physics Simulation** for realistic motion and energy dynamics
- **Agent-Based Modeling** for population dynamics and species behavior
- **Emergent Complexity** from simple individual rules

### Project Vision

Create an engaging simulation where players experience ecosystem dynamics in real-time. By taking the role of a boat operator, players must save an endangered native fish species from invasive predators while managing limited resources (fuel, health). The game demonstrates how individual behaviors lead to emergent ecosystem-level phenomena.

---

## 🎯 Project Objectives

1. **Apply Systems Modeling Concepts**
   - Implement cellular automata for terrain
   - Model predator-prey dynamics
   - Simulate population evolution
   - Demonstrate emergent behaviors

2. **Implement Realistic Agent Behavior**
   - Boid flocking/steering behaviors
   - Energy-based survival mechanics
   - Reproduction and population growth
   - Feeding and consumption

3. **Create Interactive Simulation**
   - Real-time visualization
   - Player agency (boat control)
   - Visual feedback systems
   - Victory/defeat conditions

4. **Analyze Complex Systems**
   - Observe population dynamics
   - Study species interactions
   - Document ecosystem evolution
   - Report on system stability

---

## 🌍 Background Story

**The Narrative:**

A small fishing village relies on native herbivorous fish that inhabit a nearby lake. These fish feed on algae and support the village's fishing industry. However, two disasters strike:

1. **Pollution Crisis**: A toxic substance that degrades wood is spilled into the water, damaging the boat and poisoning the fish
2. **Invasive Species**: A plague of wild shark-like predators invades the lake, hunting the native fish to extinction

**The Player's Mission**: 
Control a fishing boat to capture all invasive predators before they eliminate the native fish species. The boat has limited resources (fuel) and durability (health), which decrease as it operates in the toxic water. Success means saving the species; failure means ecological collapse.

---

## 🏗️ Architecture

### System Architecture Diagram

```
┌────────────────────────────────────────────┐
│     PROCESSING APPLICATION                 │
├────────────────────────────────────────────┤
│                                            │
│  ┌──────────────────────────────────────┐  │
│  │   SIMULATION ENGINE                  │  │
│  ├──────────────────────────────────────┤  │
│  │ • Terrain (Cellular Automata)        │  │
│  │ • Population Management              │  │
│  │ • Physics & Movement                 │  │
│  │ • Collision Detection                │  │
│  │ • Energy Management                  │  │
│  │ • Reproduction System                │  │
│  └──────────────────────────────────────┘  │
│                                            │
│  ┌──────────────────────────────────────┐  │
│  │   AGENT BEHAVIORS                    │  │
│  ├──────────────────────────────────────┤  │
│  │ • Boid Steering (Seek/Wander)        │  │
│  │ • Prey Behavior (Herbivore)          │  │
│  │ • Predator Behavior (Carnivore)      │  │
│  │ • Boat Control (Player Input)        │  │
│  └──────────────────────────────────────┘  │
│                                            │
│  ┌──────────────────────────────────────┐  │
│  │   VISUAL RENDERING                   │  │
│  ├──────────────────────────────────────┤  │
│  │ • Terrain Visualization              │  │
│  │ • Animal Sprites (Images)            │  │
│  │ • UI Elements (Bars, Text)           │  │
│  │ • Particle Effects (Blood)           │  │
│  └──────────────────────────────────────┘  │
│                                            │
│  ┌──────────────────────────────────────┐  │
│  │   USER INTERFACE                     │  │
│  ├──────────────────────────────────────┤  │
│  │ • Start Screen (Mission Brief)       │  │
│  │ • Game Screen (Real-time Gameplay)   │  │
│  │ • Status Indicators                  │  │
│  │ • End Screen (Victory/Defeat)        │  │
│  └──────────────────────────────────────┘  │
│                                            │
└────────────────────────────────────────────┘
```

### Component Structure

```
TERRAIN (Cellular Automata)
├─ States: Empty, Obstacle, Fertile, Food
├─ Majority Rule (3 iterations)
├─ Food Cycle (5-10 seconds)
└─ Images for visualization

POPULATION MANAGEMENT
├─ Prey (Herbivorous Fish)
│  ├─ Population: 55 initial
│  ├─ Energy system
│  ├─ Reproduction mechanics
│  └─ Seeking food behavior
│
├─ Predators (Invasive Sharks)
│  ├─ Population: 15 initial
│  ├─ Energy from consuming prey
│  ├─ Reproduction mechanics
│  └─ Seeking prey behavior
│
└─ Boat (Player Controlled)
   ├─ Fuel management
   ├─ Health system
   ├─ Speed related to health
   └─ Seeking mouse input

BOID BEHAVIORS
├─ Seek (Target-directed movement)
├─ Wander (Random exploration)
└─ Steering forces (Cohesion, separation)
```

---

## 🛠️ Technology Stack

### Development Environment
- **Processing** (Java-based creative coding framework)
- **IDE**: Processing IDE or compatible Java IDE
- **Graphics**: 2D rendering with Processing

### Core Technologies

**Simulation**
- Cellular Automata (terrain generation)
- Boid algorithm (steering behaviors)
- Physics simulation (velocity, acceleration, forces)
- Agent-based modeling (individual agents with behaviors)

**Data Management**
- Object-oriented design
- ArrayList for population management
- Image loading and rendering
- Particle systems (visual effects)

**Behavioral Systems**
- Behavior trees (simplified)
- State machines (agent states)
- Energy-based decision making
- Distance calculations for interaction

---

## 📊 Game Mechanics & Systems

### Terrain System (Cellular Automata)

**Cell States**:
- **Empty** (40%) - No features
- **Fertile** (10%) - Can grow food
- **Obstacle** (20%) - Blocks movement
- **Food** (30%) - Food source for prey

**Food Cycle**:
- Fertile cells → Food cells (5-10 seconds)
- Prey eating → Fertile cells (restarts cycle)
- Majority rule for terrain smoothing (3 iterations)

**Visualization**:
```
Empty:    [  ]  White/Empty
Fertile:  [🌿]  Green
Obstacle: [🗻]  Brown/Rock
Food:     [🌾]  Yellow/Plants
```

### Prey System (Native Fish)

**Characteristics**:
- **Population**: 55 initial
- **Size**: 0.2 units
- **Mass**: 1.0
- **Initial Energy**: 10
- **Energy from food**: +4 per consumption
- **Energy for reproduction**: 25 required
- **Energy loss**: Low (2-3% per movement)

**Behaviors**:
1. Seek food sources (plants)
2. Move in school-like patterns
3. Reproduce when energy ≥ 25
4. Die when energy ≤ 0
5. Avoid obstacles

**Movement**:
- Uses boid steering toward nearest food
- Random wander if no food nearby
- Affected by mass (slower than predators)

### Predator System (Invasive Sharks)

**Characteristics**:
- **Population**: 15 initial (adjusted for balance)
- **Size**: 0.3 units
- **Mass**: 0.4 (faster than prey)
- **Initial Energy**: 50
- **Energy from prey**: +25 per consumption
- **Energy for reproduction**: 200 required
- **Energy loss**: High (5-8% per movement)

**Behaviors**:
1. Seek prey using look-ahead
2. Calculate distance to prey
3. Consume prey when close
4. Reproduce when energy ≥ 200
5. Die when energy ≤ 0

**Movement**:
- Faster than prey (lower mass)
- Seek behavior when prey visible
- Wander when no prey detected
- Look-ahead distance for prey detection

**Predation**:
```java
// Pseudo-code for predator eating
if (distance_to_prey < eating_range) {
    prey.die();
    predator.energy += 25;
    particle_system.show_blood_effect();
    update_population_list();
}
```

### Boat System (Player Controlled)

**Characteristics**:
- **Size**: 0.4 units
- **Mass**: 1.0
- **Initial Fuel**: 200
- **Initial Health**: 50
- **Fuel from capturing predators**: +25 per capture
- **Health from obstacles**: -5 per collision

**Mechanics**:
1. **Fuel Management**:
   - Decreases with movement
   - Refills when capturing predators (+25)
   - Game over if fuel = 0

2. **Health System**:
   - Decreases when hitting obstacles
   - Decreases from toxic substance exposure
   - Speed directly related to health (health decreases → speed decreases)
   - Game over if health = 0

3. **Capturing Predators**:
   - Similar to predator-prey interaction
   - Boat seeks mouse cursor
   - Captures predators at close range
   - Gains fuel per capture

4. **Speed Calculation**:
   ```java
   boat_speed = (health / max_health) × initial_speed
   ```

### Energy System

**Universal Energy Mechanic**:
All animals require energy to survive:
- Movement drains energy (movement speed × mass = energy loss)
- Obstacles increase energy drain
- Food (consumption) increases energy
- Reproduction requires energy threshold
- Death occurs at energy = 0

**Energy Balance**:
```
Energy Change Per Cycle = Movement Loss + Food Gain - Maintenance
```

### Population Dynamics

**Initial State**:
- 55 prey with stable food source
- 15 predators (limited reproduction capability)
- 1 player-controlled boat

**Equilibrium Challenges**:
- Predators naturally dominate (higher reproduction energy, high reproduction energy needed)
- Prey depend on available food
- Environmental obstacles affect both species differently

**Game Balance Adjustments**:
- Predator speed reduced from initial design
- Predator population limited
- Predator reproduction energy increased (200)
- This gives player time to capture predators before prey extinction

---

## 📱 User Interface

### Start Screen

```
╔════════════════════════════════════╗
║                                    ║
║   🌿 ECOSYSTEM SIMULATION 🌿      ║
║                                    ║
║   Mission: Save the Fish Species   ║
║                                    ║
║   ┌──────────────────────────┐    ║
║   │ 🐟 Native Fish Species   │    ║
║   │    (Endangered)          │    ║
║   │                          │    ║
║   │ 🦈 Invasive Predators    │    ║
║   │    (Must be Captured)    │    ║
║   │                          │    ║
║   │ 🚣 Your Boat             │    ║
║   │    (Limited Fuel/Health) │    ║
║   └──────────────────────────┘    ║
║                                    ║
║     Click to Start Simulation      ║
║                                    ║
╚════════════════════════════════════╝
```

### Gameplay Screen

```
┌──────────────────────────────────────────────┐
│  🌿 Ecosystem Simulation                     │
├──────────────────────────────────────────────┤
│                                              │
│  [Terrain with plants, obstacles]           │
│  [🐟 Fish swimming (blue dots)]             │
│  [🦈 Sharks hunting (red dots)]             │
│  [🚣 Boat (controlled by mouse)]            │
│  [Visual indicators of animals]             │
│                                              │
│  Status Bar:                                 │
│  Prey Count: 🐟🐟🐟🐟 (48)                    │
│  Predators: 🦈 (12)                         │
│  Fuel: ████████░░ (80%)                     │
│  Boat Health: █████░░░░░ (50%)              │
│                                              │
│  Instructions: Move mouse to control boat  │
│  Click to capture predators                │
│                                              │
└──────────────────────────────────────────────┘
```

### End Screen

```
VICTORY:
┌────────────────────────────────┐
│        ✅ MISSION SUCCESS      │
│                                │
│  You captured all predators!   │
│  The fish species is saved!    │
│                                │
│  Final Stats:                  │
│  - Prey remaining: 42          │
│  - Predators captured: 15      │
│  - Fuel remaining: 75          │
│  - Boat health: 20             │
│                                │
│  [Restart Simulation]          │
└────────────────────────────────┘

DEFEAT:
┌────────────────────────────────┐
│        ❌ MISSION FAILED       │
│                                │
│  The species is extinct!       │
│  (or: Boat destroyed!)         │
│                                │
│  Reason: [Cause of failure]    │
│                                │
│  [Try Again]                   │
└────────────────────────────────┘
```

---

## 🎮 Gameplay Experience

### Game Flow

```
1. Start Screen
   └─ Display mission brief
   └─ Show ecosystem inhabitants
   └─ Wait for player to click

2. Initialization
   ├─ Generate terrain (cellular automata)
   ├─ Spawn prey population (55 fish)
   ├─ Spawn predators (15 sharks)
   └─ Place boat at center

3. Main Game Loop (Each Frame)
   ├─ Update terrain (food growth)
   ├─ Update prey
   │  ├─ Seek food or wander
   │  ├─ Consume food (energy gain)
   │  ├─ Reproduce when energetic
   │  └─ Die when out of energy
   ├─ Update predators
   │  ├─ Look for nearby prey
   │  ├─ Seek and consume prey
   │  ├─ Reproduce when energetic
   │  └─ Die when out of energy
   ├─ Update boat
   │  ├─ Follow mouse cursor
   │  ├─ Decrease fuel with movement
   │  ├─ Decrease health from obstacles
   │  ├─ Capture predators (gain fuel)
   │  └─ Check for game over
   ├─ Render everything
   └─ Display status information

4. Win/Lose Conditions
   ├─ Victory: All predators captured
   ├─ Defeat: Prey extinct
   ├─ Defeat: Boat out of fuel
   └─ Defeat: Boat destroyed (health = 0)

5. End Screen
   ├─ Show results
   ├─ Display final statistics
   └─ Allow restart
```

### Player Strategy

**Winning Strategy**:
1. Identify predator locations
2. Prioritize capturing predators
3. Manage fuel by capturing efficiently
4. Avoid obstacles to preserve health
5. Capture all 15 predators before prey die out

**Challenges**:
- Predators reproduce quickly
- Prey die out if not protected
- Fuel is limited resource
- Health affects speed/maneuverability
- Terrain obstacles complicate navigation

---

## 🧪 Simulation Parameters & Balance

### Initial Parameters (Design)

**Prey**:
- Energy: 10 initial
- Reproduction: 25 energy
- Food gain: +4
- Obstacle penalty: Low

**Predators**:
- Energy: 50 initial  
- Reproduction: 200 energy
- Prey gain: +25
- Obstacle penalty: High
- Speed: High (mass 0.4)

### Adjusted Parameters (Final Balance)

**Changes Made**:
1. Reduced predator speed
2. Limited predator reproduction energy
3. Adjusted predator population
4. Tuned food availability

**Reasoning**:
- Initial design: Predators too dominant
- Prey extinct in ~30 seconds
- Player couldn't catch predators fast enough
- Adjusted to give player ~2-3 minutes

**Results**:
- Game becomes winnable
- Requires skill and strategy
- ~50% win rate for skilled players
- Success depends partly on terrain generation

---

## 📈 Results & Observations

### Ecosystem Dynamics

**Phase 1: Prey Only**
- Stable population (~50-55)
- Reproduces steadily
- Food never runs out
- System reaches equilibrium

**Phase 2: Predators Added**
- Initial success of predators
- Rapid prey decline
- Predator population explosion (if not controlled)
- Prey extinction without intervention

**Phase 3: Player Interference (Boat)**
- Fishing pressure on predators
- Player agency affects ecosystem
- Balance becomes dependent on player skill
- Multiple stable and unstable states possible

### Key Findings

1. **Without player**: Predators dominate → Prey extinction
2. **With skilled player**: Balance achievable → Mission success
3. **Terrain effects**: Obstacles create prey refugia → Longer survival
4. **Energy dynamics**: Crucial to population stability

---

## 💡 Educational Value

### Concepts Demonstrated

**Systems Theory**:
- Emergent complexity from simple rules
- Feedback loops and regulation
- Stability and bifurcation
- Non-linear dynamics

**Ecology**:
- Predator-prey dynamics
- Population growth and regulation
- Energy flow in ecosystems
- Species interactions

**Complexity Science**:
- Agent-based modeling
- Cellular automata
- Boid flocking
- Chaos and determinism

**Programming**:
- Object-oriented design
- Physics simulation
- Collision detection
- Real-time rendering

---

## 🔧 Technical Implementation

### Key Code Structures

**Prey Class (Simplified)**:
```java
class Prey extends Animal {
    float energy = 10;
    
    void update() {
        // Seek food
        Vector target = findNearestFood();
        if (target != null) {
            Vector steer = boid.seek(target);
            applySteeringForce(steer);
        } else {
            applyWanderBehavior();
        }
        
        // Energy management
        energy -= getMovementCost();
        
        // Reproduction
        if (energy >= 25) {
            energy -= 25;
            createOffspring();
        }
        
        // Death
        if (energy <= 0) {
            die();
        }
    }
}
```

**Predator Class (Simplified)**:
```java
class Predator extends Animal {
    float energy = 50;
    
    void update() {
        // Look for prey
        Prey target = lookForPrey();
        if (target != null) {
            Vector steer = boid.seek(target.position);
            applySteeringForce(steer);
            
            // Check if close enough to eat
            if (distance < eating_range) {
                eat(target);
                energy += 25;
            }
        } else {
            applyWanderBehavior();
        }
        
        // Energy & reproduction same as prey
    }
    
    void eat(Prey target) {
        target.die();
        particleSystem.showBlood(target.position);
        population.remove(target);
    }
}
```

**Boat Class (Simplified)**:
```java
class Boat extends Animal {
    float fuel = 200;
    float health = 50;
    
    void update() {
        // Follow mouse
        Vector target = new Vector(mouseX, mouseY);
        Vector steer = boid.seek(target);
        applySteeringForce(steer);
        
        // Use fuel
        fuel -= getMovementCost();
        
        // Health from obstacles
        if (onObstacle()) {
            health -= 5;
        }
        
        // Capture predators
        Predator prey = findNearbyPredator();
        if (prey != null && distance < eating_range) {
            capturePreyator(prey);
            fuel += 25;
        }
        
        // Speed based on health
        max_speed = (health / 50) * initial_speed;
        
        // Check game over
        if (fuel <= 0 || health <= 0) {
            gameOver();
        }
    }
}
```

### Cellular Automata for Terrain

**Majority Rule**:
```java
void applyMajorityRule() {
    for (3 iterations) {
        for (each cell) {
            count neighbors by state
            cell.state = most_common_neighbor_state
        }
    }
}
```

**Food Growth**:
```java
void updateFood() {
    if (cell.state == FERTILE && timer > 5-10 seconds) {
        cell.state = FOOD;
        timer = 0;
    }
    
    if (prey_eats_here) {
        cell.state = FERTILE;
        timer = 0;
    }
}
```

---

## 🎯 Game Outcomes & Testing

### Test Scenarios

**Test 1: Prey Only**
- Result: Stable population, no extinction
- Duration: Infinite (stable)
- Learning: Prey are self-sustaining

**Test 2: Predators + Prey (No Boat)**
- Result: Prey extinction in ~30 seconds
- Duration: Very short
- Learning: Predators dominate

**Test 3: Full Ecosystem (Player Controlled)**
- Result: Variable (depends on terrain + player skill)
- Success rate: ~40-50% (requires good play)
- Duration: 2-5 minutes average
- Learning: Player skill matters, but luck involved

### Balance Changes

**Original Design → Final Design**:
- Predator speed: Reduced to ~80%
- Predator energy for reproduction: 150 → 200
- Initial predator count: Adjusted for tuning
- Prey starting position: Distributed for better survival

**Effect**:
- Game went from unwinnable → challenging but fair
- Player has ~2-3 minute window to capture predators
- Skill-based but not impossible

---

## 📚 Key Algorithms

### Boid Steering Algorithm

```java
// Calculate steering force toward target
Vector seek(Vector target) {
    Vector desired = target.copy().sub(position).normalize().mult(maxSpeed);
    Vector steer = desired.copy().sub(velocity);
    steer.limit(maxForce);
    return steer;
}

// Random wander behavior
Vector wander() {
    angle += random(-0.1, 0.1);
    Vector offset = Vector.fromAngle(angle).mult(wanderDistance);
    Vector circleCenter = position.copy().add(forward.copy().mult(wanderDistance));
    Vector target = circleCenter.add(offset);
    return seek(target);
}
```

### Distance-Based Interaction

```java
// Find nearest food source
Food findNearestFood() {
    float minDistance = Infinity;
    Food nearest = null;
    
    for (Food f : foodList) {
        float d = dist(position, f.position);
        if (d < minDistance && d < sightRange) {
            minDistance = d;
            nearest = f;
        }
    }
    
    return nearest;
}

// Eat if within range
if (distance(animal, food) < eatRange) {
    animal.energy += foodEnergy;
    food.remove();
}
```

### Majority Rule (Cellular Automata)

```java
// Count state of neighbors
int[] stateCounts = {0, 0, 0, 0}; // for 4 states

for (neighbor : get8Neighbors(cell)) {
    stateCounts[neighbor.state]++;
}

// Assign state from majority
cell.nextState = argmax(stateCounts);
```

---

## 🐛 Known Limitations & Future Improvements

### Current Limitations

⚠ Terrain generation varies significantly → Different difficulty per game  
⚠ No AI difficulty levels → Same for all players  
⚠ Limited visual feedback → Hard to track all animals  
⚠ No pause/resume functionality  
⚠ No detailed statistics tracking  
⚠ Collision resolution is simplified  

### Future Enhancements

**Short-term**:
- [ ] Add difficulty levels (Easy/Normal/Hard)
- [ ] Implement pause functionality
- [ ] Add detailed statistics screen
- [ ] Improve visual feedback (more particle effects)
- [ ] Add sound effects

**Medium-term**:
- [ ] Multiple boat strategies/tools
- [ ] Different species interactions
- [ ] Environmental factors (seasons, temperature)
- [ ] More terrain variations
- [ ] Persistent leaderboard

**Long-term**:
- [ ] Multiplayer cooperation
- [ ] Custom scenario creation
- [ ] Advanced physics (buoyancy, currents)
- [ ] Machine learning for adaptive AI
- [ ] 3D visualization
- [ ] Educational dashboard

---

## 🎓 Learning Outcomes

Students completing this project understand:

✅ **Cellular Automata**
- State-based systems
- Neighborhood rules
- Emergent patterns

✅ **Agent-Based Modeling**
- Individual behavior rules
- Population dynamics
- Emergent complexity

✅ **Boid Algorithms**
- Steering behaviors (seek/wander)
- Force-based movement
- Smooth motion

✅ **Population Ecology**
- Predator-prey dynamics
- Energy flows
- Population regulation

✅ **Systems Thinking**
- Feedback loops
- Non-linear dynamics
- Complexity from simplicity

✅ **Simulation Programming**
- Physics implementation
- Real-time rendering
- Event handling

---

## 📊 Project Statistics

| Metric | Value |
|---|---|
| Development Time | 1 semester |
| Team Size | 2 developers |
| Language | Processing (Java) |
| Lines of Code | ~1,500-2,000 |
| Classes | 7-10 main classes |
| Simulated Entities | 71 initial (55 prey + 15 predators + 1 boat) |
| Terrain Size | 1000×1000 pixels |
| Simulation Frames | 60 FPS |
| Average Game Duration | 2-5 minutes |
| Concept Demonstrated | 15+ topics from course |

---

## 📖 Course Integration

**Concepts from MSSN Course**:
- ✓ Quantitative and qualitative models
- ✓ Causal loop diagrams
- ✓ Stock and flow systems
- ✓ Autonomous agents
- ✓ Physics and movement
- ✓ System dynamics
- ✓ Chaos and fractals
- ✓ Ecosystems and evolution
- ✓ Cellular automata (Game of Life)
- ✓ Diffusion-limited aggregation (DLA)
- ✓ L-systems (Lindenmayer grammars)
- ✓ Julia and Mandelbrot sets
- ✓ Boid behavior (Craig Reynolds)
- ✓ Population dynamics
- ✓ Complex systems

---

## 🎬 Demonstration Videos

Three demonstration videos show project evolution:

1. **testes1.mkv**: Prey-only ecosystem (stable population)
2. **testes2.mkv**: Predator-prey interaction (unstable)
3. **testes3.mkv**: Full game with player interference (balanced/challenging)

---

## 👥 Team

**Authors**:
- Pedro Azevedo (A47094)
- Ricardo Pedro (A48960)

**Instructor**: Engenheiro Arnaldo Abrantes  
**Course**: MSSN - Modelação e Simulação de Sistemas Naturais  
**Institution**: ISEC - Instituto Superior de Engenharia de Lisboa  

---

## 📄 License

Educational project completed as coursework for MSSN at ISEC.

---

## 🔗 References

**Bibliography**:
- Abrantes, Arnaldo & Vieira, Paulo (2019). "Evolução em populações"
- Reynolds, Craig (1987). "Flocks, Herds, and Schools: A Distributed Behavioral Model"
- Wolfram, Stephen. "A New Kind of Science" (Cellular Automata)
- Various ecology and population dynamics research

---

## 🎉 Conclusion

This ecosystem simulation successfully demonstrates how complex ecological dynamics emerge from simple individual behaviors. The game balances scientific accuracy with engaging gameplay, creating both an educational tool and an entertaining experience.

By implementing cellular automata for terrain, boid algorithms for agent behavior, and energy-based population dynamics, the project shows how computational modeling helps us understand complex natural systems that are too intricate for human mental simulation alone.

The game serves as both a learning tool for systems thinking and a reminder of the delicate balance required to maintain healthy ecosystems.

---

**Date Completed**: January 30, 2022  
**Project Type**: Final Project (MSSN Course)  
**Status**: Complete  
**Educational Value**: High - Demonstrates 15+ course concepts  

