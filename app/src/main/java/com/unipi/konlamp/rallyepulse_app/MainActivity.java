package com.unipi.konlamp.rallyepulse_app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private LinearLayout redbarcontainer;
    private View[] redbars;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGattServer gattServer;
    private TextView statusLabel;
    private boolean isRunning = false;


    private CountDownTimer countDownTimer;

    private TextView countdownText;


    BluetoothLeAdvertiser advertiser;

    private int restart = 0;

    private LocalTime startTime;

    private Handler handler = new Handler();
    private static final int REQUEST_ENABLE_BT = 1;

    private UUID UUID_NUMBER = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
    private Long conumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conumber = this.getIntent().getLongExtra("conumber", 0);
        countdownText = findViewById(R.id.countdownText);
        statusLabel = findViewById(R.id.statusLabel);
        redbarcontainer = findViewById(R.id.redbarcontainer);
        redbars = new View[]{
                findViewById(R.id.redbar1),
                findViewById(R.id.redbar2),
                findViewById(R.id.redbar3),
                findViewById(R.id.redbar4),
                findViewById(R.id.redbar5)

        };
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            enableBluetoothDialog();
        } else {
            bluetoothAdapter.setName("Rallye_" + conumber);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startTime = LocalTime.of(17, 11);
//            startCountdown();
//        }
        countdownText.setText("Waiting to start");

        startGattServer();

    }

    private void enableBluetoothDialog() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.enable_bluetooth))
                    .setMessage(getString(R.string.enable_bluetooth_prompt))
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
            } else {
                finish();
            }
        }
    }

    private void startCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        LocalDate date = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
            String targetTimeString = date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth() + " " + startTime.getHour() + ":" + startTime.getMinute() + ":00";
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                Date startdate = dateformat.parse(targetTimeString);
                Long starttimemilis = startdate.getTime();
                Long currenttimemillis = System.currentTimeMillis();
                Long differencetimemillis = starttimemilis - currenttimemillis;
                countDownTimer = new CountDownTimer(differencetimemillis,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        Long second = millisUntilFinished/1000;
                        countdownText.setText(String.valueOf(second));
                        if (second == 5) {
                            redbarcontainer.setVisibility(View.VISIBLE);
                        }
                        if (second <= 4 ) {
                            int index = (int)(4 - second);
                            if (index < redbars.length) {
                                redbars[index].setVisibility(View.INVISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        countdownText.setText("GO!");
                        isRunning=true;
                        handler.post(updateTimeRunnable);
                        redbarcontainer.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                        countdownText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
                    }
                }.start();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalTime currentTime = LocalTime.now();
                    Duration elapsedTime = Duration.between(startTime, currentTime);
                    long hours = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        hours = elapsedTime.toHours();
                        long minutes = elapsedTime.toMinutes() % 60;
                        long seconds = elapsedTime.getSeconds() % 60;
                        long millis = elapsedTime.toMillis() % 1000;

                        String formattedTime = String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, millis);
                        countdownText.setText(formattedTime);
                        countdownText.postDelayed(this, 1);
                    }

                }

            }
        }
    };


    private void startGattServer() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        gattServer = bluetoothManager.openGattServer(this, new BluetoothGattServerCallback() {
            @Override
            public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
                super.onConnectionStateChange(device, status, newState);
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    runOnUiThread(() -> statusLabel.setText(getString(R.string.connectedtk)));
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

                    runOnUiThread(() -> statusLabel.setText(getString(R.string.waitingcontk)));

                    if(restart==0) {
                        runOnUiThread(()->stopGattServer());
                        runOnUiThread(() -> startGattServer());
                    }


                }
            }

            @Override
            public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
                //super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
                if (UUID_NUMBER.equals(characteristic.getUuid())) {
                    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
                    buffer.putLong(conumber);
                    byte[] response = buffer.array(); // Example number to send
                    gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, response);
                }
            }



            @Override
            public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
                super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
                String receivedTime = new String(value);
                runOnUiThread(() -> statusLabel.setText("Received time: " + receivedTime));



                LocalTime now;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startTime=LocalTime.parse(receivedTime);

                    runOnUiThread(() ->startCountdown());
                } else {
                    now = null;
                    startTime = null;
                }
                // Send confirmation back to the client
                String confirmation = "Time received: " + receivedTime;
                byte[] confirmationBytes = confirmation.getBytes();
                characteristic.setValue(confirmationBytes);
                gattServer.notifyCharacteristicChanged(device, characteristic, false);

                if (responseNeeded) {
                    Log.d("test", "onCharacteristicWriteRequest: ");
                    gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
                }
            }
        });

        BluetoothGattService service = new BluetoothGattService(
                UUID.fromString("00001805-0000-1000-8000-00805f9b34fb"),
                BluetoothGattService.SERVICE_TYPE_PRIMARY
        );
        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(
                UUID.fromString("00002a2b-0000-1000-8000-00805f9b34fb"),
                BluetoothGattCharacteristic.PROPERTY_WRITE | BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_WRITE
        );

        android.bluetooth.BluetoothGattCharacteristic characteristicS = new android.bluetooth.BluetoothGattCharacteristic(
                java.util.UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb"),
                android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ,
                android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ
        );

        BluetoothGattDescriptor cccDescriptor = new BluetoothGattDescriptor(
                UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"),
                BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE
        );
        characteristic.addDescriptor(cccDescriptor);
        service.addCharacteristic(characteristic);
        service.addCharacteristic(characteristicS);
        gattServer.addService(service);

        // Start advertising with custom name
        advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setConnectable(true)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .build();
        AdvertiseData data = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .addServiceUuid(new ParcelUuid(UUID.fromString("00001805-0000-1000-8000-00805f9b34fb")))
                .setIncludeTxPowerLevel(true)
                .build();
        AdvertiseData scanResponse = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .build();
        advertiser.startAdvertising(settings, data, scanResponse, advertiseCallback);


        statusLabel.setText(getString(R.string.waitingcontk) );
    }


    private void stopGattServer() {
        if (gattServer != null) {
            for (BluetoothDevice device :  bluetoothManager.getConnectedDevices(BluetoothProfile.GATT_SERVER)) {
                gattServer.cancelConnection(device);
            }
            gattServer.clearServices();
            gattServer.close();
            advertiser.stopAdvertising(advertiseCallback);
            gattServer = null;
            statusLabel.setText("GATT Server Stopped");
        }
    }


    private final AdvertiseCallback advertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            runOnUiThread(() -> statusLabel.setText("Advertising started"));
        }

        @Override
        public void onStartFailure(int errorCode) {
            runOnUiThread(() -> statusLabel.setText("Advertising failed: " + errorCode));
        }
    };
}

