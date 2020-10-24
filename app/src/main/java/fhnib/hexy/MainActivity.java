package fhnib.hexy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    ArrayAdapter<String> arrayAdapter;
    BluetoothDevice[] bluetoothDevice;
    ListView listDevice;
    TextView txtText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ánh xạ
        txtText = (TextView) findViewById(R.id.txtText);
        //
        listDevice = (ListView) findViewById(R.id.lvDevice);
        //code
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }else {
            //Hiển thị danh sách thiết bị đã kết nối
            Set<BluetoothDevice> BT = bluetoothAdapter.getBondedDevices();
            if (BT.size() > 0){
                bluetoothDevice = new BluetoothDevice[BT.size()];
                String[] stringArrayList = new String[BT.size()];
                int index = 0;
                for (BluetoothDevice device : BT){
                    bluetoothDevice[index] = device;
                    stringArrayList[index] = device.getName();
                    index++;
                }
                arrayAdapter = new ArrayAdapter<String> (getApplicationContext(),android.R.layout.simple_list_item_1,stringArrayList);
                listDevice.setAdapter(arrayAdapter);
            }
        }

        listDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtText.setText("Đang kết nối ...");
                bluetoothAdapter.cancelDiscovery();
                Intent intent = new Intent(MainActivity.this,ControlHexy.class);
                intent.putExtra("device", bluetoothDevice[position]);
                startActivity(intent);
            }
        });
    }
    //Bật Bluetooth
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(),"Đã bật Bluetooth",Toast.LENGTH_SHORT).show();
                Set<BluetoothDevice> BT = bluetoothAdapter.getBondedDevices();
                if (BT.size() > 0){
                    bluetoothDevice = new BluetoothDevice[BT.size()];
                    String[] stringArrayList = new String[BT.size()];
                    int index = 0;
                    for (BluetoothDevice device : BT){
                        bluetoothDevice[index] = device;
                        stringArrayList[index] = device.getName();
                        index++;
                    }
                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,stringArrayList);
                    listDevice.setAdapter(arrayAdapter);
                }
            }else {
                System.exit(0);
            }
        }
    }
}
