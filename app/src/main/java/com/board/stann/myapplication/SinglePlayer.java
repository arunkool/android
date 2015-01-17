package com.board.stann.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class SinglePlayer extends Activity implements OnClickListener {

    private TextView[] texts;
    private ImageView[] pits;
    private ImageView[] upPits;
    private ImageView[] downPits;

    private String playerName;
    private boolean playerWon;
    private boolean captureFlag = false;
    private Date gameStart = new Date();
    private List<Integer> cpuValidLocations = new ArrayList<>();
    private HashMap<Integer, String> imageViewPitsByIndex = new HashMap<>();

    private int player = 1;
    private int movementsNumber = 0;
    private int selectedDownPit[] = {0, 1, 2, 3, 4, 5};
    private int selectedUpPit[] = {7, 8, 9, 10, 11, 12};

    private int stones[] = {R.drawable.emptypit, R.drawable.stone1, R.drawable.stones2, R.drawable.stones3, R.drawable.stones4, R.drawable.stones5};

    private int trayStonesDown[] = {R.drawable.tray, R.drawable.tray1, R.drawable.tray2, R.drawable.tray3, R.drawable.tray4, R.drawable.tray5, R.drawable.tray6,
            R.drawable.tray7, R.drawable.tray8, R.drawable.tray9, R.drawable.tray10, R.drawable.tray11, R.drawable.tray12,
            R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13,
            R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13,R.drawable.tray13, R.drawable.tray13,R.drawable.tray13, R.drawable.tray13,
            R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13};

    private int trayStonesUp[] = {R.drawable.tray, R.drawable.tray1, R.drawable.tray2, R.drawable.tray3, R.drawable.tray4, R.drawable.tray5, R.drawable.tray6,
            R.drawable.tray7, R.drawable.tray8, R.drawable.tray9, R.drawable.tray10, R.drawable.tray11, R.drawable.tray12,
            R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13,
            R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13,R.drawable.tray13, R.drawable.tray13,R.drawable.tray13, R.drawable.tray13,
            R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13, R.drawable.tray13};

    private static int[] board = new int[14];

    public enum Players {
        One((board.length - 2) / 2, board.length - 1), Two(board.length - 1,
                (board.length - 2) / 2);

        private int trayLocation;
        private int traySkip;

        Players(int location, int skip) {
            trayLocation = location;
            traySkip = skip;
        }

        int getTray() {
            return board[trayLocation];
        }

        public void setTraySkip(int traySkip) {
            this.traySkip = traySkip;
        }

        int getTrayLoc() {
            return trayLocation;
        }

        int getSkip() {
            return traySkip;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_singleplayer);
        loadInterface();
        setPlayerArea();

        turnOn(player);
        gameStart = new Date();
        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
        delay();
    }

    private void setPlayerArea() {
        if (player == 1 && !isGameOver()) {
            for (int i = 0; i < upPits.length; i++) {
                upPits[i].setEnabled(false);
                downPits[i].setEnabled(true);
            }

        } else if (player == 2 && !isGameOver()) {

            for (int i = 0; i < downPits.length; i++) {
                downPits[i].setEnabled(false);
                upPits[i].setEnabled(true);
            }
        } else
            showWinner();
    }

    public boolean isGameOver() {
        int count = 0;
        if (player == 1) {
            for (int i = 0; i < 6; i++) {
                count += Integer.parseInt(texts[i].getText().toString());
            }
            return count == 0;
        }

        if (player == 2) {
            for (int i = 7; i < 13; i++) {
                count += Integer.parseInt(texts[i].getText().toString());
            }
            return count == 0;
        }
        return false;
    }


    public void loadInterface() {
        TextView text1 = (TextView) findViewById(R.id.down_label1);
        TextView text2 = (TextView) findViewById(R.id.down_label2);
        TextView text3 = (TextView) findViewById(R.id.down_label3);
        TextView text4 = (TextView) findViewById(R.id.down_label4);
        TextView text5 = (TextView) findViewById(R.id.down_label5);
        TextView text6 = (TextView) findViewById(R.id.down_label6);
        TextView text7 = (TextView) findViewById(R.id.trayrighttext);
        TextView text8 = (TextView) findViewById(R.id.up_label1);
        TextView text9 = (TextView) findViewById(R.id.up_label2);
        TextView text10 = (TextView) findViewById(R.id.up_label3);
        TextView text11 = (TextView) findViewById(R.id.up_label4);
        TextView text12 = (TextView) findViewById(R.id.up_label5);
        TextView text13 = (TextView) findViewById(R.id.up_label6);
        TextView text14 = (TextView) findViewById(R.id.traylefttext);
        ImageView pit1 = (ImageView) findViewById(R.id.down_pit1);
        ImageView pit2 = (ImageView) findViewById(R.id.down_pit2);
        ImageView pit3 = (ImageView) findViewById(R.id.down_pit3);
        ImageView pit4 = (ImageView) findViewById(R.id.down_pit4);
        ImageView pit5 = (ImageView) findViewById(R.id.down_pit5);
        ImageView pit6 = (ImageView) findViewById(R.id.down_pit6);
        ImageView pit7 = (ImageView) findViewById(R.id.trayright);
        ImageView pit8 = (ImageView) findViewById(R.id.up_pit1);
        ImageView pit9 = (ImageView) findViewById(R.id.up_pit2);
        ImageView pit10 = (ImageView) findViewById(R.id.up_pit3);
        ImageView pit11 = (ImageView) findViewById(R.id.up_pit4);
        ImageView pit12 = (ImageView) findViewById(R.id.up_pit5);
        ImageView pit13 = (ImageView) findViewById(R.id.up_pit6);
        ImageView pit14 = (ImageView) findViewById(R.id.trayleft);

        texts = new TextView[]{text1, text2, text3, text4, text5, text6,
                text7, text8, text9, text10, text11, text12, text13, text14};

        pits = new ImageView[]{pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8,
                pit9, pit10, pit11, pit12, pit13, pit14};

        upPits = new ImageView[]{pit8, pit9, pit10, pit11, pit12, pit13};

        downPits = new ImageView[]{pit1, pit2, pit3, pit4, pit5, pit6};

        cpuValidLocations.add(7);
        cpuValidLocations.add(8);
        cpuValidLocations.add(9);
        cpuValidLocations.add(10);
        cpuValidLocations.add(11);
        cpuValidLocations.add(12);

        imageViewPitsByIndex.put(7, "up_pit1");
        imageViewPitsByIndex.put(8, "up_pit2");
        imageViewPitsByIndex.put(9, "up_pit3");
        imageViewPitsByIndex.put(10, "up_pit4");
        imageViewPitsByIndex.put(11, "up_pit5");
        imageViewPitsByIndex.put(12, "up_pit6");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.down_pit1:
                moveStones(v.getId(), selectedDownPit[0]);
                break;
            case R.id.down_pit2:
                moveStones(v.getId(), selectedDownPit[1]);
                break;
            case R.id.down_pit3:
                moveStones(v.getId(), selectedDownPit[2]);
                break;
            case R.id.down_pit4:
                moveStones(v.getId(), selectedDownPit[3]);
                break;
            case R.id.down_pit5:
                moveStones(v.getId(), selectedDownPit[4]);
                break;
            case R.id.down_pit6:
                moveStones(v.getId(), selectedDownPit[5]);
                break;
            case R.id.up_pit1:
                moveStones(v.getId(), selectedUpPit[0]);
                break;
            case R.id.up_pit2:
                moveStones(v.getId(), selectedUpPit[1]);
                break;
            case R.id.up_pit3:
                moveStones(v.getId(), selectedUpPit[2]);
                break;
            case R.id.up_pit4:
                moveStones(v.getId(), selectedUpPit[3]);
                break;
            case R.id.up_pit5:
                moveStones(v.getId(), selectedUpPit[4]);
                break;
            case R.id.up_pit6:
                moveStones(v.getId(), selectedUpPit[5]);
                break;
        }
    }

    private void moveStones(final int pitId, final int selectedPit) {
        ImageView currentPit = (ImageView) findViewById(pitId);
        currentPit.setImageResource(R.drawable.emptypit);
        int selectedPitStones = Integer.parseInt(texts[selectedPit].getText().toString());

        if (selectedPitStones == 0) return;

        if (player == 1)
            movementsNumber = movementsNumber + 1;

        texts[selectedPit].setText("" + 0);

        int pitNo = selectedPit;

        for (int i = 0; i < selectedPitStones; i++) {
            pitNo += 1;
            if (pitNo <= 13) {
                updateStones(pitNo);

            } else {
                pitNo = 0;
                updateStones(pitNo);
            }
        }

        captureFlag = isItPossibleToCapture(selectedPit, selectedPitStones);

        checkTray(selectedPit, selectedPitStones);
        setPlayerArea();
    }

    public void updateStones(int pitNo) {
        int updatedStones;
        if (pitNo == 6 && player == 2)
            pitNo += 1;
        if (pitNo == 13 && player == 1)
            pitNo = 0;

        updatedStones = Integer.parseInt(texts[pitNo].getText().toString()) + 1;

        texts[pitNo].setText("" + updatedStones);

        if (pitNo == 6)
            pits[pitNo].setImageResource(trayStonesDown[updatedStones]);
        else if (pitNo == 13)
            pits[pitNo].setImageResource(trayStonesUp[updatedStones]);
        else {
            if (updatedStones >= stones.length)
                pits[pitNo].setImageResource(stones[stones.length - 1]);
            else
                pits[pitNo].setImageResource(stones[updatedStones]);
        }
    }

    public boolean isItPossibleToCapture(int selectedPit, int selectedPitStones) {
        int lastPit = selectedPit + selectedPitStones;

        if (lastPit >= pits.length)
            lastPit = lastPit - pits.length;

        int lastPitStones = Integer.parseInt(texts[lastPit].getText().toString());
        int oppositePit = (pits.length - 1) - (lastPit + 1);

        if (lastPitStones == 1 && ((lastPit < 6 && player == 1) || (lastPit > 6 && lastPit < 13 && player == 2))) {
            int oppositePitStones = (Integer.parseInt(texts[oppositePit].getText().toString()));
            if (oppositePitStones > 0) {
                capture(lastPit, oppositePitStones, oppositePit);
                return true;
            }
        }
        return false;
    }

    public void capture(int lastPit, int oppositePitStones, int oppositePit) {
        int downTray = Integer.parseInt(texts[6].getText().toString()) + oppositePitStones + 1;
        int upTray = Integer.parseInt(texts[13].getText().toString()) + oppositePitStones + 1;

        pits[oppositePit].setImageResource(R.drawable.emptypit);
        texts[oppositePit].setText("" + 0);
        pits[lastPit].setImageResource(R.drawable.emptypit);
        texts[lastPit].setText("" + 0);

        if (player == 1) {
            texts[6].setText("" + downTray);
            pits[6].setImageResource(trayStonesDown[downTray]);
        } else {
            texts[13].setText("" + upTray);
            pits[13].setImageResource(trayStonesUp[upTray]);
        }

        if (isGameOver()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showWinner();
                }
            }, 1000);
        }
    }


    public void checkTray(int selectedPit, int selectedPitStones) {
        if (player == 1) {
            if (selectedPitStones + selectedPit == 6) {
                // Player 1 free turn (Human)
                turnOn(3);
            }
            else {
                // Player 2 turn (Computer)
                player = 2;
                turnOn(player);
            }
        } else if (player == 2) {
            if (selectedPitStones + selectedPit == 13) {
                // Player 2 free turn (Computer)
                turnOn(4);
            }
            else {
                // Player 1 turn (Human)
                player = 1;
                turnOn(player);
            }
        }
    }

    public void showWinner() {
        TextView score = (TextView) findViewById(R.id.score);
        ImageView gameOver = (ImageView) findViewById(R.id.gameover);
        ImageView playAgain = (ImageView) findViewById(R.id.playagain);
        ImageView gameQuit = (ImageView) findViewById(R.id.gamequit);

        int totalPlayer1 = 0;
        int totalPlayer2 = 0;

        for (int i = 0; i < 6; i++) {
            totalPlayer1 += Integer.parseInt(texts[i].getText().toString());
            texts[i].setText("" + 0);
            pits[i].setImageResource(R.drawable.emptypit);

        }

        totalPlayer1 += Integer.parseInt(texts[6].getText().toString());
        texts[6].setText("" + totalPlayer1);
        pits[6].setImageResource(trayStonesDown[totalPlayer1]);

        for (int i = 7; i < 13; i++) {
            totalPlayer2 += Integer.parseInt(texts[i].getText().toString());
            pits[i].setImageResource(R.drawable.emptypit);
            texts[i].setText("" + 0);

        }

        totalPlayer2 += Integer.parseInt(texts[13].getText().toString());
        texts[13].setText("" + totalPlayer2);
        pits[13].setImageResource(trayStonesUp[totalPlayer2]);

        if (totalPlayer1 > totalPlayer2) {
            gameOver.setImageResource(R.drawable.gameover1);
            score.setText("" + totalPlayer1);
            playerWon = true;
        } else if (totalPlayer1 < totalPlayer2) {
            gameOver.setImageResource(R.drawable.gameover2);
            score.setText("" + totalPlayer2);
            playerWon = false;
        } else {
            //gameOver.setImageResource(R.drawable.gamedraw);//game draw
        }
        gameOver.setVisibility(View.VISIBLE);
        score.setVisibility(View.VISIBLE);
        playAgain.setVisibility(View.VISIBLE);
        gameQuit.setVisibility(View.VISIBLE);

        SavePlayer savePlayer = new SavePlayer();
        savePlayer.savePlayerStatistics(getApplicationContext(), playerName, playerWon, movementsNumber, computeElapsedTime());
    }

    public void playAgain(View v) {
        Intent intent;
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void gameQuit(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Do you really want to exit?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void delay() {
        Timer myTimer = new Timer();
        MyTimerTask mTask = new MyTimerTask();
        myTimer.schedule(mTask, 1000);
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    turnOff();
                }
            });
        }
    }

    public void turnOff() {
        ImageView turnImage = (ImageView) findViewById(R.id.showturn);
        turnImage.setVisibility(View.INVISIBLE);
    }

    /**
     * This method controls the playing order of the game
     *
     * @param turn : Integer that identifies which player is allowed to move.
     *               1 : Player 1 turn (Human)
     *               2 : Player 2 turn (Computer)
     *               3 : Player 1 free turn (Human)
     *               4 : Player 2 free turn (Computer)
     */
    public void turnOn(int turn) {
        final ImageView turnImage = (ImageView) findViewById(R.id.showturn);
        TextView player1 = (TextView) findViewById(R.id.player1);
        TextView player2 = (TextView) findViewById(R.id.player2);

        // Player 1 turn (Human)
        if (turn == 1) {
            turnImage.setImageResource(R.drawable.turn_player1);
            player1.setTextColor(Color.GREEN);
            player2.setTextColor(Color.WHITE);
        }
        // Player 2 turn (Computer)
        else if (turn == 2) {
            turnImage.setImageResource(R.drawable.turn_player2);
            player2.setTextColor(Color.GREEN);
            player1.setTextColor(Color.WHITE);
            cpuPitSelect();
        }
        // Player 1 free turn (Human)
        else if (turn == 3) {
            turnImage.setImageResource(R.drawable.freeturn);
        }
        // Player 2 free turn (Computer)
        else if (turn == 4) {
            turnImage.setImageResource(R.drawable.freeturn);
            cpuPitSelect();
        }
        else
            showWinner();

        if (captureFlag) {
            captureFlag = false;
            turnImage.setImageResource(R.drawable.captured);
        }

        turnImage.setVisibility(View.VISIBLE);
        delay();
    }

    // Chooses a location for the computer to play
    // Returns an integer of the location
    public void cpuPitSelect() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Players computer = Players.Two;

                List<Integer> boardStones = new ArrayList<>();
                for (int i = 0; i < texts.length; i++) {
                    int pitStones = Integer.parseInt(texts[i].getText().toString());
                    boardStones.add(pitStones);
                }

                // If any location lets us go again, return the first we find
                for (int location : cpuValidLocations) {
                    if ((location + boardStones.get(location))
                            % getBoard().length == computer.getTrayLoc()) {

                        int pitId = getPitViewIdByPitLocation(location);
                        moveStones(pitId, location); return;
                    }
                }

                // If any location lets us capture stones, return the one that gives us
                // the most stones
                int maxStones = 0;
                int pos = cpuValidLocations.get(0);
                for (int location : cpuValidLocations) {
                    int landingPos = (location + boardStones.get(location)) % getBoard().length;
                    if (landingPos == 13)
                        continue;
                    int oppositePos = getOppositePit(landingPos);
                    if (boardStones.get(landingPos) == 0
                            && boardStones.get(oppositePos) > maxStones
                            && oppositePos != location
                            && isItPossibleToCapture(pos, boardStones.get(pos))) {
                        maxStones = boardStones.get(oppositePos);
                        pos = location;
                    }
                }

                // We found at least one opportunity to capture Stones, let's return it
                if (maxStones > 0) {
                    //System.out.println("All your Stones belong to me!");
                    int pitId = getPitViewIdByPitLocation(pos);
                    moveStones(pitId, pos); return;
                }

                // No particular location looks advantageous. Just return a random location
                boolean randomLocationHasStones = false;
                int randomLocation = 7;

                do {
                    randomLocation = cpuValidLocations.get(new Random().nextInt(cpuValidLocations.size()));
                    randomLocationHasStones = boardStones.get(randomLocation) > 0 ? true : false;
                } while (!randomLocationHasStones);

                int pitId = getPitViewIdByPitLocation(randomLocation);
                moveStones(pitId, randomLocation); return;
            }
        }, 2000);
    }

    public static int[] getBoard() {
        return board;
    }

    // Returns index of the opposite position on the board
    public int getOppositePit(int pos) {
        return getBoard().length - 2 - pos;
    }

    public int getPitViewIdByPitLocation(int pitLocation) {
        int viewId = getResources().getIdentifier(imageViewPitsByIndex.get(pitLocation), "id", getApplicationContext().getPackageName());
        return viewId;
    }

    public String computeElapsedTime() {
        Date gameEnd = new Date();

        long duration  = gameEnd.getTime() - gameStart.getTime();

        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

        String elapsedTime = diffInHours + ":" + diffInMinutes + ":" + diffInSeconds;
        return elapsedTime;
    }
}