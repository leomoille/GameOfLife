# Conway’s Game of Life - Implémentation Java Swing

Une implémentation robuste du **Jeu de la vie de Conway** en Java, démontrant une **architecture MVC**
rigoureuse, le suivi des **principes SOLID**, et l’utilisation de **Design Patterns**.

## 🎯 Aperçu du projet

Cette application sert de vitrine pour du code propre et des pratiques modernes d’ingénierie logicielle appliquées à un
problème classique. Elle va au-delà d’une simple implémentation en proposant une interface soignée, une configuration
flexible et un respect strict des standards professionnels.

## ✨ Fonctionnalités principales

- **Architecture MVC** : séparation stricte entre Model, View et Controller.
- **Dependency Injection** : `Main` joue le rôle de Composition Root et assemble les composants de manière découplée.
- **Principes SOLID** : respect du Single Responsibility, Open/Closed (règles extensibles), et Dependency Inversion (le
  Controller dépend de l’abstraction `GameView`).
- **Design Patterns** :
    - **Strategy Pattern** : règles extensibles (Conway, HighLife).
    - **Observer Pattern** : couplage faible entre le Game Model et l’interface graphique.
- **Interface utilisateur** :
    - **Main Menu** : configuration des règles et de la taille de la grille (y compris Custom).
    - **Interactive Grid** : vue scrollable et centrée pour un confort optimal sur tout type d’écran.
    - **Zoom Controls** : zoom dynamique avec la roulette de la souris (Ctrl/Cmd).
    - **Controls** : Start, Pause, Reset, Randomize, réglage de la vitesse.
    - **Fullscreen Mode** : vue plein écran activable.

## 🛠️ Stack technique

- Langage : Java 21+
- GUI Framework : Swing
- Build Tool : Maven
- Testing : JUnit 5 (tests unitaires, d’intégration, et paramétrés)

## 🚀 Lancer le projet

**Prérequis**

- Java JDK 21 ou supérieur
- Maven 3+

**Build et lancement**

1. Cloner le dépôt :

```bash
git clone https://github.com/your-username/game-of-life.git
cd game-of-life
```

2. Compiler :

```bash
mvn clean compile
```

3. Exécuter :

```bash
mvn exec:java -Dexec.mainClass="com.leomoille.gameoflife.app.Main"
```

## 🎮 Contrôles

- **Left Click** : basculer l’état d’une cellule (Alive/Dead).
- **Ctrl + Mouse Wheel** : Zoom In / Zoom Out.
- **Speed Slider** : ajuster la vitesse de simulation.
- **Start/Pause** : lancer/mettre en pause.
- **Randomize** : remplir la grille avec 20% de cellules vivantes.
- **Menu** : retour à l’écran de configuration.

## 🧪 Tests

Le projet inclut une suite de tests complète couvrant la logique métier et l’intégration du controller.

**Lancer les tests :**

```bash
mvn test
```

## 📐 Architecture & Patterns

1. **Model-View-Controller (MVC)**
- **Model** : `GameModel`, `Grid`, `RuleStrategy`. Gère la logique et les données.
- **View** : `MainFrame`, `GamePanel`, `MenuPanel`. Affiche l’état du jeu.
- **Controller** : `GameController`. Gère les interactions utilisateur et met à jour le Model.

2. **Strategy Pattern**

L’interface `RuleStrategy` permet d’ajouter facilement de nouvelles règles. Déjà implémentées :

- **ConwayNodes** (Standard B3/S23)
- **HighLifeRules** (B36/S23)

3. **Observer Pattern**

GameModel utilise `PropertyChangeSupport` pour notifier la `View` des changements (`grid`, `generation`), assurant une réaction
automatique sans dépendance directe au mécanisme d’updates.