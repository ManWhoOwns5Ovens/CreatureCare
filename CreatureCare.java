/* ***************************************
  @author    David Valsan
  @SID       240487193
  @date      24 October 2024
  @version   3
  @level     8

    A program that alllows the user to choose and
    care for a mythical creature.
   ****************************************/

import java.util.Scanner;
import java.util.Random;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

class PlayerCreature
    {
        String species;
        String name;
        int hunger;
        int fun;
        int magic;
        int currentEnemyID;
        boolean gameOver;
        boolean gameWon;
    }

class EnemyCreature
    {
        String species;
        int magic;
    }

public class CreatureCare
    {
        //takes string from user and returns it
        //makes program easier to read
        //scanner does not have to to be declared every function that takes player input
        //in this program the player only needs to input a string
        public static String inputString(String message)
        {
            Scanner scanner= new Scanner(System.in);
            System.out.println(message);
            return scanner.nextLine();
        }

        //general method
        //uses Random() to generate a random integer
        //limit is passed in as argument- generate number from 0 to (limit-1)
        public static int genRandomInt(int limit)
        {
            Random random= new Random();
            return random.nextInt(limit);
        }

        ////////////////////////////////////////////////////////////
        
        //creates record for a new player creature
        public static PlayerCreature createPlayerCreature(String nSpecies, String nName)
        {
            PlayerCreature nCreature= new PlayerCreature(); 
            nCreature.species=nSpecies;
            nCreature.name=nName;
            nCreature.hunger=0;
            nCreature.fun=0;
            nCreature.magic=0;
            nCreature.currentEnemyID=0;
            nCreature.gameOver=false;
            nCreature.gameWon=false;

            return nCreature;
        }

        //creates record for enemy creature
        public static EnemyCreature createEnemyCreature(String nSpecies, int nMagic)
        {
            EnemyCreature nCreature= new EnemyCreature();
            nCreature.species=nSpecies;
            nCreature.magic=nMagic;

            return nCreature;
        }

        //run once, at the beginning of the program
        //initialises an array of pre-determined enemies for the player's creature to fight
        public static EnemyCreature[] initiateEnemies(int magicMax)
        {
            final String[] enemySpecies={"Goblin", "Siren", "Minotaur", "Giant", "Dragon"};
            final int[] enemyMagic={(int)(Math.ceil(0.1*magicMax)), (int)(Math.ceil(0.2*magicMax)), (int)(Math.ceil(0.5*magicMax)), (int)(Math.ceil(0.7*magicMax)), magicMax};
            EnemyCreature[] enemyCreatures=new EnemyCreature[5];
            
            for (int i=0; i<=enemySpecies.length-1; i++)
                {
                    enemyCreatures[i]= new EnemyCreature();
                    enemyCreatures[i]= createEnemyCreature( enemySpecies[i], enemyMagic[i]);
                }
            return enemyCreatures;
        }

        ////////////////////////////////////////////////////////////

        //getter accessor methods -player
        public static String getPlayerSpecies(PlayerCreature pc)
        {
            return pc.species;
        }
        public static String getName(PlayerCreature pc)
        {
            return pc.name;
        }
        public static int getHunger(PlayerCreature pc)
        {
            return pc.hunger;
        }
        public static int getFun(PlayerCreature pc)
        {
            return pc.fun;
        }
        public static int getPlayerMagic(PlayerCreature pc)
        {
            return pc.magic;
        }
        public static int getCurrentEnemy(PlayerCreature pc)
        {
            return pc.currentEnemyID;
        }
        public static boolean getGameOver(PlayerCreature pc)
        {
            return pc.gameOver;
        }
        public static boolean getGameWon(PlayerCreature pc)
        {
            return pc.gameWon;
        }

        //getter accessor methods -enemy
        public static String getEnemySpecies(EnemyCreature ec)
        {
            return ec.species;
        }
        public static int getEnemyMagic(EnemyCreature ec)
        {
            return ec.magic;
        }
        
        ////////////////////////////////////////////////////////////

        //setter accessor methods (player only)
        public static PlayerCreature setName(PlayerCreature pc, String nName)
        {
            pc.name=nName;
            return pc;
        }
        public static PlayerCreature setHunger(PlayerCreature pc, int hungerModifier)
        {
            pc.hunger+=hungerModifier;
            if(pc.hunger>10)
            {
                pc.hunger=10;
            }
            return pc;
        }
        public static PlayerCreature setFun(PlayerCreature pc, int funModifier)
        {
            pc.fun+=funModifier;
            if(pc.fun>10)
            {
                pc.fun=10;
            }
            return pc;
        }
        public static PlayerCreature setMagic(PlayerCreature pc, int magicModifier)
        {
            pc.magic+=magicModifier;
            return pc;
        }
        public static PlayerCreature setCurrentEnemy(PlayerCreature pc, int nID)
        {
            pc.currentEnemyID+=nID;
            return pc;
        }

        public static PlayerCreature setGameOver(PlayerCreature pc, boolean nGO)
        {
            pc.gameOver=nGO;
            return pc;
        }
        public static PlayerCreature setGameWon(PlayerCreature pc, boolean nGW)
        {
            pc.gameWon=nGW;
            return pc;
        }

        ////////////////////////////////////////////////////////////

        //increase hunger(feed/become less hungry)
        public static PlayerCreature feed(PlayerCreature creature)
        {
            System.out.println("You fed "+getName(creature) +".");
            return setHunger(creature, 3);
        }

         //increase fun
        public static PlayerCreature play(PlayerCreature creature)
        {
            System.out.println("You played fetch with "+getName(creature)+".");
            return setFun(creature, 3);
        }
        
        //increase magic
        public static PlayerCreature train(PlayerCreature creature)
        {
            System.out.println("You trained "+getName(creature)+" in the arcane.");
            return setMagic(creature, 5);
        }

        //passively decreases the creature's stats every round
        public static PlayerCreature nextRound(PlayerCreature creature, boolean isHunger, boolean isFun)
        {
            if(isHunger)
            {
                creature=setHunger(creature, -(genRandomInt(4)));
            }
            if(isFun)
            {
                creature=setFun(creature, -(genRandomInt(4)));
            }
            
            
            return creature;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        //allows player to input their creature's species
        //double checks with them as the species cannot be later changed
        public static String inputSpecies()
        {
            String temp="", speciesAnswer="";
            while(temp.equals("n") | !temp.equals("y"))
                {
                    temp="";
                    speciesAnswer=inputString("What species is your creature?");
                    while(!temp.equals("y") & !temp.equals("n"))
                        {
                            temp=inputString("Are you sure? You cannot change this later.(y/n)");
                        }
                   
                }
            return speciesAnswer;
        }

        //calls method to take input from the user
        //asks for name
        //no double-check as this can be changed
        public static String inputName()
        {
            return inputString("Name you creature.(This can be changed later)");
        }

        //displays current stats and their values
        public static void displayStatus(PlayerCreature creature)
        {
            System.out.println("\n ");
            System.out.println("Name: "+getName(creature));
            System.out.println("Hunger: "+getHunger(creature));
            System.out.println("Fun: "+getFun(creature));
            System.out.println("Magic: "+getPlayerMagic(creature));
            return;
        }

        //displays every action the player can take (input)
        //normal round
        public static String displayActions()
        {
            System.out.println("\n ");
            System.out.println(">Feed");
            System.out.println(">Play");
            System.out.println(">Train");
            System.out.println(">Check stats");
            System.out.println(">Change name");
            System.out.println(">New creature");
            System.out.println(">Battle");
            System.out.println(">Save");
            System.out.println(">Load");
            System.out.println(">Quit");
            return inputString("");
        }

        
        //display available commands
        public static String displayCreatureAttackCommands(PlayerCreature creature)
        {
            System.out.println("\n ");
            System.out.println("Your "+getPlayerSpecies(creature)+" is unhappy with your care.");
            System.out.println(">Run");
            System.out.println(">Fight back");
            return inputString("");
        }




        //allows user to fight back if attacked by their creature
        //but the stronger the creature raised the higher the chance of escaping
        public static boolean fightBack(PlayerCreature creature, int magicMax)
        {
            if(genRandomInt(magicMax+1)>getPlayerMagic(creature))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        //alternate strategy
        //50% chance of escape
        public static boolean runAway()
        {
            if(genRandomInt(2)==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        //write the values of the player creature record to an external file
        //each value is placed on a different row
        //gameOver and gameWon are not saved, as if either is set to true this always means the game has ended
        public static void saveGame(PlayerCreature pc) throws IOException
        {
            PrintWriter outputStream = new PrintWriter (new FileWriter("MythicCreatureCareSave.txt"));
            outputStream.println(getPlayerSpecies(pc));
            outputStream.println(getName(pc));
            outputStream.println(getHunger(pc));
            outputStream.println(getFun(pc));
            outputStream.println(getPlayerMagic(pc));
            outputStream.println(getCurrentEnemy(pc));
            outputStream.close();
        }

        //if data had been written to the save file, it takes each value from each row and assigns it to the correct record field
        // these will always be in the same order
        public static PlayerCreature loadGame() throws IOException
        {
            BufferedReader inputStream = new BufferedReader (new FileReader("MythicCreatureCareSave.txt"));
            String temp =inputStream.readLine();
            if(temp!=null)
            {
                PlayerCreature playerCreature= new PlayerCreature();
                playerCreature=createPlayerCreature(temp,inputStream.readLine());
                playerCreature=setHunger(playerCreature,Integer.parseInt(inputStream.readLine()));
                playerCreature=setFun(playerCreature,Integer.parseInt(inputStream.readLine()));
                playerCreature=setMagic(playerCreature,Integer.parseInt(inputStream.readLine()));
                playerCreature= setCurrentEnemy(playerCreature,Integer.parseInt(inputStream.readLine()));
                inputStream.close();
                return playerCreature;
            }
            inputStream.close();
            return null;
        }

        /////////////////////////////////////////////////////////////////
        
        //runs when game has ended
        //print appropriate message, based on the passed in gameWon boolean
        //either a win or a loss
        public static void printFinalMessage(boolean gameWon, PlayerCreature pc)
        {
            //runs when game has ended
            if(getGameOver(pc))
            {
                System.out.println("YOU WON! Congratulations "+getName(pc)+" is the strongest magical beast.");
            }
            else
            {
                System.out.println("You died! \n GAME OVER");
            }
            return;
        }

        //compares stats of the passed in player and enemy creature records
        // return 1,0 or -1 representing the result of the battle
        //1= win; 0=draw; -1=loss
        //outcome decided by their magic attributes
        public static int battleEnemy(PlayerCreature playerCreature, EnemyCreature[] enemyCreatures, int currentEnemyID)
        {
            if(getPlayerMagic(playerCreature)> getEnemyMagic(enemyCreatures[currentEnemyID]))
            {
                System.out.println(getName(playerCreature)+" defeated the "+ getEnemySpecies(enemyCreatures[currentEnemyID]));
                return 1;
            }
            else if (getPlayerMagic(playerCreature)< getEnemyMagic(enemyCreatures[currentEnemyID]))
            {
                System.out.println(getName(playerCreature)+" was defeated in combat with the "+ getEnemySpecies(enemyCreatures[currentEnemyID]));
                System.out.println("You managed to escape while "+getName(playerCreature)+" became a meal.");
                return -1;
            }
            else
            {
                System.out.println(getName(playerCreature)+ " and the " + getEnemySpecies(enemyCreatures[currentEnemyID]) +" were evenly matched.");
                System.out.println("The two of you managed to escape the encounter.");
                return 0;
            }
        }

        //separated from rest of the method so it should only run in the case of valid input
        //checks value of the "sruvived" boolean passsed
        //player's progress is either reset or the game is lost
        public static PlayerCreature checkForSurvival(boolean survived, PlayerCreature pc)
        {
            if (survived)
            {
                System.out.println("You survived the encounter but your "+getPlayerSpecies(pc)+ " ran away. \n Time to start again.");
                pc=createPlayerCreature(inputSpecies(), inputName()); 
            }
            else
            {
                pc=setGameOver(pc, true);
            }
            return pc;
        }

        //separated from main, runs when the creature goes rabid(fun or hunger stat <=-10)
        public static PlayerCreature creatureRabid(PlayerCreature playerCreature, int magicMax)
        {
            String answer=displayCreatureAttackCommands(playerCreature).toLowerCase();
            boolean survived=false;

           if (answer.equals("fight back"))
            {
                playerCreature=checkForSurvival(fightBack(playerCreature, magicMax), playerCreature);
            }
            else if (answer.equals("run"))
            {
                playerCreature=checkForSurvival(runAway() , playerCreature);
            }
            else
            {
                System.out.println("Could not recognise command.");
            }
            
            return playerCreature;
        }

        //regular round, separated from main()
        //runs appropriate methods based on the action the player input
        public static PlayerCreature regularRound(PlayerCreature playerCreature, EnemyCreature[] enemies) throws IOException
        {
            String answer=displayActions().toLowerCase();
            int battleResult;
            if (answer.equals("feed"))
            {
                playerCreature=feed(playerCreature);
                playerCreature=nextRound(playerCreature, false, true);
            }
            else if (answer.equals("play"))
            {
                playerCreature=play(playerCreature);
                playerCreature=nextRound(playerCreature, true, false);
            }
            else if (answer.equals("train"))
            {
                playerCreature=train(playerCreature);
                playerCreature=nextRound(playerCreature, true, true);
            }
            else if (answer.equals("check stats"))
            {
               displayStatus(playerCreature);
            }
            else if (answer.equals("change name"))
            {
                playerCreature=setName(playerCreature, inputName());
            }
            else if (answer.equals("new creature"))
            {
                System.out.println("You abandoned "+getName(playerCreature)+". You monster.");
                playerCreature=createPlayerCreature(inputSpecies(), inputName()); 
            }
            else if (answer.equals("battle"))
            {
                answer="y";
                while (!answer.equals("n") && getCurrentEnemy(playerCreature)<5 && answer.equals("y"))
                {
                    battleResult= battleEnemy(playerCreature, enemies, getCurrentEnemy(playerCreature));
                    if(battleResult==1)
                    {
                        playerCreature=setCurrentEnemy(playerCreature,1);
                        if (getCurrentEnemy(playerCreature)>=enemies.length)//game is won, all enemies defeated, breaks out the loop
                        {
                            playerCreature=setGameOver(playerCreature, true);
                            playerCreature=setGameWon(playerCreature, true);
                        }
                        else
                        {
                            answer=inputString("Would you like to battle the next enemy?(y/n)"); 
                            playerCreature=nextRound(playerCreature, true, true);
                        }
                        
                        
                    }
                    else if (battleResult==-1)//loss = reset
                    {
                        playerCreature=createPlayerCreature(inputSpecies(), inputName()); 
                        answer="n";
                    }
                    else//draw, can only be 0(no point in extra check)
                    {
                        playerCreature=nextRound(playerCreature, true, true);
                        answer=inputString("Would you like to battle the next enemy?(y/n)"); 
                    }
                    
                }
            }
                
            else if (answer.equals("save"))
            {
                saveGame(playerCreature);
            }
            else if (answer.equals("load"))
            {
                if(loadGame()==null)
                {
                    System.out.println("Save file was empty, no creature can be retrieved.");
                }
                else
                {
                    playerCreature=loadGame();
                }
            }
                
            else if (answer.equals("quit"))
            {
                playerCreature=setGameOver(playerCreature, true);
            }

            else
            {
                System.out.println("Could not recognise command.");
            }
            return playerCreature;
            
        }
        
        public static void main(String[] a) throws IOException
        {   
            PlayerCreature playerCreature=new PlayerCreature();
            final int magicMax=10;

           EnemyCreature[] enemyCreatures= initiateEnemies(magicMax);
           playerCreature=createPlayerCreature(inputSpecies(), inputName()); 
            
            while(!getGameOver(playerCreature))
                {
                    if (getHunger(playerCreature)<-10 | getFun(playerCreature) <-10)// creature goes rabid
                    {
                        playerCreature=creatureRabid(playerCreature, magicMax);
                    }
                    
                    else//regular turn
                    {
                        playerCreature=regularRound(playerCreature, enemyCreatures);
                    }
                    
                        
                }
            
            printFinalMessage(getGameWon(playerCreature), playerCreature);
            
            return;
            
        }
    }