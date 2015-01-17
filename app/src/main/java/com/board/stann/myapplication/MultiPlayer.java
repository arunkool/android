package com.board.stann.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MultiPlayer extends Activity implements OnClickListener {

    TextView[] texts;
    ImageView[] pits;
    ImageView[] upPits;
    ImageView[] downPits;

    private String playerName1;
    private String playerName2;
    private boolean player1Won;
    private Date gameStart = new Date();
    private int movementsNumberP1 = 0;
    private int movementsNumberP2 = 0;

    int player = 1;
    int selectedDownPit[] = {0, 1, 2, 3, 4, 5};
    int selectedUpPit[] = {7, 8, 9, 10, 11, 12};

    int stones[] = {R.drawable.emptypit, R.drawable.stone1, R.drawable.stones2, R.drawable.stones3, R.drawable.stones4, R.drawable.stones5, R.drawable.stones6, R.drawable.stones7, R.drawable.stones8, R.drawable.stones9,
            R.drawable.stones10, R.drawable.stones10, R.drawable.stones10, R.drawable.stones10, R.drawable.stones10, R.drawable.stones10, R.drawable.stones10};

    int trayStonesDown[] = {R.drawable.tray, R.drawable.tray1, R.drawable.tray2, R.drawable.tray3, R.drawable.tray4, R.drawable.tray5, R.drawable.tray6, R.drawable.tray7, R.drawable.tray8, R.drawable.tray9, R.drawable.tray10, R.drawable.tray11, R.drawable.tray12, R.drawable.tray13, R.drawable.tray14, R.drawable.tray15, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16,
            R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16,
            R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16};

    int trayStonesUp[] = {R.drawable.tray, R.drawable.tray1, R.drawable.tray2, R.drawable.tray3, R.drawable.tray4, R.drawable.tray5, R.drawable.tray6,
            R.drawable.tray7, R.drawable.tray8, R.drawable.tray9, R.drawable.tray10, R.drawable.tray11, R.drawable.tray12, R.drawable.tray13, R.drawable.tray14, R.drawable.tray15,
            R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16,
            R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16,
            R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16, R.drawable.tray16};
    boolean captureFlag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mutliplayer);

        loadInterface();
        setPlayerArea();

        turnOn(player);
        gameStart = new Date();
        Intent intent = getIntent();
        playerName1 = intent.getStringExtra("playerName1");
        playerName2 = intent.getStringExtra("playerName2");
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


        ImageView resume = (ImageView) findViewById(R.id.resume);
        ImageView pause = (ImageView) findViewById(R.id.pause);
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
            case R.id.pause:


                resume.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                for (int i = 0; i < pits.length; i++)
                    pits[i].setEnabled(false);
                break;
            case R.id.resume:


                resume.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                setPlayerArea();
                break;
            case R.id.playagain:
                playAgain(v);


            default:
                break;

        }


    }

    private void moveStones(int pitId, int selectedPit) {

        ImageView currentpit = (ImageView) findViewById(pitId);

        currentpit.setImageResource(R.drawable.emptypit);

        /*Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        currentpit.startAnimation(animation);*/
        int selectedPitStones = Integer.parseInt(texts[selectedPit].getText().toString());
        texts[selectedPit].setText("" + 0);
        if (selectedPitStones == 0)
            return;

        if (player == 1)
            movementsNumberP1 = movementsNumberP1 + 1;
        else
            movementsNumberP2 = movementsNumberP2 + 1;

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


        if (pitNo == 6) {

            pits[pitNo].setImageResource(trayStonesDown[updatedStones]);
        } else if (pitNo == 13) {

            pits[pitNo].setImageResource(trayStonesUp[updatedStones]);
        } else {

            pits[pitNo].setImageResource(stones[updatedStones]);

        }
        MediaPlayer mp;
        mp = MediaPlayer.create(MultiPlayer.this, R.raw.stonesound);
        mp.start();

    }


    public boolean isItPossibleToCapture(int selectedPit, int selectedPitStones) {


        int lastPit = selectedPit + selectedPitStones;
        if (lastPit >= pits.length)
            lastPit = lastPit - pits.length;
        int LastPitStones = Integer.parseInt(texts[lastPit].getText().toString());
        int oppositepit = (pits.length - 1) - (lastPit + 1);
        if (LastPitStones == 1 && ((lastPit < 6 && player == 1) || (lastPit > 6 && lastPit < 13 && player == 2))) {

            int oppositePitStones = (Integer.parseInt(texts[oppositepit].getText().toString()));
            if (oppositePitStones > 0) {
                capture(lastPit, oppositePitStones, oppositepit);
                return true;
            }

        }
        return false;
    }

    public void capture(int lastpit, int oppositePitStones, int oppositePit) {


        int downTray = Integer.parseInt(texts[6].getText().toString()) + oppositePitStones + 1;
        int upTray = Integer.parseInt(texts[13].getText().toString()) + oppositePitStones + 1;
        pits[oppositePit].setImageResource(R.drawable.emptypit);
        texts[oppositePit].setText("" + 0);
        pits[lastpit].setImageResource(R.drawable.emptypit);
        texts[lastpit].setText("" + 0);
        if (player == 1) {

            texts[6].setText("" + downTray);
            pits[6].setImageResource(trayStonesDown[downTray]);
        } else {
            texts[13].setText("" + upTray);
            pits[13].setImageResource(trayStonesUp[upTray]);

        }

    }

    public void checkTray(int selectedPit, int selectedPitStones) {

        if (player == 1) {

            if (selectedPitStones + selectedPit == 6)
                turnOn(3);
            else {
                player = 2;
                turnOn(player);
            }
        } else if (player == 2) {
            if (selectedPitStones + selectedPit == 13)
                turnOn(3);

            else {
                player = 1;
                turnOn(player);
            }
        }
    }

    public void showWinner() {
        ImageView gameOver = (ImageView) findViewById(R.id.gameover);
        TextView score = (TextView) findViewById(R.id.score);
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
            player1Won = true;
        } else if (totalPlayer1 < totalPlayer2) {
            gameOver.setImageResource(R.drawable.gameover2);
            score.setText("" + totalPlayer2);
            player1Won = false;
        } else {
            gameOver.setImageResource(R.drawable.gamedraw);//game draw
        }

        gameOver.setVisibility(View.VISIBLE);
        score.setVisibility(View.VISIBLE);
        playAgain.setVisibility(View.VISIBLE);
        gameQuit.setVisibility(View.VISIBLE);

        SaveMultiplayer saveMultiplayer = new SaveMultiplayer();
        saveMultiplayer.savePlayersStatistics(getApplicationContext(), playerName1, playerName2, player1Won, movementsNumberP1, movementsNumberP2, computeElapsedTime());
    }

    public void playAgain(View v) {

        ImageView back = (ImageView) findViewById(R.id.playagain);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
        ImageView turnimage = (ImageView) findViewById(R.id.showturn);

        turnimage.setVisibility(View.INVISIBLE);
    }

    public void turnOn(int turn) {


        ImageView turnImage = (ImageView) findViewById(R.id.showturn);
        TextView player1 = (TextView) findViewById(R.id.player1);
        TextView player2 = (TextView) findViewById(R.id.player2);


        if (turn == 1) {
            turnImage.setImageResource(R.drawable.turn_player1);
            player1.setTextColor(Color.GREEN);
            player2.setTextColor(Color.WHITE);
        } else if (turn == 2) {
            turnImage.setImageResource(R.drawable.turn_player2);
            player2.setTextColor(Color.GREEN);
            player1.setTextColor(Color.WHITE);
        } else if (turn == 3)
            turnImage.setImageResource(R.drawable.freeturn);

        else
            showWinner();

        if (captureFlag) {
            captureFlag = false;
            turnImage.setImageResource(R.drawable.captured);

        }

        turnImage.setVisibility(View.VISIBLE);
        delay();
    }

    public String computeElapsedTime() {
        Date gameEnd = new Date();

        long duration = gameEnd.getTime() - gameStart.getTime();

        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

        String elapsedTime = diffInHours + ":" + diffInMinutes + ":" + diffInSeconds;
        return elapsedTime;
    }
}

