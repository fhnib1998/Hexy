package fhnib.hexy;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

public class ControlHexy extends AppCompatActivity{
    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket;
    OutputStream outputStream;
    private static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    ImageView foreward,backward,left,right,stop;
    Button getup,reset,typing,wave,dance,lean,fever,point,tiltforward,thriller,tiltbackward,tiltleft,tiltnone,tiltright;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_hexy);
        //Ánh xạ
        textView = (TextView) findViewById(R.id.textView3);
        fever = (Button) findViewById(R.id.btnFever);
        foreward = (ImageView) findViewById(R.id.ivForeward);
        backward = (ImageView) findViewById(R.id.ivBackward);
        left = (ImageView) findViewById(R.id.ivLeft);
        right = (ImageView) findViewById(R.id.ivRight);
        stop = (ImageView) findViewById(R.id.ivStop);
        getup = (Button) findViewById(R.id.btnGetup);
        reset = (Button) findViewById(R.id.btnReset);
        typing = (Button) findViewById(R.id.btnTyping);
        wave = (Button) findViewById(R.id.btnWave);
        dance = (Button) findViewById(R.id.btnDance);
        lean = (Button) findViewById(R.id.btnLean);
        point = (Button) findViewById(R.id.btnPoint);
        tiltforward = (Button) findViewById(R.id.btnFoward);
        thriller = (Button) findViewById(R.id.btnThriller);
        tiltbackward = (Button) findViewById(R.id.btnBackward);
        tiltleft = (Button) findViewById(R.id.btnLeft);
        tiltnone = (Button) findViewById(R.id.btnNone);
        tiltright = (Button) findViewById(R.id.btnRight);
        //Kết nối với thiết bị
        Intent intent = getIntent();
        bluetoothDevice =  intent.getParcelableExtra("device");
        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(mUUID);
        } catch (IOException e) {
                e.printStackTrace();
        }
        try {
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Sự kiện Click
        foreward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("3");
            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("4");
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("1");
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("2");
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        getup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("5");
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("0");
            }
        });
        typing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("g");
            }
        });
        wave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("h");
            }
        });
        dance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("6");
            }
        });
        lean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("8");
            }
        });
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("9");
            }
        });
        tiltforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("d");
            }
        });
        tiltbackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("e");
            }
        });
        fever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("7");
            }
        });
        thriller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("a");
            }
        });
        tiltright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("c");
            }
        });
        tiltleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("b");
            }
        });
        tiltnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write("f");
            }
        });
    }
    //Send data
    public void write(String s){
        try {
            outputStream.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Gọi dialog của google speech thông qua Intent
     * Một số action quan trọng trong Intent như
     * ACTION_RECOGNIZE_SPEECH, LANGUAGE_MODEL_FREE_FORM, EXTRA_PROMPT
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "(^_*)'");
        //intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,0);
        //intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,0);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            a.printStackTrace();
        }
    }
    /**
     * Trả lại dữ liệu sau khi nhập giọng nói vào
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result.get(0).equals("đứng dậy")){
                        write("5");
                    }else if(result.get(0).equals("lên")){
                        write("3");
                    }else if(result.get(0).equals("xuống")){
                        write("4");
                    }else if(result.get(0).equals("quay trái")){
                        write("1");
                    }else if(result.get(0).equals("quay phải")){
                        write("2");
                    }else if(result.get(0).equals("dừng lại")){
                        write("j");
                    }else if(result.get(0).equals("đứng lên")){
                        write("f");
                    }else if(result.get(0).equals("múa")){
                        write("6");
                    }else if(result.get(0).equals("chỉ tay")){
                        write("9");
                    }else if(result.get(0).equals("ngồi xuống")){
                        write("8");
                    }else if(result.get(0).equals("alo")){
                        write("0");
                    }else if(result.get(0).equals("nằm xuống")){
                        write("k");
                    }else if(result.get(0).equals("nhảy")){
                        write("g");
                    }else if(result.get(0).equals("vẫy tay")){
                        write("h");
                    }
                    textView.setText(result.get(0));
                }
                break;
            }

        }
    }
}