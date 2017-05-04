package edu.student.android.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import static android.R.attr.mode;
import static android.R.string.no;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "ScarnesDiceApp";
    private int user_overall_score = 0;
    private int user_turn_score = 0;
    private int computer_overall_score = 0;
    private int computer_turn_score = 0;
    private Button roll,reset,hold,inc,dec;
    private ImageView diceImage1,diceImage2;
    private TextView status,counter;
    private ArrayList<String> diceImages;
    private boolean isTwoDice,isFastMode;
    private Random random = new Random();
    private int i=1;
    final Handler timerHandler = new Handler();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_scarnes, menu);
        MenuItem item = menu.findItem(R.id.two_dice);
        item.setChecked(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.two_dice) {
            item.setChecked(!item.isChecked());
            if(item.isChecked()) {
                Log.v(TAG,"Two Dice Mode On");
                isTwoDice = true;
                diceImage2.setVisibility(View.VISIBLE);
                user_overall_score=0;
                user_turn_score=0;
                computer_overall_score=0;
                computer_turn_score=0;
                hold.setVisibility(View.VISIBLE);
                roll.setVisibility(View.VISIBLE);
                status.setText("Your score: "+user_overall_score+"\ncomputer score: "+computer_overall_score+"\nyour turn score: "+user_turn_score);


            }else{
                Log.v(TAG,"Two Dice Mode OFF");
                isTwoDice = false;
                diceImage2.setVisibility(View.GONE);
                user_overall_score=0;
                user_turn_score=0;
                computer_overall_score=0;
                computer_turn_score=0;
                hold.setVisibility(View.VISIBLE);
                roll.setVisibility(View.VISIBLE);
                status.setText("Your score: "+user_overall_score+"\ncomputer score: "+computer_overall_score+"\nyour turn score: "+user_turn_score);

            }

        }
        else if(id == R.id.face_dice) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                Log.v(TAG, "Fast Mode On");
                isFastMode = true;
                diceImage2.setVisibility(View.GONE);
                user_overall_score = 0;
                user_turn_score = 0;
                computer_overall_score = 0;
                computer_turn_score = 0;
                inc.setVisibility(View.VISIBLE);
                dec.setVisibility(View.VISIBLE);
                counter.setVisibility(View.VISIBLE);
                hold.setVisibility(View.GONE);
                roll.setVisibility(View.VISIBLE);
                counter.setText(String.valueOf(i));

                status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nyour turn score: " + user_turn_score);

            }
            else{
                Log.v(TAG, "Fast Mode Off");
                isFastMode = false;
                diceImage2.setVisibility(View.GONE);
                user_overall_score = 0;
                user_turn_score = 0;
                computer_overall_score = 0;
                computer_turn_score = 0;
                inc.setVisibility(View.GONE);
                dec.setVisibility(View.GONE);
                counter.setVisibility(View.GONE);
                hold.setVisibility(View.VISIBLE);
                roll.setVisibility(View.VISIBLE);
                status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nyour turn score: " + user_turn_score);

            }

        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFastMode= false;
        isTwoDice = false;
        roll = (Button) findViewById(R.id.roll);
        reset = (Button) findViewById(R.id.reset);
        hold = (Button) findViewById(R.id.hold);
        inc = (Button) findViewById(R.id.button);
        dec = (Button) findViewById(R.id.button2);
        counter = (TextView) findViewById(R.id.textView2);
        diceImage1 = (ImageView) findViewById(R.id.dice_img1);
        diceImage2 = (ImageView) findViewById(R.id.dice_img2);
        status = (TextView) findViewById(R.id.textView);
        inc.setVisibility(View.GONE);
        dec.setVisibility(View.GONE);
        counter.setVisibility(View.GONE);

            inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG,"Increment");
                    if(i>=1&& i<=10) {
                        i = i + 1;
                        counter.setText(String.valueOf(i));
                    }
                }
            });
            dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG,"Decrement");
                    if(i>1) {
                        i = i - 1;
                        counter.setText(String.valueOf(i));
                    }
                }
            });

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFastMode)
                rollDice("user",1);
                else{
                    rollDice("user",i);
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"Reset Clicked");
                user_overall_score=0;
                user_turn_score=0;
                computer_overall_score=0;
                computer_turn_score=0;
                hold.setVisibility(View.VISIBLE);
                roll.setVisibility(View.VISIBLE);
                status.setText("Your score: "+user_overall_score+"\ncomputer score: "+computer_overall_score+"\nyour turn score: "+user_turn_score);

            }
        });
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"Hold Clicked");
                user_overall_score = user_overall_score+user_turn_score;
                user_turn_score=0;
                status.setText("Your score: "+user_overall_score+"\ncomputer score: "+computer_overall_score+"\nyour turn score: "+user_turn_score);
                computerTurn();
            }
        });
    }

    private void rollDice(String turnOf,int noOfRolls) {
        if(!isFastMode) {
            //<editor-fold desc="When two dice and one dice">
            Log.e(TAG, "rollDice Method");
            int[] a = new int[6];
            int m = random.nextInt(6) + 1;
            int n = random.nextInt(6) + 1;
            for (int i = 1; i <= 6; i++) {
                String url = "drawable/" + "dice" + (i);
                a[i - 1] = getResources().getIdentifier(url, "drawable", getPackageName());
            }
            diceImage1.setImageResource(a[n - 1]);
            if (isTwoDice) {
                Log.e(TAG, "isTwoDice");
                int[] b = new int[6];

                for (int j = 1; j <= 6; j++) {
                    String url = "drawable/" + "dice" + (j);
                    b[j - 1] = getResources().getIdentifier(url, "drawable", getPackageName());
                }
                diceImage2.setImageResource(b[m - 1]);
            }

            if (isWinner()) {
                hold.setVisibility(View.GONE);
                roll.setVisibility(View.GONE);
            } else {
                if (!isTwoDice) {
                    Log.v(TAG, "One Dice Mode");
                    if (turnOf.equals("user")) {
                        Log.v(TAG, "Users Turn");
                        if (n != 1) {
                            Log.v(TAG, "User scored other than 1");
                            user_turn_score = user_turn_score + n;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nyour turn score: " + user_turn_score);
                        } else {
                            Log.v(TAG, "User scored 1");
                            user_turn_score = 0;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nyour turn score: " + user_turn_score);
                            computerTurn();
                        }
                    } else {
                        Log.v(TAG, "Computers Turn");
                        if (n != 1) {
                            Log.v(TAG, "Computers scored other than 1");
                            computer_turn_score = computer_turn_score + n;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\ncomputer turn score: " + computer_turn_score);
                            computerTurn();
                        } else {
                            Log.v(TAG, "Computers scored 1");
                            computer_turn_score = 0;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nComputer rolled a 1 ");
                            computerTurn();

                        }
                    }
                } else {
                    Log.v(TAG, "Two Dice Mode");
                    if (turnOf.equals("user")) {
                        Log.v(TAG, "Users Turn");
                        if (n == m) {
                            Log.v(TAG, "Both the dice have same value");
                            user_turn_score = user_turn_score + n + m;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nyour turn score: " + user_turn_score);
                        } else if (n != 1 && m != 1) {
                            Log.v(TAG, "User scored a " + n + " " + m + ".");
                            user_turn_score = user_turn_score + n + m;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nyour turn score: " + user_turn_score);
                        } else if (n == 1 || m == 1) {
                            Log.v(TAG, "User scored a " + n + " " + m + ".");
                            user_turn_score = 0;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nyour turn score: " + user_turn_score);
                            System.out.println("User rolled a one");
                            computerTurn();
                        } else if (n == 1 && m == 1) {
                            Log.v(TAG, "User scored a " + n + " " + m + ".");
                            user_turn_score = 0;
                            user_overall_score = 0;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nyour turn score: " + user_turn_score);
                            System.out.println("User rolled two ones");
                            computerTurn();
                        }

                    } else {
                        Log.v(TAG, "Computers Turn");
                        if (n == m) {
                            Log.v(TAG, "Both the dice have same value");
                            computer_turn_score = computer_turn_score + n + m;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\ncomputer turn score: " + computer_turn_score);
                            computerTurn();
                        } else if (n != 1 && m != 1) {
                            Log.v(TAG, "Computer scored a " + n + " " + m + ".");
                            computer_turn_score = computer_turn_score + n + m;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\ncomputer turn score: " + computer_turn_score);
                            computerTurn();
                        } else if (n == 1 || m == 1) {
                            Log.v(TAG, "Computer scored a " + n + " " + m + ".");
                            computer_turn_score = 0;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nComputer rolled a 1 ");
                            System.out.println("Computer rolled a one");
                            computerTurn();
                        } else if (n == 1 && m == 1) {
                            Log.v(TAG, "Computer scored a " + n + " " + m + ".");
                            computer_turn_score = 0;
                            computer_overall_score = 0;
                            status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nComputer rolled a 1 ");
                            System.out.println("Computer rolled two ones");
                        }
                   /* if (n != 1) {
                        computer_turn_score = computer_turn_score + n;
                        status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\ncomputer turn score: " + computer_turn_score);
                        computerTurn();
                    } else {
                        computer_turn_score = 0;
                        status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nComputer rolled a 1 ");
                        System.out.println("computer rolled a one");
                        computerTurn();

                    }*/
                    }
                }
            }
            //</editor-fold>
        }else{
            isFastModeRollDice(turnOf,noOfRolls);
        }
    }

    private void isFastModeRollDice(String turnOf, int noOfRolls) {
        Log.v(TAG, "isFastModeRollDice - No of Rolls"+ noOfRolls);
        boolean s= false;

            Log.v(TAG, turnOf+" turn - fast mode");
            int[] a = new int[noOfRolls];
            int sum = 0;
            for (int i = 0; i < noOfRolls; i++) {
                a[i] = random.nextInt(6) + 1;
                Log.v(TAG, turnOf+" Rolled a "+a[i]);

                if (a[i] == 1) {
                    Log.v(TAG, turnOf+" Rolled a 1");
                    s = true;
                    break;
                }else{
                    sum+=a[i];
                }
            }
        if(turnOf.equals("user")){
            if (s) {
                Log.v(TAG, turnOf+" rolled a 1 - fast mode");
                user_turn_score = 0;
                status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nyour turn score: " + user_turn_score);
                computerTurn();
            }else{
                Log.v(TAG, turnOf+" rolled no 1 in the no of rolls Total Sum "+sum+" - fast mode");
                user_turn_score = user_turn_score + sum;
                user_overall_score = user_overall_score+user_turn_score;
                status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\nyour turn score: " + user_turn_score);
                computerTurn();
            }
        }else{
            if (s) {
                Log.v(TAG, turnOf+" rolled a 1 - fast mode");
                computer_turn_score = 0;
                status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\ncomputer turn score: " + computer_turn_score);

            }else{
                Log.v(TAG, turnOf+" rolled no 1 in the no of rolls - fast mode");
                computer_turn_score = computer_turn_score + sum;
                status.setText("Your score: " + user_overall_score + "\ncomputer score: " + computer_overall_score + "\ncomputer turn score: " + computer_turn_score);

            }
        }
    }

    private boolean isWinner(){
        if(computer_overall_score >=100 || user_overall_score >=100){
            if(user_overall_score>=100){
                Log.v(TAG,"User scored a 100");
                status.setText("You have won the game");
                return true;
            }else{
                Log.v(TAG,"Computer scored a 100");
                status.setText("You lost the game! Computer won this one.");
                return true;
            }
        }
        return false;
    }
    private void computerTurn() {
        Log.e(TAG, "computerTurn method");
        roll.setVisibility(View.GONE);
        hold.setVisibility(View.GONE);
        System.out.println("Computer Score: " + computer_turn_score);
        if (!isFastMode) {
            if (computer_turn_score < 20) {
                Log.v(TAG, "Computer Turn Score is less than 20");
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFastMode)
                            rollDice("computer", 1);
                        else {
                            rollDice("computer", 5);
                        }
                    }
                }, 1000);
            } else {
                Log.v(TAG, "Computer Turn Score is more than 20");
                computer_overall_score = computer_overall_score + computer_turn_score;
                computer_turn_score = 0;
                status.setText("Your score: " + user_overall_score + "\n computer score: " + computer_overall_score + "\nComputer holds ");
                if (isWinner()) {
                    Log.v(TAG, "Computer Scored a Hundred");
                    roll.setVisibility(View.GONE);
                    hold.setVisibility(View.GONE);
                } else {
                    Log.v(TAG, "Computer Holds and Overall Score not greater than 100");
                    roll.setVisibility(View.VISIBLE);
                    hold.setVisibility(View.VISIBLE);
                }

            }

        }
    }
}
