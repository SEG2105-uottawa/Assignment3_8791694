class Point2D {
  double xCoord;
  double yCoord;
  
  Point2D(double x, double y) {
    xCoord = x;
    yCoord = y;
  }
}

class SpecialPoint {
  1 -- 1 Point2D;
  0..1 -- 1 BoobyTrap;
  0..1 -- 1 Treasure;
  0..1 -- 1 ExitPoint;
  0..1 -- 1 EntryPoint;
  isA Point2D;
  super(Point2D);
  boolean found = false;

  void discovered(SpecialPoint found) {
    found = true;
    reveal(SpecialPoint found);
  }

  void reveal(SpecialPoint found) {
    // Graphically reveals SpecialPoint object found //
  }

  void delete(SpecialPoint point) {
    // Graphically and functionally removes point from level //
  }
}

class Level {
  Path path = new Path();
}

class Path {
  1..* -- 1 Level;
  double length;
  ArrayList<SpecialPoint[]> connectedPoints = new ArrayList<SpecialPoint[]>;
}

class EntryPoint {
  isA SpecialPoint;
}

class ExitPoint {
  isA SpecialPoint;
}

class HighScoreRecord {
  1 -- 0..* HighScoreList;
  String playerName;
  Level levelAchieved;
  double powerLevelAchieved;
  int aliensDestroyed;
  
  HighScoreRecord(Avatar avatar) {
    playerName = Avatar.name;
    levelAchieved = Avatar.highestLevel;
    playerPowerLevel = Avatar.highestPowerLevel;
    this.aliensDestroyed = Avatar.aliensDestroyed;
    
  }
}

class HighScoreList {
  ArrayList<HighScoreRecord[]> hsl = new ArrayList<HighScoreRecord[]>;
  
  void updateHSL(Avatar avatar) {
    HighScoreRecord record = new HighScoreRecord(avatar);
    hsl.add(record);
  }
}


class Avatar {
  1 -- 1 Point2D;
  String name;
  double currentPowerLevel;
  double highestPowerLevel;
  double maxPowerLevel = 100;
  double movementSpeed;
  int aliensDestroyed;
  Level currentLevel = new Level;
  Level highestLevel;
  Point2D currentPosition = new Point2D;
  
  void stop() {
    movementSpeed = 0;
  }

  void resume() {
    movementSpeed = 1;
  }

  void doNotCollect(SpecialPoint found) {
    resume();
  }

  void collect(SpecialPoint found) {
    currentPowerLevel += found.powerUnit;
    adjustPowerLevel(found.powerUnit);
    found.delete();
    resume();
  }

  void adjustPowerLevel(int powerUnit) {
    if(powerLevel >= maxPowerLevel) {
      powerLevel = 100;
    }
  }
}

class Treasure {
  
  0..1 -- 1 Silver;
  0..1 -- 1 Gold;
  0..1 -- 1 Diamond;
  isA SpecialPoint;
  private int powerUnit = null;
  
  static Treasure spawnTreasure(TreasureType item) {
    Treasure treasure = null;
    switch (item) {
      case SILVER:
      treasure = new Silver();
      powerUnit = 1;
      break;
      
      case GOLD:
      treasure = new Gold();
      powerUnit = 3;
      break;
      
      case DIAMOND:
      treasure = new Diamond();
      powerUnit = 10;
      break;
    }
    return treasure;
  }
 
  TreasureType item = null;

  void delete(Treasure item) {
    // Deletes treasure item from level //
  }
}

enum TreasureType {
    SILVER, GOLD, DIAMOND
}

class Silver {
  isA Treasure;
    SilverItem() {
        super(TreasureType.SILVER);
        construct();
    }
}

class Gold {
  isA Treasure;
    GoldItem() {
      super(TreasureType.GOLD);
      construct();
    }
}

class Diamond {
  isA Treasure;
    DiamondItem() {
      super(TreasureType.DIAMOND);
      construct();
    }
}

class AlienMode {
  CREATED, SLEEPING, WANDERING, SHOOTING, FOLLOWING, ESCAPING, DESTROYED;
}

class Alien {
  1 -- 1 AlienMode;
  double currentStrength;
  double initialStrength;
  AlienMode mode = CREATED;

  if(currentStrength <= 0.5*initialStrength) {
    mode = ESCAPING;
  }
}

class BoobyTrap {
  isA SpecialPoint;
  double damageDealt;
  
}