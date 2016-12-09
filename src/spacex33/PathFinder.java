/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacex33;

/**
 * Backwards pathfinding algorithm
 * @author Johnathon Robertson
 */

public class PathFinder {
    //generate a path to spawn asteroids around
    private int lanes;
    private float shipHeight;
    private boolean spawnGrid[][];
    private int x;
    private int y;
    private int openSpots; //min spots in path wanted in row
	private int blockSize; //number of adjacent spots in path in row
	private int blocks; //number of blocks in a row
	private final int randomness = 2;
    
    public PathFinder(Ship ship, int laneNum) {
        lanes = laneNum;
        shipHeight = ship.getHeight();
        x = lanes;
        y = 25;
        openSpots = 3;
        blockSize = 0;
        blocks = 0;
    }
    
    public boolean[][] pathGen() {
        System.out.println("generating...");
        spawnGrid = new boolean[x][y];
        int blockChoice = 0; //chosen spot in block of adjacent path spots in row
        int numSpots = 0; //counter that tracks spots in path in current row
        //TODO: randomly select certain spots in each row to be true, path
            //of true spots is the path. Each row must have at least one spot
            //that is in previous row. Difficulty setting variable??
			
        //fill first row of chunk
        while(numSpots < openSpots) {
            for(int j=0; j<x; j++) {
                if((int)(Math.random() * 100) % randomness == 0 && numSpots < openSpots && spawnGrid[j][0] == false) {
                    spawnGrid[j][0] = true;
                    numSpots++;
                }
                if( numSpots >= openSpots)
                        break;
            }
        }
        numSpots = 0;
		
        //iterates through every spot in the grid, by row and every lane in row)
        for(int i=1; i<y; i++) {
            //iterate through lanes in a row
            for(int j=0; j<x; j++) {
                if(spawnGrid[j][i-1]) {
                    blockSize++;
		}
                else {
                    spawnGrid[j][i] = false;
                    if(blockSize > 0) {
                        blockChoice = chooseSpot(blockSize);
                        spawnGrid[j - blockSize + blockChoice][i] = true;
                        numSpots++;
                    }
                    blockSize = 0;
                }
            }
			
							
            if(blockSize >= 1) {
                blockChoice = chooseSpot(blockSize);
                spawnGrid[x - blockSize + blockChoice][i] = true;
                numSpots++;
            }
			
            //until rest of method is dealing with not having the correct amount of spots in the path in rows at this point
            while(numSpots > openSpots) {
                for(int j=0; (j<x); j++) {
                    if(Math.random() < 0.5 && numSpots > openSpots) {
                        if(spawnGrid[j][i]) {
                            spawnGrid[j][i] = false;
                            numSpots--;
                        }
                    }
                }
            }			
            //adds remaining open spots into the row;
            while(numSpots < openSpots) {
                for(int j=0; (j<x); j++) {
                    if(Math.random() < 0.001 && spawnGrid[j][i] == false && numSpots < openSpots) {
                        spawnGrid[j][i] = true;
                        numSpots++;
                    }
                }
            }
			
            blockSize = 0;
            blocks = 0;
            //fixing problem with algorithm, when all single width blocks cause a loop
            for(int j=0; j<x; j++) {
                if(spawnGrid[j][i]==true) {
                    blockSize++;
                }
                else {
                    if(blockSize == 1) {
                        blocks++;
                    }
                    blockSize = 0;
                }
            }
            //if last spot in row is in the path
            if(blockSize == 1) {
                blocks++;
            }
			
            numSpots = 0;
            if(blocks == openSpots) {
                while(numSpots < 1) {
                    for(int j=0; (j<x); j++) {
                        if((int)(Math.random() * 100) % randomness == 0
                                 && numSpots < 1 && spawnGrid[j][i] == false) {
                            spawnGrid[j][i] = true;
                            numSpots++;
                            break;
                        }
                    }
                }
            }

            blocks = 0;
            blockSize = 0;
            numSpots = 0;
        }
		
        /*for(int i = 0; i<y; i++) {
            for(int j = 0; j<x; j++) {
                if(spawnGrid[j][i])
                    System.out.print("o");
                else
                    System.out.print("x");
            }
            System.out.println();
        }*/
        return spawnGrid;
    }
    
    private int chooseSpot(int n) {
		return (int)(Math.random()*100) % n;
    }

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
}